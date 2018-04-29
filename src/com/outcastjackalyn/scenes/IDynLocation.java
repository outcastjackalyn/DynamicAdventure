package com.outcastjackalyn.scenes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.outcastjackalyn.objects.IFurniture;
import com.outcastjackalyn.utils.DynamicDescriptionUtil;
import jjcard.text.game.IItem;
import jjcard.text.game.IMob;
import jjcard.text.game.impl.Exit;

import java.util.Map;

/**
 * An IDynLocation contains a collection of IItems, IMobs, and IDynExits and methods to use them.
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "@class")
public interface IDynLocation extends Comparable<IDynLocation> {

    /**
     * Gets the name of the Location. Should only be set once.
     * @return name
     */
    public String getName();
    public String getDescription();
    public Map<String, IItem> getInventory();
    public Map<String, IFurniture> getFurnishings();
    public Map<String, IMob> getMobs();
    public Map<String, IDynExit> getExits();


    public void setInventory(Map<String, IItem> inventory);
    public void setFurnishings(Map<String, IFurniture> furnishings);
    public void setMobs(Map<String, IMob> mobs);
    public void setExits(Map<String, IDynExit> exits);
    /**
     * Adds <i>item</i> to Inventory map.
     * @param item
     * @return previous IItem associated with it's key
     */
    public IItem addItem(IItem item);
    public IItem removeItem(String key);
    public boolean containsItem(String key);
    public IItem getItem(String key);
    /**
     * Adds <i>furniture</i> to Furnishings map.
     * @param furniture
     * @return previous IFurniture associated with it's key
     */
    public IFurniture addFurniture(IFurniture furniture);
    public IFurniture removeFurniture(String key);
    public boolean containsFurniture(String key);
    public IFurniture getFurniture(String key);
    /**
     * Adds <i>mob</i> to Mobs map.
     * @param mob
     * @return previous IMob associated with it's key
     */
    public IMob addMob(IMob mob);
    public IMob removeMob(String key);
    public boolean containsMob(String key);
    public IMob getMob(String key);
    /**
     * Adds exit to Exits map
     * @param exit
     * @return previous exit associated with dir key of exit
     */
    public IDynExit addExit(IDynExit exit);
    /**
     * Removes the exit with the given direction and returns it.
     * @param dir
     * @return removed IDynExit
     */
    public IDynExit removeExit(String dir);
    public boolean containsOpenExit(String dir);
    public boolean containsExit(String dir);
    public IDynExit getExit(String dir);
    /**
     * Creates IDynExit for given room and direction and adds it to the Exits map.
     * <br> Default: Creates a {@link Exit}
     * @param dir
     * @param room
     * @return previous exit associated with dir
     */
    default public IDynExit addExit(String dir, IDynLocation room){
        LockableExit exit = new LockableExit.Builder().name(dir).location(room).build();
        return addExit(exit);
    }

    /**
     * returns Location corresponding to directions
     * <br> Default: Calls {@link #getExit(String)} and calls {@link Exit#getLocation()} on it
     * @param dir
     * @return IDynLocation
     */
    public default IDynLocation getExitLocation(String dir){
        IDynExit exit = getExit(dir);
        return exit == null? null: exit.getLocation();
    }

    /**
     *
     * @return room description, description of items and mobs in room, and exits. 
     * <br> Default: Calls 
     * <blockquote>
     * {@link DynamicDescriptionUtil}.{@link DynamicDescriptionUtil#showRoom(IDynLocation) showRoom(<i>this</i>)}
     * </blockquote>
     */
    default public String showRoom(){
        return DynamicDescriptionUtil.showRoom(this);
    }
    /**
     * Gets the description for the exits. Must be non-null
     * <br> Default: Calls 
     * <blockquote>
     * {@link DynamicDescriptionUtil}.{@link DynamicDescriptionUtil#getConcealableNames(Map, boolean) getConcealableNames(getExits(), true)}
     * </blockquote>
     * @return
     */
    @JsonIgnore
    default public String getExitsDescriptions(){
        return DynamicDescriptionUtil.getConcealableNames(getExits(), true);
    }
    /**
     * Gets the description for all the furniture. Must be non-null
     * <br> Default: Calls 
     * <blockquote>
     * {@link DynamicDescriptionUtil}.{@link DynamicDescriptionUtil#getConcealableNames(Map, boolean) getConcealableNames(getFurnishings(), true)}
     * </blockquote>
     * @return
     */
    @JsonIgnore
    default public String getFurnishingsDescriptions(){
        return DynamicDescriptionUtil.getConceableRoomDescriptions(getFurnishings(), true);
    }

    /**
     * Gets the description for each item in the inventory. Must be non-null
     * <br> Default: Calls
     * <blockquote>
     * {@link DynamicDescriptionUtil}.{@link DynamicDescriptionUtil#getConcealableNames(Map, boolean) getConcealableNames(getInventory(), true)}
     * </blockquote>
     * @return
     */
    @JsonIgnore
    default public String getInventoryDescriptions(){
        return DynamicDescriptionUtil.getConceableRoomDescriptions(getInventory(), true);
    }
    /**
     * Gets the description for each mob. Must be non-null
     * <br> Default: Calls 
     * <blockquote>
     * {@link DynamicDescriptionUtil}.{@link DynamicDescriptionUtil#getConcealableNames(Map, boolean) getConcealableNames(getMobs(), true)}
     * </blockquote>
     * @return
     */
    @JsonIgnore
    default public String getMobDescriptions(){
        return DynamicDescriptionUtil.getConceableRoomDescriptions(getMobs(), true);
    }

}
