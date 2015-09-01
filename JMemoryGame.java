//
// Name: Succar, George
// Exra Credit 1
// Due: March 20, 2015   
// Course: cs-245-01-w15
//
// Description:
// Simple memory game.
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class JMemoryGame implements ActionListener {

    JFrame frame;
    int buttonsSelected = 0;
    ArrayList<JButton> buttonArray;
    JButton lastButtonSelected;
    javax.swing.Timer memoryTimer;
    ActionListener timerAL;
    JButton selectedButton;
    int matchesFound = 0;
    JPanel buttonPanel;

    public JMemoryGame() {
        frame = new JFrame("JMemoryGame");
        frame.setLayout(new FlowLayout());
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar menu = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");
        JMenuItem showAllOption = new JMenuItem("Show All Tiles");
        showAllOption.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                for(int i = 0; i < 16; i++){
                    buttonArray.get(i).setIcon(new ImageIcon
                            (buttonArray.get(i).getName() + ".png"));
                    buttonArray.get(i).setDisabledIcon(new ImageIcon
                            (buttonArray.get(i).getName() + ".png"));
                    buttonArray.get(i).setEnabled(false);
                }
            }
        });
        JMenuItem resetOption = new JMenuItem("Reset");
        resetOption.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                reset();
            }
        });
        JMenuItem exitOption = new JMenuItem("Exit");
        exitOption.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                frame.dispose();
            }
        });
        JMenuItem aboutOption = new JMenuItem("About");
        aboutOption.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                JOptionPane.showMessageDialog
                        (frame, "JMemoryGame\n(c) 2015 George Succar");
            }
        });
        gameMenu.add(showAllOption);
        gameMenu.add(resetOption);
        gameMenu.add(aboutOption);
        gameMenu.add(exitOption);
        menu.add(gameMenu);
        frame.setJMenuBar(menu);
        
        buttonArray = new ArrayList<JButton>();
        generateTiles();

        ActionListener timerAL = new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                selectedButton.setIcon(null);
                selectedButton.setDisabledIcon(null);
                lastButtonSelected.setIcon(null);
                lastButtonSelected.setDisabledIcon(null);
                buttonsSelected = 0;
                lastButtonSelected = null;
                for(int i = 0; i < 16; i++){
                    if(buttonArray.get(i).getIcon() == null){
                        buttonArray.get(i).setEnabled(true);
                    }
                    memoryTimer.stop();
                }
            }
        };

        memoryTimer = new javax.swing.Timer(2000, timerAL);

        frame.setContentPane(buttonPanel);
        frame.setVisible(true);
    }
    
    public void generateTiles(){
        buttonPanel = new JPanel(new GridLayout(4, 4));
        buttonPanel.setSize(frame.getWidth() - 35, frame.getHeight() - 35);
        if(!buttonArray.isEmpty()){
            buttonArray.clear();
        }
        for(Integer i = 0; i < 16; i++){
            buttonArray.add(new JButton("JMemoryGame"));
            buttonArray.get(i).setActionCommand(i.toString());
            buttonArray.get(i).addActionListener(this);
        }

        Collections.shuffle(buttonArray);

        for(int i = 0; i < 16; i++){
            JButton buttonToBeAdded = buttonArray.get(i);
            Integer actionCommand = Integer.parseInt
                    (buttonToBeAdded.getActionCommand());
            buttonPanel.add(buttonToBeAdded);
            if(actionCommand >= 8){
                actionCommand -= 8;
            }
            buttonToBeAdded.setName(actionCommand.toString());
        }
    }

    public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < 16; i++){
            if(buttonArray.get(i).getActionCommand().equals
                    (ae.getActionCommand())){
                selectedButton = buttonArray.get(i);
                selectedButton.setIcon(new ImageIcon
                        (selectedButton.getName() + ".png"));
                selectedButton.setDisabledIcon(new ImageIcon
                        (selectedButton.getName() + ".png"));
                break;
            }
        }

        if(selectedButton != lastButtonSelected){
            buttonsSelected++;
        }
        

        if(buttonsSelected == 2){
            if(selectedButton.getName().equals(lastButtonSelected.getName())){
                selectedButton.setEnabled(false);
                lastButtonSelected.setEnabled(false);
                buttonsSelected = 0;
                lastButtonSelected = null;
                matchesFound++;
            }
            else{
                for(int i = 0; i < 16; i++){
                    buttonArray.get(i).setEnabled(false);
                }
                memoryTimer.start();
            }
            if(matchesFound == 8){
                int optionChosen;
                optionChosen = JOptionPane.showConfirmDialog(frame, "Restart?",
                        "YOU WON!", JOptionPane.YES_NO_OPTION);
                matchesFound = 0;
                if(optionChosen == JOptionPane.YES_OPTION){
                    reset();
                }
            }
        }
        else{
            lastButtonSelected = selectedButton;
        }
    }
    //Couldn't find a good way to re-shuffle an already made game board
    //it just re-hides all tiles and clears progress
    private void reset() {
        if(memoryTimer.isRunning()){
            memoryTimer.restart();
            memoryTimer.stop();
        }
        buttonsSelected = 0;
        lastButtonSelected = null;
        matchesFound = 0;
        frame.setContentPane(new JPanel(new FlowLayout()));
        generateTiles();
        frame.setContentPane(buttonPanel);
        frame.repaint();
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new JMemoryGame();
            }
        }); 
    }
}