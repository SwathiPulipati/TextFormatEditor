import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.AttributeSet.ColorAttribute;
import javax.swing.border.*;

import java.awt.GraphicsEnvironment;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.io.*;

public class GUITask extends JPanel implements ActionListener{

    JFrame frame;
    JMenuBar menuBar;
    JButton reset, north, south, east, west;
    JMenu fontMenu, fontSizeMenu, textColorMenu, textBgColorMenu, outlineColorMenu; 
    ArrayList<JMenuItem> fontItems, sizeItems, textColorItems, bgColorItems, outlineItems;
    Font[] fonts;
    String[] fontStrings, colorStrings;
    Color[] colors;

    JTextArea textArea;

    int curSize = 12;
    Font curFont = new Font("Arial", Font.PLAIN, curSize);  

    public GUITask(){
        frame = new JFrame("GUI Task");
		frame.add(this);
		frame.setSize(1200,700);

        createMenuBar();
        frame.add(menuBar, BorderLayout.NORTH);
        textArea = new JTextArea();
        frame.add(textArea,BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void createMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1,10));

        reset = new JButton("Reset");
        north = new JButton("North");
        south = new JButton("South");
        east = new JButton("East");
        west = new JButton("West");
        

        fontMenu = new JMenu("Font");
        fillFontMenu();
        fontSizeMenu = new JMenu("Font Size");
        fillSizeMenu();
        textColorMenu = new JMenu("Text Color");
        textBgColorMenu = new JMenu("Background Color");
        outlineColorMenu = new JMenu("Outline Color");
        fillColorMenu(); 

        reset.addActionListener(this);
        north.addActionListener(this);
        south.addActionListener(this);
        east.addActionListener(this);
        west.addActionListener(this);

        fontMenu.addActionListener(this);
        fontSizeMenu.addActionListener(this);
        textColorMenu.addActionListener(this);
        textBgColorMenu.addActionListener(this);
        outlineColorMenu.addActionListener(this);

        menuBar.add(reset);
        menuBar.add(north);
        menuBar.add(south);
        menuBar.add(east);
        menuBar.add(west);

        menuBar.add(fontMenu);
        menuBar.add(fontSizeMenu);
        menuBar.add(textColorMenu);
        menuBar.add(textBgColorMenu);
        menuBar.add(outlineColorMenu);


        repaint();
    }

    public void fillFontMenu(){
        fontStrings = new String[]{"Calibri", "Courier New", "Dialog", "Helvetica", "Times New Roman"};
        fonts = new Font[fontStrings.length];
        fontItems = new ArrayList<JMenuItem>();
        resetFonts();
    }

    public void resetFonts(){
        fontMenu.removeAll();
        for (int i = 0; i < fontStrings.length; i++) {
            fonts[i] = new Font(fontStrings[i], Font.PLAIN, 12);
            fontItems.add(new JMenuItem(fontStrings[i]));
            fontItems.get(i).setFont(fonts[i]);
            fontItems.get(i).addActionListener(this);
            fontMenu.add(fontItems.get(i));
        }
    }

    public void fillSizeMenu(){
        sizeItems = new ArrayList<JMenuItem>();
        int count = 0;
        for (int i = 12; i < 100; i+=8) {
            sizeItems.add(new JMenuItem(String.valueOf(i)));
            sizeItems.get(count).addActionListener(this);
            sizeItems.get(count).setFont(new Font(curFont.getFontName(), Font.BOLD, i));
            fontSizeMenu.add(sizeItems.get(count));
            count++;
        }
    }

    public void fillColorMenu(){
        colorStrings = new String[]{"Black","Dark Grey","Grey","Light Grey","White","Red","Orange","Yellow","Green","Cyan","Blue","Magenta","Pink", "Random Color"};
        colors = new Color[]{Color.BLACK,Color.DARK_GRAY,Color.GRAY,Color.LIGHT_GRAY,Color.WHITE,Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLUE,Color.MAGENTA,Color.PINK};

        textColorItems = new ArrayList<JMenuItem>();
        bgColorItems = new ArrayList<JMenuItem>();
        outlineItems = new ArrayList<JMenuItem>();

        outlineItems.add(new JMenuItem("No Color"));
        outlineItems.get(0).addActionListener(this);
        outlineColorMenu.add(outlineItems.get(0));

        for (int i = 0; i < colorStrings.length; i++) {
            textColorItems.add(new JMenuItem(colorStrings[i]));
            textColorItems.get(i).addActionListener(this);

            bgColorItems.add(new JMenuItem(colorStrings[i]));
            bgColorItems.get(i).addActionListener(this);

            outlineItems.add(new JMenuItem(colorStrings[i]));
            outlineItems.get(i+1).addActionListener(this);

            if(i < colors.length){
                textColorItems.get(i).setForeground(colors[i]);
                bgColorItems.get(i).setBackground(colors[i]);
                outlineItems.get(i+1).setBorder(new LineBorder(colors[i]));
            }

            textColorMenu.add(textColorItems.get(i));
            textBgColorMenu.add(bgColorItems.get(i));
            outlineColorMenu.add(outlineItems.get(i+1));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(reset == e.getSource()){
            textArea.setBackground(Color.WHITE);
            textArea.setText("");
            textArea.setForeground(Color.BLACK);
            curSize = 12;
            curFont = new Font("Dialog", Font.PLAIN, curSize); 
            setMenuFonts(new Font("Dialog", Font.BOLD, curSize));
            textArea.setFont(curFont);
            resetBorders();
            setMenuDimens(1,10);
            frame.add(menuBar, BorderLayout.NORTH);
        }   
   
        if(textColorItems.contains(e.getSource())){
            if(textColorItems.get(textColorItems.size()-1) == e.getSource())
                textArea.setForeground(new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
            else{
                int ind = 0;
                for (int i = 0; i < textColorItems.size()-1; i++) {
                    if(textColorItems.get(i) == e.getSource()){
                        ind = i;
                        break;
                    }
                }

                textArea.setForeground(colors[ind]);
            }
        }

        if(bgColorItems.contains(e.getSource())){
            if(bgColorItems.get(bgColorItems.size()-1) == e.getSource())
                textArea.setBackground(new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
            else{
                int ind = 0;
                for (int i = 0; i < bgColorItems.size()-1; i++) {
                    if(bgColorItems.get(i) == e.getSource()){
                        ind = i;
                        break;
                    }
                }

                textArea.setBackground(colors[ind]);
            }
        }

        if(fontItems.contains(e.getSource())){
            int ind = 0;
            for (int i = 0; i < fontItems.size(); i++) {
                if(fontItems.get(i) == e.getSource()){
                    ind = i;
                    break;
                }
            }

            curFont = new Font(fonts[ind].getFontName(), Font.PLAIN, curSize);
            setMenuFonts(new Font(fonts[ind].getFontName(), Font.BOLD, 12));
            textArea.setFont(curFont);
            
        }
        
        if(sizeItems.contains(e.getSource())){
            int ind = 0;
            for (int i = 0; i < sizeItems.size(); i++) {
                if(sizeItems.get(i) == e.getSource()){
                    ind = i;
                    break;
                }
            }
            curSize = Integer.parseInt(sizeItems.get(ind).getText());
            curFont = new Font(curFont.getFontName(), Font.PLAIN, curSize);
            resetFonts();
            textArea.setFont(curFont);
        }

        if(outlineItems.contains(e.getSource())){
            if(outlineItems.get(outlineItems.size()-1) == e.getSource())
                createBorder(new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
            else if(outlineItems.get(0) == e.getSource()){
                resetBorders();
            }
            else{
                int ind = 0;
                for (int i = 1; i < outlineItems.size()-1; i++) {
                    if(outlineItems.get(i) == e.getSource()){
                        ind = i-1;
                        break;
                    }
                }

                createBorder(colors[ind]);
            }
        }

        if(north == e.getSource()){
            setMenuDimens(1,10);
            frame.add(menuBar, BorderLayout.NORTH);
        }

        if(south == e.getSource()){
            setMenuDimens(1,10);
            frame.add(menuBar, BorderLayout.SOUTH);
        }

        if(east == e.getSource()){
            setMenuDimens(10,1);
            frame.add(menuBar, BorderLayout.EAST);
        }

        if(west == e.getSource()){
            setMenuDimens(10,1);
            frame.add(menuBar, BorderLayout.WEST);
        }

        revalidate();
    }

    public void createBorder(Color color){
        north.setBorder(new LineBorder(color));
        south.setBorder(new LineBorder(color));
        east.setBorder(new LineBorder(color));
        west.setBorder(new LineBorder(color));
        reset.setBorder(new LineBorder(color));
    }
    
    public void resetBorders(){
        north.setBorder(UIManager.getBorder("Button.border"));
        south.setBorder(UIManager.getBorder("Button.border"));
        east.setBorder(UIManager.getBorder("Button.border"));
        west.setBorder(UIManager.getBorder("Button.border"));
        reset.setBorder(UIManager.getBorder("Button.border"));
    }

    public void setMenuDimens(int r, int c){
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(r, c));

        menuBar.add(reset);
        menuBar.add(north);
        menuBar.add(south);
        menuBar.add(east);
        menuBar.add(west);

        menuBar.add(fontMenu);
        menuBar.add(fontSizeMenu);
        menuBar.add(textColorMenu);
        menuBar.add(textBgColorMenu);
        menuBar.add(outlineColorMenu);

     }

    public void setMenuFonts(Font f){
        north.setFont(f);
        south.setFont(f);
        east.setFont(f);
        west.setFont(f);
        reset.setFont(f);
        fontMenu.setFont(f);
        fontSizeMenu.setFont(f);
        textColorMenu.setFont(f);
        textBgColorMenu.setFont(f);
        outlineColorMenu.setFont(f);

        for (int i = 0; i < outlineItems.size(); i++) {
            if(i < sizeItems.size())
                sizeItems.get(i).setFont(new Font(f.getFontName(), Font.BOLD, Integer.parseInt(sizeItems.get(i).getText())));
            if(i < textColorItems.size()){
                textColorItems.get(i).setFont(f);
                bgColorItems.get(i).setFont(f);
            }
            outlineItems.get(i).setFont(f);
        }
    }

    public static void main(String[] args) {
        GUITask g = new GUITask();
    }
   
}