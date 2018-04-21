package com.outcastjackalyn;

import com.outcastjackalyn.game.DynamicTextGame;
import jjcard.text.game.impl.*;
import jjcard.text.game.parser.ITextParser;
import jjcard.text.game.parser.impl.BasicTextParser;
import jjcard.text.game.parser.impl.BasicTextTokenType;
import jjcard.text.game.parser.impl.TextDictionary;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {




 //   public Location start = new Location("Start", "This is where it begins") ;
   // public Player.Builder builder = new Player.Builder();
  //  public Player player = new Player("player");

  //  public DynamicTextGame dTGame = new DynamicTextGame(start, player);

    public static DynamicTextGame game;
    public static Player player;
    public static Location local;
    public static Location hallway;
    public static Mob mob;
    public static ITextParser<BasicTextTokenType> parser;

    public static JFrame frame = new JFrame("Dynamic Text Adventure");


    public static int printedLines = 0;
    private static int Char_Max = 67;
    private static int Line_Max = 20;
    static String outputStr = "";


    public static void setUp() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int height = 500;
        int width = 500;
        JPanel gui = new JPanel(new BorderLayout());
        gui.setBorder(new EmptyBorder(5,5,5,5));
        JLabel outputFeed = new JLabel("<html>Welcome...<br>");
        JTextField entry = new JTextField(100);
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, entry.getFont().getSize());
        gui.setBackground(Color.BLACK);
        outputFeed.setForeground(Color.WHITE);
        entry.setFont(font);
        outputFeed.setFont(font);
        outputFeed.setSize(width, height - entry.getHeight());
        gui.add(outputFeed, BorderLayout.NORTH);
        gui.add(entry, BorderLayout.SOUTH);
        //gui.add(new JScrollPane(outputFeed,
             //   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.NORTH);

        /*frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });*/

        frame.add(gui);
        frame.setLocationByPlatform(true);
        frame.setSize(width, height);
        frame.setVisible(true);

        player = new Player.Builder().name("player1").maxHealth(50).health(50).defense(8).attack(5).build();
        local = new Location("entry room", "A barren room.");
        game = new DynamicTextGame(local, player);
        game.setTextParser(getParser());

        Item item = new Item.Builder().name("apple").roomDescription("There is an apple on the floor.").build();
        local.addItem(item);
        hallway = new Location("hallway", "A long hallway with one torch.");
        local.addExit("NORTH", hallway);
        hallway.addExit(Exit.SOUTH.getWithLocation(local));

        mob = new Mob.Builder().name("Goblin").health(10).defense(1).attack(4).build();
        mob.setViewDescription("You can tell it's a goblin because it's green and broccoli usually doesn't try to kill you.");
        hallway.addMob(mob);

        game.setOutput(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        }) {
            @Override
            public void println(String str) {
                process(str + "<br>");
            }

            @Override
            public void print(String str) {
                process(str);
            }

            private void process(String str) {

                printedLines++;

                if(printedLines > Line_Max) {
                    outputStr = cycleText(outputStr);
                }
                if(str.indexOf("<br>") > Char_Max) {
                    printedLines++;
                    if(printedLines > Line_Max) {
                        outputStr = cycleText(outputStr);
                    }
                }
                outputStr = outputStr + str;
                //checkPrintedLines(outputStr);
                outputFeed.setText("<html>" + outputStr);
                System.out.println("Line "+ printedLines + " printed: \"" + str + "\"");
            }
        });

        entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(entry.getText().length() > 0) {
                    game.setInString(entry.getText());
                    game.getOuput().println(">" + entry.getText());
                    entry.setText("");
                }
            }
        });


    }


    private static String removeTopLine(String str, int lineEnd) {
        String s = str.substring(lineEnd, str.length());
        return s;
    }



    private static String cycleText(String str){
        int i = 1;
        int endLine = outputStr.indexOf("<br>");
        str = removeTopLine(outputStr, endLine + 4);
        if(endLine > Char_Max) {
            i++;
        }
        printedLines -= i;
       // checkPrintedLines(str);
       /* if(printedLines > Line_Max) {
            cycleText(str);
        }*/

        return str;
    }


    public static void main(String[] args) {
        setUp();
        game.play();
    }







    private static ITextParser<BasicTextTokenType> getParser(){ //not written by me
        if (parser == null){
            parser = new BasicTextParser<>();
            TextDictionary<BasicTextTokenType> dictionary = new TextDictionary<>(
                    BasicTextTokenType.values()).putAll(BasicTextTokenType.DIRECTION, Exit.DEFAULT_VALUES)
                    .putAll(BasicTextTokenType.ITEM, "coin", "item", "apple").putAll(BasicTextTokenType.WEAPON, "shank")
                    .putAll(BasicTextTokenType.ENEMY, "goblin").putAll(BasicTextTokenType.ARMOR, "wool");
            parser.setTextDictionary(dictionary);
            //TODO make this less tedious..
        }
        return parser;
    }
}
