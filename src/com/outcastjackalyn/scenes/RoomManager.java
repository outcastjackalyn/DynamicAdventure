package com.outcastjackalyn.scenes;

import com.outcastjackalyn.game.GameData;
import com.outcastjackalyn.objects.furniture.Furniture;
import com.outcastjackalyn.objects.furniture.Furnitures;
import com.outcastjackalyn.objects.items.Items;
import com.outcastjackalyn.utils.AdjectiveUtil;
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

    public static DynLocation newRoom(DynLocation location, int seed) {
        //String str = ((Integer) seed).toString();
        Rooms room = Rooms.values()[seed];
        location.setName(room.getName());
        location.setDescription(AdjectiveUtil.updateText(room.getRoomDescription(), seed, ""));
        location.setInventoryDescription(room.getInventoryDescription());
        //location = new DynLocation(room.getName(), room.getRoomDescription());
        System.out.println(" New room made yo");
        location = fillRoom(location, seed);
        location = newExits(location, "123");
        return location;
    }

    public static DynLocation fillRoom(DynLocation location, int seed) {

        for(int i = 0; i < seed; i++) {
            location.addFurniture(newFurniture(location, seed));
        }
        fillInventory(location, seed);

        return location;
    }

    private static Map<String, IItem> fillInventory(DynLocation location, int seed) {

        Map<String, IItem> map = location.getInventory();
        for(int i = 0; i < seed; i++) {
            newItem(location, seed);
        }
        return map;
    }

    private static Item newItem(DynLocation location, int seed) {
        Items i = Items.values()[seed];
        Item item = new Item.Builder().name(i.getName())
                .roomDescription(AdjectiveUtil.updateText(i.getRoomDescription(),seed, ""))
                .viewDescription(i.getViewDescription()).build();
        return item;
    }

    private static Furniture newFurniture(DynLocation location, int seed) {
        Furnitures f = Furnitures.values()[seed];
        Furniture furniture = new Furniture.Builder().name(f.getName())
                .roomDescription(AdjectiveUtil.updateText(f.getRoomDescription(), seed, ""))
                .inventoryDescription(AdjectiveUtil.updateText(f.getInventoryDescription(), seed, ""))
                .build();
        fillInventory(location, seed);
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
        //directions = dirs.toArray(new LockableExit[dirs.size()]);
        return directions;
    }


    public static void newExit(DynLocation location, String seed, String name) {
        //TODO set lockstate with seed
        LockState lockState = LockState.ALWAYS_OPEN;

        DynLocation newLocation = new DynLocation("empty");

        LockableExit exit = new LockableExit.Builder().name(name).location(newLocation).build();
        LockableExit entrance = LockableExit.oppositeDirection(exit);
        entrance.setLockState(lockState);

        location.addExit(exit);
        newLocation.addExit(entrance.getWithLocation(location));
        gameData.addLocation(newLocation);

    }


}
