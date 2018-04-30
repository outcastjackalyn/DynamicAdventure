package com.outcastjackalyn.objects.items;

public enum Items {
    APPLE("apple", "It is shiny and red.", "There is an apple $pos$.", true),
    CROWBAR("crowbar", "It's rusty, but sturdy.", "You see a crowbar $pos$.", true),
    VASE("vase", "A $color$ vase.", "A $adj$, empty vase $pos$.", false),
    PAPER("a piece of paper", "You find the letters :$code$ scrawled on the page.", "A dusty scrap of paper $pos$.", true),
    KEY("key", "A $adj$ $color$ key.", "Something shiny can be seen $pos$.", true);

    Items(String name, String viewDescription, String roomDescription, boolean movable) {

    }
}
