package com.outcastjackalyn.scenes;

public enum Exits {
    BASIC("Just a normal $color$ door.", " there is a door.", LockState.ALWAYS_OPEN, 12),
    LOCKED("This door looks like it needs a key.", " there is a $lock$ door.", LockState.LOCKED, 4),
    OPENING("You can see another room this way.", " there is an opening.", LockState.ALWAYS_OPEN, 7),
    CURTAIN("A curtain drapes between a doorway.", " a $color$ curtain hangs in place of a door.", LockState.ALWAYS_OPEN, 4, Direction.HORIZONTAL),
    TAPESTRY("An elaborately painted tapestry hangs here, you think maybe you recognise it.",
            " a tapestry covers a hole in the wall.", LockState.ALWAYS_OPEN, 2, "There is a tapestry against a wall.", true, Direction.HORIZONTAL),
    BLOCKED("You can't see a way through, yet...", " a passage seems blocked.", LockState.BLOCKED, 3),
    CODELOCK("There is a keypad next to the door.", " there is a $lock$ door.", LockState.LOCKED, 1),
    BOOKCASE("Looks like just a normal $adj$ bookcase. You see the book: $book$."
            , " a bookcase has moved revealing a path.", LockState.BLOCKED, 2, "Maybe this a library or study, there's a bookcase standing in the corner.", true),
    TRAPDOOR("A trapdoor with a handle.", " there is a hatch.", LockState.ALWAYS_OPEN, 2, "", true, Direction.VERTICAL);


    private String roomDescription;
    private String viewDescription;
    private String hiddenDescription;
    private LockState lockState;
    private Direction direction;
    private boolean hidden;
    private int weight;


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
    public boolean getHidden() { return hidden; }
    public int getWeight() { return weight; }

    Exits(String viewDescription, String roomDescription, LockState lockState, int weight) {
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = "";
        this.hidden = false;
        this.direction = Direction.EITHER;
    }
    Exits(String viewDescription, String roomDescription, LockState lockState, int weight, Direction direction) {
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = "";
        this.hidden = false;
        this.direction = direction;
    }
    Exits(String viewDescription, String roomDescription, LockState lockState, int weight, String hiddenDescription, boolean hidden) {
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = hiddenDescription;
        this.hidden = hidden;
        this.direction = Direction.EITHER;
    }
    Exits(String viewDescription, String roomDescription, LockState lockState, int weight, String hiddenDescription, boolean hidden, Direction direction) {
        this.roomDescription = roomDescription;
        this.viewDescription = viewDescription;
        this.lockState = lockState;
        this.weight = weight;
        this.hiddenDescription = hiddenDescription;
        this.hidden = hidden;
        this.direction = direction;
    }
}