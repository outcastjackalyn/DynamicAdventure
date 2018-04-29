package com.outcastjackalyn;

import com.outcastjackalyn.game.DynamicTextGame;
import com.outcastjackalyn.game.GameData;
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


        gameData = new GameData();



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
                    game.getOuput().println(">>" + entry.getText());
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







}
