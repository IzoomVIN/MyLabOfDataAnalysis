package ru;

import javax.swing.*;

public class MainMenu {
    private JButton buttonVT;
    private JButton buttonVGSY;
    private JButton buttonVGSYR;
    private JButton buttonVTC;
    private JLabel MainMenu;
    private JButton buttonID;

    MainMenu(){
        buttonVT.setText("View table");
        buttonVGSY.setText("View graphic of Suicide/Years");
        buttonVGSYR.setText("View graphic of Suicide?Years in Russia");
        buttonVTC.setText("View table of correlation");
        buttonID.setText("Input data");
        MainMenu.setText("Main Menu");
    }
}
