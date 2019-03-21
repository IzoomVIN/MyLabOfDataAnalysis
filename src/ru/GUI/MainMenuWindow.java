package ru.GUI;

import ru.SourceFiles.LogicProgramm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuWindow extends JFrame{
    private JButton buttonVT;
    private JLabel MainMenuLabel;
    private JButton buttonID;
    private JPanel MMPanel;
    private JList ListOfTable;

    public MainMenuWindow() {
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

    public void setActionListenerID(ActionListener al){
        buttonID.addActionListener(al);
    }

    public void addTableInList(String table){
        ListOfTable.add(table, null);
    }

    public void setActionListenerVT(ActionListener al){
        buttonID.addActionListener(al);
    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }

}
