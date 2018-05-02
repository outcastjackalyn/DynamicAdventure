package com.outcastjackalyn.scenes;

import com.outcastjackalyn.utils.SeedUtil;

import java.util.ArrayList;
import java.util.Random;

public enum Rooms {
    HALLWAY("hallway", "A long #color# hallway.", "amongst the dust", 5),
    ROOM("room", "A #adj#, #color# room.", "on the floor", 7),
    LOBBY("lobby", "A wide open lobby area.", "in the middle of the room", 4),
    STAIR("stair", "A #adj# staircase.", "on a step", 1),
    START("start", "A spacious room.", "on the floor", 0)
    ;

    private String name;
    private String roomDescription;
    private String inventoryDescription;
    private int weight;


    public String getName() {
        return name;
    }
    public String getRoomDescription() {
        return roomDescription;
    }
    public String getInventoryDescription() {
        return inventoryDescription;
    }
    public int getWeight() {
        return weight;
    }

    public static DynLocation build(DynLocation location, Rooms room) {
        location.setName(room.getName());
        location.setDescription(room.getRoomDescription());
        location.setInventoryDescription(room.getInventoryDescription());
        return location;
    }

    Rooms(String name, String roomDescription, String inventoryDescription, int weight) {
        this.name = name;
        this.roomDescription = roomDescription;
        this.inventoryDescription = inventoryDescription;
        this.weight = weight;
    }



    public static Rooms selectRoom(String seed) {
        int min = SeedUtil.getDigit(seed, 0);
        int total = 0;
        ArrayList<Rooms> rooms = new ArrayList<Rooms>();
        for(Rooms r : Rooms.values()) {
            if(r.getWeight() > min) {
                int weight = r.getWeight() - min;
                for (int i = 0; i< weight; i++) {
                    rooms.add(r);
                }
                total += weight;
            }
        }

        return rooms.get(new Random().nextInt(total));
    }


}
