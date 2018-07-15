package com.outcastjackalyn.scenes;

import com.outcastjackalyn.game.GameData;
import com.outcastjackalyn.utils.SeedUtil;
import jjcard.text.game.impl.Item;

import java.util.ArrayList;
import java.util.Random;

public enum Exits {
    BASIC("Just a normal #color# door.", " there is a door", LockState.ALWAYS_OPEN, 12),
    LOCKED("This door looks like it needs a key.", " there is a #lock# door", LockState.LOCKED, 4), // supposed to require a key to be found
    OPENING("You can see another room this way.", " there is an opening", LockState.ALWAYS_OPEN, 7),
    CURTAIN("A curtain drapes between a doorway.", " a #color# curtain hangs in place of a door", LockState.ALWAYS_OPEN, 4, Direction.HORIZONTAL),
    TAPESTRY("An elaborately painted tapestry hangs here, you think maybe you recognise it",
            " a tapestry covers a hole in the wall", LockState.ALWAYS_OPEN, 2, "There is a tapestry against a wall", true, Direction.HORIZONTAL, "tapestry"),
    BLOCKED("You can't see a way through, yet...", " a passage seems blocked", LockState.BLOCKED, 3),
    CODELOCK("There is a keypad next to the door.", " there is a #lock# door", LockState.LOCKED, 1),// supposed to require the player to find two pieces of paper
    BOOKCASE("Looks like just a normal #adj# bookcase. You see the book: #book#."
            , " a bookcase has moved revealing a path", LockState.ALWAYS_OPEN, 2 // should have been counted as blocked and be opened by examining the book
            , "Maybe this a library or study, there's a bookcase standing in the corner", true, "bookcase"),
    TRAPDOOR("A trapdoor with a handle.", " there is a hatch", LockState.ALWAYS_OPEN, 2, "There is an odd handle", true, Direction.VERTICAL, "handle");

    private String name;
    private String roomDescription;
    private String viewDescription;
    private String hiddenDescription;
    private String hiddenName;
    private LockState lockState;
    private Direction direction;
    private boolean hidden;
    private int weight;


    public String getName() { return name; }
    public String getRoomDescription() {
        return roomDescription;
    }
    public String getViewDescription() {
        return viewDescription;
    }
    public LockState getLockState() { return lockState; }
    public Direction getDirection() { return direction; }
    public String getHiddenDescription() {
        return hiddenDescription;
    }
    public String getHiddenName() {
        return hiddenName;
    }
    public boolean getHidden() { return hidden; }
    public int getWeight() { return weight; }

    Exits(String viewDescription, String roomDescription, LockState lockState, int weight) {
        this.name = this.toString();
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = "";
        this.hidden = false;
        this.direction = Direction.EITHER;
        this.hiddenName = "";
    }
    Exits(String viewDescription, String roomDescription, LockState lockState, int weight, Direction direction) {
        this.name = this.toString();
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = "";
        this.hidden = false;
        this.direction = direction;
        this.hiddenName = "";
    }
    Exits(String viewDescription, String roomDescription, LockState lockState, int weight, String hiddenDescription, boolean hidden, String hiddenName) {
        this.name = this.toString();
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = hiddenDescription;
        this.hidden = hidden;
        this.direction = Direction.EITHER;
        this.hiddenName = hiddenName;
    }
    Exits(String viewDescription, String roomDescription, LockState lockState, int weight, String hiddenDescription, boolean hidden, Direction direction, String hiddenName) {
        this.name = this.toString();
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = hiddenDescription;
        this.hidden = hidden;
        this.direction = direction;
        this.hiddenName = hiddenName;
    }


    public static Exits selectExit(String seed, GameData gameData) {
        //String str = gameData.getSeed();
        int min = SeedUtil.getDigit(seed, 0);
        int total = 0;
        ArrayList<Exits> exits = new ArrayList<Exits>();
        for(Exits e : Exits.values()) {
            int weight = e.getWeight();
            // increase chance of certain exit types spawning when an associated item
            // is already available and on the map for the player.
            if(!gameData.getAssociated().isEmpty()) {
                for (String s : gameData.getAssociated()) {
                    if (s.equals(e.getName())) {
                        weight *= 2;
                    }
                }
            }
            if(e.getWeight() > min) {
                //weight -= min;
                for (int i = 0; i< weight; i++) {
                    exits.add(e);
                }
                total += weight;
            }
        }
        Exits out = exits.get(new Random().nextInt(total));
        if(!gameData.getAssociated().isEmpty()) {
            //when the associated door is selected it should no longer gained increased chance of appearing
            for (String s : gameData.getAssociated()) {
                if (s.equals(out.getName())) {
                    gameData.removeAssociated(s);
                    break;
                }
            }
        }
        return out;
    }




}