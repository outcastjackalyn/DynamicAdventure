package com.outcastjackalyn.scenes;

import com.outcastjackalyn.game.GameData;
import com.outcastjackalyn.objects.furniture.Furniture;
import com.outcastjackalyn.objects.furniture.Furnitures;
import com.outcastjackalyn.objects.items.Items;
import com.outcastjackalyn.utils.AdjectiveUtil;
import com.outcastjackalyn.utils.AdjectiveUtil.*;
import com.outcastjackalyn.utils.DynamicDescriptionUtil;
import com.outcastjackalyn.utils.SeedUtil;
import jjcard.text.game.IItem;
import jjcard.text.game.impl.Item;
import jjcard.text.game.util.MapUtil;

import java.util.*;

public class RoomManager {

    private static GameData gameData;

    public RoomManager(GameData gameData) {
        this.gameData = gameData;
    }

    public static DynLocation newRoom(DynLocation location, String seed) {
        int number = SeedUtil.getDigit(seed, 1);
        Rooms room = Rooms.values()[number >= Rooms.values().length ? 0 : number];
        location.setName(room.getName());
        location.setDescription(AdjectiveUtil.updateText(room.getRoomDescription(), seed));
        location.setInventoryDescription(room.getInventoryDescription());
        location = fillRoom(location, seed);
        location = newExits(location, seed);
        return location;
    }

    public static DynLocation fillRoom(DynLocation location, String seed) {

        int numberOfFurniture = 0;
        if (SeedUtil.containsStraight(seed, new Random().nextInt(5), 1, 2)) {
            numberOfFurniture++;
        }
        if (SeedUtil.getDigitFromEnd(seed, numberOfFurniture + 3) < new Random().nextInt(7)) {
            numberOfFurniture++;
        }
        for(int i = 0; i < numberOfFurniture; i++) {
            location.addFurniture(newFurniture(location, seed, i));
        }
        location.setInventory(fillInventory(location, seed, 0));

        return location;
    }

    private static Map<String, IItem> fillInventory(DynLocation location, String seed, int numberOfItems) {

        Map<String, IItem> inventory = location.getInventory();
        if (SeedUtil.containsStraight(seed, new Random().nextInt(10), 0, 3)) {
            numberOfItems++;
        }
        if (SeedUtil.getDigitFromEnd(seed, numberOfItems) < 2) {
            numberOfItems++;
        }
        for(int i = 0; i < numberOfItems; i++) {
            Item item = newItem(location, seed, i);
            inventory.put(item.getName(),item);
        }
        return inventory;
    }

    private static Item newItem(DynLocation location, String seed, int number) {
        int j = SeedUtil.getDigit(seed,3 + number);
        Items i = Items.values()[j > 4 ? j - 5 : j];
        Item item = new Item.Builder().name(i.getName())
                .roomDescription(AdjectiveUtil.updateText(i.getRoomDescription(),seed))
                .viewDescription(AdjectiveUtil.updateText(i.getViewDescription(), seed)).build();
        return item;
    }

    private static Furniture newFurniture(DynLocation location, String seed, int number) {
        int i = SeedUtil.getDigit(seed,8 + number);
        Furnitures f = Furnitures.values()[i > 4 ? i -5 : i];
        Furniture furniture = new Furniture.Builder().name(f.getName()).lockState(f.getLockState())
                .roomDescription(AdjectiveUtil.updateText(f.getRoomDescription(), seed))
                .viewDescription(AdjectiveUtil.updateText("It is a " + f.getName() + ".", seed))
                .inventoryDescription(AdjectiveUtil.updateText(f.getInventoryDescription(), seed))
                .build();
        furniture.setInventory(fillInventory(location, seed, 1));
        return furniture;
    }

    public static DynLocation newExits(DynLocation location, String seed) {
        String previous = location.getOnlyExit().getName();
        int numberOfExits = 1;


        ArrayList<String> directions = getDirections(numberOfExits, previous, location.getName().equals("stair"));
        for (String direction : directions) {
            newExit(location, seed, direction);
        }

        return location;
    }

    private static ArrayList<String> getDirections(int numberOfExits, String previous, boolean staircase) {

        ArrayList<String> directions = new ArrayList<String>();
        for(LockableExit exit : LockableExit.DEFAULT_VALUES)
        {
            directions.add(exit.getName());
        }
        directions.remove(previous);
        for (int i = directions.size(); i > numberOfExits; i--) {
            if(staircase) {
                //TODO make staircases enforce up downs
                directions.remove(new Random().nextInt(directions.size()));
            } else {
                directions.remove(new Random().nextInt(directions.size()));
            }
        }
        return directions;
    }


    public static void newExit(DynLocation location, String seed, String name) {
        DynLocation newLocation = new DynLocation("empty");
        Exits e = Exits.selectExit(seed);
        LockState lock = e.getLockState();

        LockableExit exit = new LockableExit.Builder().name(name).location(newLocation)
                .roomDescription(AdjectiveUtil.updateText(e.getRoomDescription(), seed))
                .viewDescription(AdjectiveUtil.updateText(e.getViewDescription(), seed))
                .hiddenDescription(AdjectiveUtil.updateText(e.getHiddenDescription(), seed ))
                .hidden(e.getHidden()).lockState(lock).hiddenName(e.getHiddenName()).build();

        LockableExit entrance = new LockableExit.Builder().name(LockableExit.oppositeDirection(exit)).location(location)
                .roomDescription(AdjectiveUtil.updateText(e.getRoomDescription(), seed))
                .viewDescription(AdjectiveUtil.updateText(e.getViewDescription(), seed))
                .hiddenDescription(AdjectiveUtil.updateText(e.getHiddenDescription(), seed ))
                .hidden(e.getHidden()).lockState(lock).hiddenName(e.getHiddenName()).build();

        location.addExit(exit);
        newLocation.addExit(entrance);
        gameData.addLocation(newLocation);

    }


}
