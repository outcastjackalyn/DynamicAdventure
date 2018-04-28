package com.outcastjackalyn.scenes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jjcard.text.game.IExit;
import jjcard.text.game.ILocation;
import jjcard.text.game.impl.AbstractGameElement;
import jjcard.text.game.impl.Exit;
import jjcard.text.game.util.ObjectsUtil;


/**
 * Class to hold an LockableExit pertaining to a specific IDynLocation
 *
 */
@JsonDeserialize(builder = LockableExit.Builder.class)
public class LockableExit extends AbstractGameElement implements IDynExit {

    public static final Builder NORTH_BUILD = new LockableExit.Builder().name("NORTH");
    public static final Builder SOUTH_BUILD = new LockableExit.Builder().name("SOUTH");
    public static final Builder EAST_BUILD = new LockableExit.Builder().name("EAST");
    public static final Builder WEST_BUILD = new LockableExit.Builder().name("WEST");
    public static final Builder NORTHEAST_BUILD = new LockableExit.Builder().name("NORTHEAST");
    public static final Builder NORTHWEST_BUILD = new LockableExit.Builder().name("NORTHWEST");
    public static final Builder SOUTHEAST_BUILD = new LockableExit.Builder().name("SOUTHEAST");
    public static final Builder SOUTHWEST_BUILD = new LockableExit.Builder().name("SOUTHWEST");
    public static final Builder UP_BUILD = new LockableExit.Builder().name("UP");
    public static final Builder DOWN_BUILD = new LockableExit.Builder().name("DOWN");

    public static final LockableExit NORTH = NORTH_BUILD.build();
    public static final LockableExit SOUTH = SOUTH_BUILD.build();
    public static final LockableExit EAST = EAST_BUILD.build();
    public static final LockableExit WEST = WEST_BUILD.build();
    public static final LockableExit NORTHEAST = NORTHEAST_BUILD.build();
    public static final LockableExit NORTHWEST = NORTHWEST_BUILD.build();
    public static final LockableExit SOUTHEAST = SOUTHEAST_BUILD.build();
    public static final LockableExit SOUTHWEST = SOUTHWEST_BUILD.build();
    public static final LockableExit UP = UP_BUILD.build();
    public static final LockableExit DOWN = DOWN_BUILD.build();

    /**
     * List that includes North, South, East, And West
     */
    public static final LockableExit[] SIMPLE_VALUES = new LockableExit[]{NORTH, SOUTH, EAST, WEST};
    /**
     * List that contains all compass directions; NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
     */
    public static final LockableExit[] COMPASS_VALUES = new LockableExit[]
            {NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST};
    /**
     * List that includes all static default LockableExits
     */
    public static final LockableExit[] DEFAULT_VALUES = new LockableExit[]
            {NORTH, SOUTH, EAST, WEST, NORTHWEST, NORTHEAST, SOUTHEAST, SOUTHEAST, UP, DOWN};



    @JsonProperty("loc")
    private final IDynLocation location;

    @JsonProperty("hid")
    private boolean hidden = false;

    @JsonProperty("state")
    private LockState lockState = LockState.ALWAYS_OPEN;

    public static class Builder extends AbstractGameElement.Builder{
        private IDynLocation location;
        private boolean hidden = false;
        private LockState lockState;

        public Builder(){
            super();
        }
        public Builder(LockableExit exit){
            super(exit);
            this.location = exit.location;
        }
        public Builder(AbstractGameElement element){
            super(element);
        }
        public LockableExit.Builder name(String name){
            super.name(name);
            return this;
        }
        public LockableExit.Builder roomDescription(String roomDescrip){
            super.roomDescription(roomDescrip);
            return this;
        }
        @JsonProperty("loc")
        public LockableExit.Builder location(IDynLocation location){
            this.location = location;
            return this;
        }
        @JsonProperty("hid")
        public LockableExit.Builder hidden(boolean hidden){
            this.hidden = hidden;
            return this;
        }
        @JsonProperty("state")
        public LockableExit.Builder lockState(LockState lockState){
            this.lockState = lockState;
            return this;
        }
        @JsonIgnore
        /**
         * Sets the location to given location and name to location's name
         * @param location
         * @return this
         */
        public LockableExit.Builder locationAndName(IDynLocation location){
            this.location = location;
            this.name(location.getName());
            return this;
        }
        public LockableExit build(){
            return new LockableExit(this);
        }


    }

    protected LockableExit(LockableExit.Builder builder){
        super(builder);
        this.location = builder.location;
        this.hidden = builder.hidden;
        this.lockState = lockState;
    }
    /**
     * Gets the Location
     */
    public IDynLocation getLocation() {
        return location;
    }

   // public ILocation getLocation() {return null;}
    /**
     * Returns a new LockableExit with the same properties as the current one with the given location
     * @param location
     * @return copy of LockableExit with location
     */
    public LockableExit getWithLocation(IDynLocation location){
        return new LockableExit.Builder(this).location(location).build();
    }
    /**
     * Returns a new LockableExit with given location, and name of LockableExit being same as the location
     * @param location
     * @return new LockableExit
     */
    public static LockableExit getWithLocationAsName(IDynLocation location){
        return new LockableExit.Builder().location(location).name(location.getName()).build();
    }
    public boolean isHidden(){
        return hidden;
    }
    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }

    public LockState getLockState() {
        return lockState;
    }

    public void setLockState(LockState lockState) {
        this.lockState = lockState;
    }


    public boolean equals(Object object){
        if (object == this){
            return true;
        }

        if (object instanceof LockableExit){
            if (!super.equals(object)){
                return false;
            }
            LockableExit e = (LockableExit) object;
            if (ObjectsUtil.notEqual(location, e.location)){
                return false;
            }
            if (lockState != e.lockState){
                return false;
            }
            if (hidden != e.hidden){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
    public int hashCode(){
        return ObjectsUtil.getHash(super.hashCode(), ObjectsUtil.DEFAULT_PRIME, location, hidden, lockState);
    }
    /**
     * Returns the Room Description if set, or else the name of the LockableExit
     */
    @JsonIgnore
    public String getDescription(){//TODO
        return getRoomDescription() != null? getRoomDescription(): getName();
    }





}
