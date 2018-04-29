package com.outcastjackalyn.game;
import com.outcastjackalyn.objects.furniture.Furniture;
import com.outcastjackalyn.objects.player.PlayerData;
import com.outcastjackalyn.scenes.DynLocation;
import com.outcastjackalyn.scenes.LockableExit;
import jjcard.text.game.impl.*;
import jjcard.text.game.parser.ITextParser;
import jjcard.text.game.parser.impl.BasicTextParser;
import jjcard.text.game.parser.impl.BasicTextTokenType;
import jjcard.text.game.parser.impl.TextDictionary;

import java.util.ArrayList;

public class GameData {

    private String seed = "000000";

    private ArrayList<Mob> mobs;
    private ArrayList<Item> items;
    private ArrayList<DynLocation> locations;
    private ArrayList<Furniture> furnishings;
    private Player player;
    private DynLocation start;

    private ITextParser<BasicTextTokenType> parser;


    public ArrayList<Mob> getMobs() { return mobs; }
    public void setMobs(ArrayList<Mob> mobs) { this.mobs = mobs; }
    public void addMob(Mob mob) { mobs.add(mob); }

    public ArrayList<Item> getItems() { return items; }
    public void setItems(ArrayList<Item> items) { this.items = items; }
    public void addItem(Item item) { items.add(item); }

    public ArrayList<Furniture> getFurnishings() { return furnishings; }
    public void setFurnishings(ArrayList<Furniture> furnishings) { this.furnishings = furnishings; }
    public void addFurniture(Furniture furniture) { furnishings.add(furniture); }

    public ArrayList<DynLocation> getLocations() { return locations; }
    public void setLocations(ArrayList<DynLocation> locations) { this.locations = locations; }
    public void addLocation(DynLocation location) { locations.add(location); }

    public Player getPlayer() { return player;}
    public DynLocation getStart() { return start;}


    public ITextParser<BasicTextTokenType> getParser(){ //not written by me
        if (parser == null){
            parser = new BasicTextParser<>();
            TextDictionary<BasicTextTokenType> dictionary = new TextDictionary<>(
                    BasicTextTokenType.values())
                    .putAll(BasicTextTokenType.DIRECTION, LockableExit.DEFAULT_VALUES)
                    .putAll(BasicTextTokenType.ITEM, "coin", "item", "apple")
                    //I have re-purposed a number of BasicTextTokenTypes that i don't intend to use for the time-being
                    // without needing to change or duplicate the file
                    // WORDS - is used to indicate FURNITURE
                    .putAll(BasicTextTokenType.WORDS, "table")
                    // EQUIP - is used in place of LOCK
                    .putAll(BasicTextTokenType.EQUIP, "lock", "close")
                    // UNEQUIP - is used in place of UNLOCK
                    .putAll(BasicTextTokenType.UNEQUIP, "unlock", "open")
                    .putAll(BasicTextTokenType.WEAPON, "shank")
                    .putAll(BasicTextTokenType.ENEMY, "goblin")
                    .putAll(BasicTextTokenType.ARMOR, "wool")
                    .putAll(BasicTextTokenType.GET, "take");
            parser.setTextDictionary(dictionary);
            //TODO make this less tedious..
        }
        return parser;
    }



    public GameData() {

        setMobs(new ArrayList<Mob>());
        setItems(new ArrayList<Item>());
        setFurnishings(new ArrayList<Furniture>());
        setLocations(new ArrayList<DynLocation>());

        player = new Player.Builder().name("player1").maxHealth(50).health(50).defense(8).attack(5).build();
        start = new DynLocation("entry room", "A dimly lit room.");
        addLocation(start);


        Item apple = new Item.Builder().name("apple").roomDescription("There is an apple on the table.").viewDescription("It is shiny and red.").build();
        //start.addItem(apple);
        addItem(apple);

        Furniture table = new Furniture.Builder().name("table").roomDescription("There is an circular wooden table.").viewDescription("This is a table only big enough for two to eat at.").movable(false).addItem(apple).build();
        start.addFurniture(table);
        addFurniture(table);

        DynLocation hallway = new DynLocation("hallway", "A long hallway with one torch.");
        start.addExit("NORTH", hallway);
        hallway.addExit(LockableExit.SOUTH.getWithLocation(start));
        addLocation(hallway);

        Mob mob = new Mob.Builder().name("Goblin").health(10).defense(1).attack(4).hostile(true).build();
        mob.setRoomDescription("it's a goblin probably.");
        mob.setViewDescription("You can tell it's a goblin because it's green and broccoli usually doesn't try to kill you.");
        hallway.addMob(mob);
        addMob(mob);

    }





}
