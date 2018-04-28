package com.outcastjackalyn.game;
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

    private static ArrayList<Mob> mobs;
    private static ArrayList<Item> items;
    private static ArrayList<DynLocation> DynLocations;
    private static Player player;
    private static DynLocation start;

    private static ITextParser<BasicTextTokenType> parser;


    public static ArrayList<Mob> getMobs() { return mobs;}
    public static void setMobs(ArrayList<Mob> mobs) { mobs = mobs;}
    public static void addMob(Mob mob) { mobs.add(mob);}
    public static ArrayList<Item> getItems() { return items;}
    public static void setItems(ArrayList<Item> items) { items = items;}
    public static ArrayList<DynLocation> getDynLocations() { return DynLocations;}
    public static void setDynLocations(ArrayList<DynLocation> DynLocations) { DynLocations = DynLocations;}
    public static Player getPlayer() { return player;}
    public static DynLocation getStart() { return start;}


    public static ITextParser<BasicTextTokenType> getParser(){ //not written by me
        if (parser == null){
            parser = new BasicTextParser<>();
            TextDictionary<BasicTextTokenType> dictionary = new TextDictionary<>(
                    BasicTextTokenType.values())
                    .putAll(BasicTextTokenType.DIRECTION, Exit.DEFAULT_VALUES)
                    .putAll(BasicTextTokenType.ITEM, "coin", "item", "apple")
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


        player = new Player.Builder().name("player1").maxHealth(50).health(50).defense(8).attack(5).build();
        start = new DynLocation("entry room", "A barren room.");

        Item item = new Item.Builder().name("apple").roomDescription("There is an apple on the floor.").viewDescription("It is shiny and red.").build();
        start.addItem(item);
        DynLocation hallway = new DynLocation("hallway", "A long hallway with one torch.");
        start.addExit("NORTH", hallway);
        hallway.addExit(LockableExit.SOUTH.getWithLocation(start));

        Mob mob = new Mob.Builder().name("Goblin").health(10).defense(1).attack(4).hostile(true).build();
        mob.setRoomDescription("it's a goblin probably.");
        mob.setViewDescription("You can tell it's a goblin because it's green and broccoli usually doesn't try to kill you.");
        hallway.addMob(mob);

    }


    public void newRoom()  {

    }



}
