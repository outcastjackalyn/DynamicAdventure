package com.outcastjackalyn.objects.items;

public enum Items {
    APPLE("apple", "It is shiny and red.", "There is an apple $pos$.", true),
    CROWBAR("crowbar", "It's rusty, but sturdy.", "You see a crowbar $pos$.", true),
    VASE("vase", "A $color$ vase.", "A $adj$, empty vase $pos$.", false),
    PAPER("a piece of paper", "You find the letters :$code$ scrawled on the page.", "A dusty scrap of paper $pos$.", true),
    KEY("key", "A $adj$ $color$ key.", "Something shiny can be seen $pos$.", true);


    private String name;
    private String roomDescription;
    private String viewDescription;
    private boolean movable;


    public String getName() {
        return name;
    }
    public String getRoomDescription() {
        return roomDescription;
    }
    public String getViewDescription() {
        return viewDescription;
    }
    public boolean getMovable() { return movable; }



    Items(String name, String viewDescription, String roomDescription, boolean movable) {
        this.name = name;
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.movable = movable;
    }
}
