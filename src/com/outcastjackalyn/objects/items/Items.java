package com.outcastjackalyn.objects.items;

import com.outcastjackalyn.scenes.Exits;

public enum Items {
    APPLE("apple", "It is shiny and red.", "There is an apple #pos#", true),
    CROWBAR("crowbar", "It's rusty, but sturdy.", "You see a crowbar #pos#", true, Exits.BLOCKED),
    VASE("vase", "A #color# vase.", "A #adj#, empty vase #pos#", false),
    PAPER("paper", "You find the letters :#code# scrawled on the page.", "A dusty scrap of paper #pos#", true, Exits.CODELOCK),
    KEY("key", "A #adj# #color# key.", "Something shiny can be seen #pos#", true, Exits.LOCKED);


    private String name;
    private String roomDescription;
    private String viewDescription;
    private boolean movable;
    private Exits associated;


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
    public Exits getAssociated() { return  associated; }



    Items(String name, String viewDescription, String roomDescription, boolean movable) {
        this.name = name;
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.movable = movable;
        this.associated = Exits.BASIC;
    }

    Items(String name, String viewDescription, String roomDescription, boolean movable, Exits associated) {
        this.name = name;
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.movable = movable;
        this.associated = associated;
    }
}
