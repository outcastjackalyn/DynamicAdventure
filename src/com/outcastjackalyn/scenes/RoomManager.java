package com.outcastjackalyn.scenes;

import com.outcastjackalyn.game.GameData;
import com.outcastjackalyn.objects.furniture.Furniture;
import com.outcastjackalyn.objects.furniture.Furnitures;
import com.outcastjackalyn.objects.items.Items;
import com.outcastjackalyn.utils.AdjectiveUtil;
import jjcard.text.game.impl.Item;

public class RoomManager {

    public static DynLocation newRoom(DynLocation location, int seed) {
        //String str = ((Integer) seed).toString();
        Rooms room = Rooms.values()[seed];
        location.setName(room.getName());
        location.setDescription(AdjectiveUtil.updateText(room.getRoomDescription(), seed, ""));
        location.setInventoryDescription(room.getInventoryDescription());
        //location = new DynLocation(room.getName(), room.getRoomDescription());
        System.out.println(" New room made yo");
        location = fillRoom(location, seed);
        location = newExits(location, seed);
        return location;
    }

    public static DynLocation fillRoom(DynLocation location, int seed) {

        for(int i = 0; i < seed; i++) {
            location.addFurniture(newFurniture(location, seed));
        }
        fillInventory(location, seed);

        return location;
    }

    private static void fillInventory(DynLocation location, int seed) {

        for(int i = 0; i < seed; i++) {
            newItem(location, seed);
        }
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

    public static DynLocation newExits(DynLocation location, int seed) {


        return location;
    }



}
