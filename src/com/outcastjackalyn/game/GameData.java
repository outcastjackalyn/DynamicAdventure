package com.outcastjackalyn.game;

import com.outcastjackalyn.objects.NPCs.NPCs;
import com.outcastjackalyn.objects.furniture.Furniture;
import com.outcastjackalyn.objects.furniture.Furnitures;
import com.outcastjackalyn.objects.items.Items;
import com.outcastjackalyn.objects.player.PlayerData;
import com.outcastjackalyn.scenes.DynLocation;
import com.outcastjackalyn.scenes.Exits;
import com.outcastjackalyn.scenes.LockableExit;
import com.outcastjackalyn.scenes.Rooms;
import jjcard.text.game.impl.*;
import jjcard.text.game.parser.ITextParser;
import jjcard.text.game.parser.impl.BasicTextParser;
import jjcard.text.game.parser.impl.BasicTextTokenType;
import jjcard.text.game.parser.impl.TextDictionary;
import jjcard.text.game.util.MapUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class GameData {

    private long seedValue = System.currentTimeMillis();
    private String seed = "00000000000000000000000000";

    private ArrayList<Mob> mobs;
    private ArrayList<Item> items;
    private ArrayList<DynLocation> locations;
    private ArrayList<Furniture> furnishings;
    private Map<LockableExit, LockableExit> exits;
    private ArrayList<String> associated;
    private Player player;
    private DynLocation start;

    private ITextParser<BasicTextTokenType> parser;

    public ArrayList<Mob> getMobs() { return mobs; }
    public void setMobs(ArrayList<Mob> mobs) { this.mobs = mobs; }
    public void addMob(Mob mob) { mobs.add(mob); }

    public ArrayList<String> getAssociated() {
        return associated;
    }
    public void setAssociated(ArrayList<String> associated) {
        this.associated = associated;
    }
    public void addAssociated(String associated) { this.associated.add(associated); }
    public void removeAssociated(String associated) { this.associated.remove(associated); }

    public ArrayList<Item> getItems() { return items; }
    public void setItems(ArrayList<Item> items) { this.items = items; }
    public void addItem(Item item) { items.add(item); }

    public ArrayList<Furniture> getFurnishings() { return furnishings; }
    public void setFurnishings(ArrayList<Furniture> furnishings) { this.furnishings = furnishings; }
    public void addFurniture(Furniture furniture) { furnishings.add(furniture); }

    public ArrayList<DynLocation> getLocations() { return locations; }
    public void setLocations(ArrayList<DynLocation> locations) { this.locations = locations; }
    public void addLocation(DynLocation location) { locations.add(location); }

    public Map<LockableExit, LockableExit> getExits() { return exits; }
    public void setExits(Map<LockableExit, LockableExit>  exits) { this.exits = exits; }
    public void addExit(LockableExit exit, LockableExit entrance) { exits.put(exit, entrance); }

    public Player getPlayer() { return player; }
    public DynLocation getStart() { return start; }

    public String getSeed() {
        return seed;
    }
    public void setSeed(String seed) {
        this.seed = seed;
    }

    public long getSeedValue() { return seedValue; }
    public void setSeedValue(long seedValue) {
        this.seedValue += seedValue;
        String seed = String.valueOf(this.seedValue);
        String subStr = String.valueOf(seedValue);
        subStr = subStr.substring(subStr.length() - 2);
        seed = subStr.concat(seed);
        //this.seedValue = Long.valueOf(seed);
        setSeed(seed);
    }

    public Set<String> getDictionary() {
        Set<String> strings = parser.getTextDictionary().keySet();
        return strings;
    }
    public ITextParser<BasicTextTokenType> setParser(){
        if (parser == null) {
            parser = new BasicTextParser<>();
            TextDictionary<BasicTextTokenType> dictionary = new TextDictionary<>(BasicTextTokenType.values())
                    .putAll(BasicTextTokenType.DIRECTION, LockableExit.DEFAULT_VALUES)
                    //I have re-purposed a number of BasicTextTokenTypes that i don't intend to use for the time-being
                    // without needing to change or duplicate the file from the library
                    // EQUIP - is used in place of LOCK
                    .putAll(BasicTextTokenType.EQUIP, "lock", "close")
                    // UNEQUIP - is used in place of UNLOCK
                    .putAll(BasicTextTokenType.UNEQUIP, "unlock", "open")
                    //HEALTH - is used in place CHEAT (i.e. display all words in the dictionary.
                    .putAll(BasicTextTokenType.HEALTH, "cheat")
                    .putAll(BasicTextTokenType.LOOK, "examine")
                    .putAll(BasicTextTokenType.GET, "take");

            for(Items item : Items.values()) {
                dictionary.putAll(BasicTextTokenType.ITEM, item.getName());
            }
            for(Furnitures furniture : Furnitures.values()) {
                // WORDS - is used to indicate FURNITURE and hidden EXITS
                dictionary.putAll(BasicTextTokenType.WORDS, furniture.getName());
            }
            for(Exits exit : Exits.values()) {
                // WORDS - is used to indicate FURNITURE and hidden EXITS
                dictionary.putAll(BasicTextTokenType.WORDS, exit.getHiddenName());
            }
            for(NPCs npc : NPCs.values()) {
                if(npc.isHostile()) {
                    dictionary.putAll(BasicTextTokenType.ENEMY, npc.getName());
                } else {
                    dictionary.putAll(BasicTextTokenType.NPC, npc.getName());
                }
            }

            parser.setTextDictionary(dictionary);
        }
        return parser;
    }



    public GameData() {

        setMobs(new ArrayList<Mob>());
        setItems(new ArrayList<Item>());
        setFurnishings(new ArrayList<Furniture>());
        setLocations(new ArrayList<DynLocation>());
        setAssociated(new ArrayList<String>());
        setExits(MapUtil.newHashMap());

        player = new Player.Builder().name("player1").maxHealth(50).health(50).defense(8).attack(5).build();
        start = Rooms.build(new DynLocation("", ""), Rooms.START);
        addLocation(start);


        Item apple = new Item.Builder().name("apple").roomDescription("There is an apple on the table").viewDescription("It is shiny and red.").build();
        //start.addItem(apple);
        addItem(apple);

        Furniture table = new Furniture.Builder().name("table").roomDescription("There is an circular wooden table").viewDescription("This is a table only big enough for two to eat at.").movable(false).addItem(apple).build();
        start.addFurniture(table);
        addFurniture(table);

        DynLocation hallway = new DynLocation("empty", "A long hallway with one torch.");

        start.addExit("NORTH", hallway);
        hallway.addExit(LockableExit.SOUTH.getWithLocation(start));

        addLocation(hallway);


    }


}
