package com.outcastjackalyn.scenes;


import static com.outcastjackalyn.Main.gameData;
import static jjcard.text.game.util.MapUtil.newHashMap;
import static jjcard.text.game.util.ObjectsUtil.notEqual;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.outcastjackalyn.objects.IFurniture;
import com.outcastjackalyn.utils.AdjectiveUtil;
import com.outcastjackalyn.utils.DynamicDescriptionUtil;
import jjcard.text.game.IItem;
import com.outcastjackalyn.scenes.IDynLocation;
import com.outcastjackalyn.scenes.IDynExit;
import jjcard.text.game.IMob;
import jjcard.text.game.util.DescriptionUtil;
import jjcard.text.game.util.MapUtil;
import jjcard.text.game.util.ObjectsUtil;

/**
 * Basic class to implement {@link IDynLocation}
 *
 */
public class DynLocation implements IDynLocation {
    @JsonIgnore
    private static final MapUtil MAP_UTIL = MapUtil.getInstance();
    @JsonProperty("name")
    private String name;
    @JsonProperty("descrip")
    private String description;
    @JsonProperty("invdescrip")
    private String inventoryDescription;
    @JsonProperty("inventory")
    private Map<String, IItem >inventory;
    @JsonProperty("furnishings")
    private Map<String, IFurniture>furnishings;
    @JsonProperty("mobs")
    private Map<String, IMob> roomMob;
    @JsonProperty("exits")
    private Map<String, IDynExit> exits;

    public DynLocation(@JsonProperty("name") String name){
        this(name, "");
    }
    public DynLocation(String name, String description){
        this.name = name;
        this.description = description;
        this.inventoryDescription = "on the floor";
        inventory = newHashMap();
        furnishings = newHashMap();
        roomMob =  newHashMap();
        exits = newHashMap();
    }
    public DynLocation(String name, String description, String inventoryDescription){
        this.name = name;
        this.description = description;
        this.inventoryDescription = inventoryDescription;
        inventory = newHashMap();
        furnishings = newHashMap();
        roomMob =  newHashMap();
        exits = newHashMap();
    }
    public DynLocation(String name, String description, String inventoryDescription, Map<String, IItem> inventory){
        this.name = name;
        this.description = description;
        this.inventoryDescription = inventoryDescription;
        setInventory(inventory);
        furnishings = newHashMap();
        roomMob =  newHashMap();
        exits = newHashMap();
    }
    public DynLocation(String name, String description, String inventoryDescription, Map<String, IItem> inventory, Map<String, IFurniture> furnishings){
        this.name = name;
        this.description = description;
        this.inventoryDescription = inventoryDescription;
        setInventory(inventory);
        setFurnishings(furnishings);
        roomMob =  newHashMap();
        exits = newHashMap();
    }
    public DynLocation(String name, String description, String inventoryDescription, Map<String, IItem> inventory, Map<String, IFurniture> furnishings, Map<String, IMob>  mobs){
        this.name = name;
        this.description = description;
        this.inventoryDescription = inventoryDescription;
        setInventory(inventory);
        setFurnishings(furnishings);
        setMobs(mobs);
        exits = newHashMap();
    }
    @JsonProperty("name")
    public String getName(){
        return name;
    }
    @JsonProperty("descrip")
    public String getDescription(){
        return description;
    }
    @JsonProperty("invdescrip")
    public String getInventoryDescription(){
        return inventoryDescription;
    }
    @JsonProperty("inventory")
    public Map<String, IItem> getInventory(){
        return inventory;
    }
    @JsonProperty("furnishings")
    public Map<String, IFurniture> getFurnishings(){
        return furnishings;
    }
    @JsonProperty("mobs")
    public Map<String, IMob> getMobs() {
        return roomMob;
    }
    @JsonProperty("exits")
    public Map<String, IDynExit> getExits() {
        return exits;
    }
    public IItem addItem(IItem add){
        return MAP_UTIL.addItemToMap(inventory, add);
    }
    @JsonProperty("inventory")
    public void setInventory(Map<String, IItem> inventory){
        this.inventory = MapUtil.getMapOrNew(inventory);
    }
    public IFurniture addFurniture(IFurniture add){
        return MAP_UTIL.addItemToMap(furnishings, add);
    }
    @JsonProperty("furnishings")
    public void setFurnishings(Map<String, IFurniture> furnishings) {
        this.furnishings = MapUtil.getMapOrNew(furnishings);
    }
    @JsonProperty("mobs")
    public void setMobs(Map<String, IMob> mobs){
        this.roomMob = MapUtil.getMapOrNew(mobs);
    }
    @JsonProperty("exits")
    public void setExits(Map<String, IDynExit> exits){
        this.exits = MapUtil.getMapOrNew(exits);
    }
    public IItem removeItem(String key){
        return MAP_UTIL.removeItemFromMap(inventory, key);
    }
    public boolean containsItem(String key){
        return MAP_UTIL.containsKey(inventory,key);
    }
    public IFurniture removeFurniture(String key) {
        return MAP_UTIL.removeItemFromMap(furnishings, key);
    }
    public boolean containsFurniture(String key){
        return MAP_UTIL.containsKey(furnishings,key);
    }
    public IMob addMob(IMob m){
        return MAP_UTIL.addItemToMap(roomMob, m);
    }
    public IMob removeMob(String key){
        return MAP_UTIL.removeItemFromMap(roomMob, key);
    }
    public boolean containsMob(String m){
        return MAP_UTIL.containsKey(roomMob, m);
    }
    /**
     * adds Exit with given String and location
     * @param dir
     * @param room
     */
    public IDynExit addExit(String dir, IDynLocation room){
        LockableExit exit = new LockableExit.Builder().name(dir).location(room).build();
        return MAP_UTIL.addItemToMap(exits, exit);
    }

    public IDynExit addExit(IDynExit exit){
        return MAP_UTIL.addItemToMap(exits, exit);
    }
    /**
     * removes Exit under that String
     * @param dir
     * @return
     */
    public IDynExit removeExit(String dir){
        return MAP_UTIL.removeItemFromMap(exits, dir);
    }

    @JsonIgnore
    public IDynExit getExit(String dir){
        return MAP_UTIL.getItemFromMap(exits, dir);
    }

    public IDynExit getOnlyExit() {
        IDynExit e  = (IDynExit) exits.values().toArray()[0];
        return e;
    }


    @JsonIgnore
    public IMob getMob(String key){
        return MAP_UTIL.getItemFromMap(roomMob,key);
    }
    @JsonIgnore
    public IItem getItem(String key){
        return MAP_UTIL.getItemFromMap(inventory, key);
    }
    @JsonIgnore
    public IFurniture getFurniture(String key){
        return MAP_UTIL.getItemFromMap(furnishings, key);
    }

    public boolean containsExit(String dir){
        return MAP_UTIL.containsKey(exits, dir);
    }

    public boolean containsOpenExit(String dir) {
        boolean bool = false;
        dir = dir.toUpperCase(Locale.getDefault());
        if(exits.containsKey(dir)){ // check if it contains the exit
            for(IDynExit exit : exits.values()) { // select the exit
                if(exit.getName().equals(dir)) {
                    if(exit.isOpen()) { // check if the exit is open
                        bool = true;
                    }
                }
            }
        }
        return bool;
    }
    @JsonProperty("descrip")
    public void setDescription(String descrip){
        description = descrip;
    }
    @JsonProperty("invdescrip")
    public void setInventoryDescription(String invdescrip){
        inventoryDescription = invdescrip;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(IDynLocation other) {
        int compare = getName().compareTo(other.getName());
        if (compare == 0){
            compare = ObjectsUtil.compareTo(description,other.getDescription());
        }
        return compare;
    }
    public String getExitsDescriptions() {
        return DynamicDescriptionUtil.getConcealableRoomDescriptions(exits, true);
    }
    public String getHiddenExitsDescriptions(){
        return DynamicDescriptionUtil.getConcealableHiddenDescriptions(exits);
    }
    public String getInventoryDescriptions() {
        String str = DynamicDescriptionUtil.getConcealableRoomDescriptions(inventory, true);
        if(!isEmpty()) {
            str = AdjectiveUtil.updateText(str, gameData.getSeed(), getInventoryDescription());
        }
        return str;
    }


    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public String getFurnishingsDescriptions() {
        String str = DynamicDescriptionUtil.getConcealableRoomDescriptions(furnishings, true);
        for(IFurniture furniture : furnishings.values()) {
            if(furniture.isOpen()) {
                if (!furniture.isEmpty()) {
                    str = str + ": " + DynamicDescriptionUtil.getConcealableRoomDescriptions(furniture.getInventory(), true);
                    str = AdjectiveUtil.updateText(str, gameData.getSeed(), furniture.getInventoryDescription());
                }
            }
        }
        return str;
    }
    public String getMobDescriptions(){
        return DynamicDescriptionUtil.getConcealableRoomDescriptions(roomMob, true);
    }
    /**
     * Checks that the name and description are equals. uses {@link ObjectsUtil#equalKeys(Map, Map)}
     * when it checks if the inventory, mobs, and exits are equal.
     */
    public boolean equals(Object o){
        if (o == this){
            return true;
        }

        if (o instanceof DynLocation){
            DynLocation l = (DynLocation) o;
            if (notEqual(name, l.name)){
                return false;
            }
            if (notEqual(description, l.description)){
                return false;
            }
            if (notEqual(inventoryDescription, l.inventoryDescription)){
                return false;
            }
            if (ObjectsUtil.notEqualKeys(inventory, l.inventory)){
                return false;
            }
            if (ObjectsUtil.notEqualKeys(furnishings, l.furnishings)){
                return false;
            }
            if (ObjectsUtil.notEqualKeys(roomMob, l.roomMob)){
                return false;
            }
            if (ObjectsUtil.notEqualKeys(exits, l.exits)){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
    /**
     * Gets the hash code of values in Location, using {@link ObjectsUtil#getKeysHash(Map)}
     * for getting hash of exits, inventory, and mobs
     */
    public int hashCode(){
        int start = 1;
        start = start * ObjectsUtil.DEFAULT_PRIME  + ObjectsUtil.getKeysHash(exits);
        start = start * ObjectsUtil.DEFAULT_PRIME  + ObjectsUtil.getKeysHash(inventory);
        start = start * ObjectsUtil.DEFAULT_PRIME  + ObjectsUtil.getKeysHash(furnishings);
        start = start * ObjectsUtil.DEFAULT_PRIME  + ObjectsUtil.getKeysHash(roomMob);
        return ObjectsUtil.getHashWithStart(start, ObjectsUtil.DEFAULT_PRIME, name, description, inventoryDescription);
    }



   /* public void setDescription(String description) {
        this.description = description;
    }*/
}
