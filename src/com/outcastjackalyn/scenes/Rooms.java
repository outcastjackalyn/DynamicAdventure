package com.outcastjackalyn.scenes;

public enum Rooms {
    HALLWAY("hallway", "A long $color$ hallway.", "amongst the dust"),
    ROOM("room", "A $adj$, $color$ room.", "on the floor"),
    STAIR("stair", "A $adj$ staircase.", "on a step"),
    EMPTY("empty", "", "");

    private String name;
    private String roomDescription;
    private String inventoryDescription;


    public String getName() {
        return name;
    }
    public String getRoomDescription() {
        return roomDescription;
    }
    public String getInventoryDescription() {
        return inventoryDescription;
    }


    Rooms(String name, String roomDescription, String inventoryDescription) {
        this.name = name;
        this.roomDescription = roomDescription;
        this.inventoryDescription = inventoryDescription;
    }


}
