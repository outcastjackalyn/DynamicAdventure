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
        LockableExit previous = (LockableExit) location.getOnlyExit();
        int numberOfExits = 1;
        for (LockableExit exit : getDirections(numberOfExits, previous, location.getName().equals("stair"))) {
            newExit(location, seed, exit.getName());
        }

        return location;
    }

    private static LockableExit[] getDirections(int numberOfExits, LockableExit previous, boolean staircase) {
        LockableExit[] directions = LockableExit.DEFAULT_VALUES;
        ArrayList<LockableExit> dirs = new ArrayList<LockableExit>(Arrays.asList(directions));
        dirs.remove(previous);
        for (int i = dirs.size(); i <= numberOfExits; i--) {
            if(staircase) {
                //TODO make staircases enforce up downs
                dirs.remove(new Random().nextInt(dirs.size()));
            } else {
                dirs.remove(new Random().nextInt(dirs.size()));
            }
        }
        directions = dirs.toArray(new LockableExit[dirs.size()]);
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
