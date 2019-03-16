package ru.GUI;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{
    private JButton buttonVT;
    private JButton buttonVGSY;
    private JButton buttonVGSYR;
    private JButton buttonVTC;
    private JLabel MainMenuLabel;
    private JButton buttonID;
    private JPanel MMPanel;

    MainMenu() {
        super("Suicide");
        Dimension size = new Dimension();

        buttonVT.setText("View table");
        buttonVGSY.setText("View graphic of Suicide/Years");
        buttonVGSYR.setText("View graphic of Suicide/Years in Russia");
        buttonVTC.setText("View table of correlation");
        buttonID.setText("Input data");
        MainMenuLabel.setText("Main Menu");

        size.height = 400;
        size.width = 400;
        setMaximumSize(size);
        setMinimumSize(size);
        setContentPane(MMPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]){
        ru.GUI.MainMenu menu = new MainMenu();
        menu.setVisible(true);

    }
}
