package com.outcastjackalyn.scenes;

import com.outcastjackalyn.game.GameData;
import com.outcastjackalyn.objects.furniture.Furniture;
import com.outcastjackalyn.utils.AdjectiveUtil;

public class RoomManager {

    public static DynLocation newRoom(DynLocation location, int seed) {
        Rooms room = Rooms.values()[seed];
        //location.setName(room.getName());
        location = new DynLocation(room.getName(), room.getRoomDescription());
        System.out.println(" New room made yo");
        fillRoom(location, seed, room.getInventoryDescription());
        addExits(location, seed);
        return location;
    }

    public static void fillRoom(DynLocation location, int seed, String inventoryDescription) {

        addFurniture(location, seed);
        fillInventory(location, seed);


    }

    private static void fillInventory(DynLocation location, int seed) {

    }

    private static void addFurniture(DynLocation location, int seed) {

        fillInventory(location, seed);
    }

    public static void addExits(DynLocation location, int seed) {



    }

    public static String updateText(String str, int seed) {
        str.replaceAll("$color$", AdjectiveUtil.colors[seed]);
     return str;
    }

}
