package com.outcastjackalyn.objects.furniture;

import com.outcastjackalyn.scenes.LockState;

public enum Furnitures {
    TABLE("table", "There is a #adj# wooden table", "on the table", LockState.ALWAYS_OPEN),
    CHAIR("chair", "You see a small chair", "under the chair", LockState.ALWAYS_OPEN),
    WARDROBE("wardrobe", "A #adj# wardrobe stands against the wall", "in the wardrobe", LockState.BLOCKED),
    CHEST("chest", "You see a wooden chest - maybe it has useful contents" , "in the chest", LockState.LOCKED),
    BED("bed", "This must have been a bedroom - there's a #adj# bed here", "between the #color# sheets", LockState.ALWAYS_OPEN);




    private String name;
    private String roomDescription;
    private String inventoryDescription;
    private LockState lockState;


    public String getName() {
        return name;
    }
    public String getRoomDescription() {
        return roomDescription;
    }
    public String getInventoryDescription() {
        return inventoryDescription;
    }
    public LockState getLockState() { return lockState; }


    Furnitures(String name, String roomDescription, String inventoryDescription, LockState lockState) {
        this.name = name;
        this.roomDescription = roomDescription;
        this.inventoryDescription = inventoryDescription;
        this.lockState = lockState;
    }

}
