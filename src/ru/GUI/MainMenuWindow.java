package ru.GUI;

import javax.swing.*;
import java.awt.*;

public class MainMenuWindow extends JFrame{
    private JButton buttonVT;
    private JLabel MainMenuLabel;
    private JButton buttonID;
    private JPanel MMPanel;

    MainMenuWindow() {
        super("Suicide");
        Dimension size = new Dimension();

        buttonVT.setText("View table");
        buttonID.setText("Input data");
        MainMenuLabel.setText("Main Menu");

        size.height = 400;
        size.width = 400;

        //Set the size of window and close resized;
        setResizable(false);
        setSize(size);

        // Realization of creating frame in center of Window;
        setLocationRelativeTo(null);

        setContentPane(MMPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]){
        MainMenuWindow menu = new MainMenuWindow();
        menu.setVisible(true);

    }
}
