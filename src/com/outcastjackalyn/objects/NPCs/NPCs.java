package com.outcastjackalyn.objects.NPCs;

public enum NPCs {
    OLDMAN("old man", "He's minding his own business.", "You're not alone, there's an old balding man in the corner.", false),
    RAT("rat", "It's larger than the last rat you saw.", "You think you hear a scurrying sound in here.", false);



    private String name;
    private String roomDescription;
    private String viewDescription;
    private boolean hostile;


    public String getName() {
        return name;
    }
    public String getRoomDescription() {
        return roomDescription;
    }
    public String getViewDescription() {
        return viewDescription;
    }
    public boolean isHostile() { return hostile; }



    NPCs(String name, String viewDescription, String roomDescription, boolean hostile) {
        this.name = name;
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.hostile = hostile;
    }


}
