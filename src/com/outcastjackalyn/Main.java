package com.outcastjackalyn;

import com.outcastjackalyn.game.DynamicTextGame;
import com.outcastjackalyn.game.GameData;
import com.outcastjackalyn.scenes.RoomManager;
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


    public static DynamicTextGame game;
    public static GameData gameData;
    public static RoomManager roomManager;

    public static JFrame frame = new JFrame("Dynamic Text Adventure");
    public static JLabel outputFeed = new JLabel("<html>Welcome...<br>");


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
        //JLabel outputFeed = new JLabel("<html>Welcome...<br>");
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


        gameData = new GameData();
        roomManager = new RoomManager(gameData);


        game = new DynamicTextGame(gameData);
        game.setTextParser(game.gameData.getParser());


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
                outputStr = outputStr + str;
                printedLines = count(outputStr, "<br>");
                if(printedLines > Line_Max) {
                    outputStr = cycle(outputStr);
                }
                updateDisplay();
                printedLines = count(outputStr, "<br>");
                System.out.println("Line "+ printedLines + " printed: \"" + str + "\"");
            }
        });

        entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(entry.getText().length() > 0) {
                    game.setInString(entry.getText());
                    game.getOutput().println(">>" + entry.getText());
                    entry.setText("");
                    gameData.setSeedValue(System.currentTimeMillis());
                    System.out.println("Seed: " + gameData.getSeed());
                }
                else {
                    updateDisplay();
                }
            }
        });


    }

    private static void updateDisplay() {
        outputFeed.setText("<html>" + outputStr);
    }


    private static String removeTopLine(String str, int lineEnd) {
        String s = str.substring(lineEnd, str.length());
        return s;
    }


    private static String cycle(String string) {
        String str = string;
        int endLine;
        for(int i = 0; i < printedLines - Line_Max; i++)
        {
            endLine = string.indexOf("<br>");
            str = removeTopLine(string, endLine + 4);
            string = str;
        }
        return str;
    }


    public static int count(String string, String substring)
    {
        int count = 0;
        int idx = 0;
        int last = 0;
        int extra = 0;
        while ((idx = string.indexOf(substring, idx)) >= 0)
        {
            extra = (idx - last) / Char_Max;
            if(extra > 0) {
                count += extra;
            }
            last = idx;
            idx++;
            count++;
        }
        return count;
    }


    public static void main(String[] args) {
        setUp();
        game.play();
    }







}
