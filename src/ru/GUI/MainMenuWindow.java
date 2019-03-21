package ru.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainMenuWindow extends JFrame{
    private JButton buttonVT;
    private JLabel MainMenuLabel;
    private JButton buttonID;
    private JPanel MMPanel;
    private JList ListOfTable;
    private DefaultListModel<String> listOfTable;

    public MainMenuWindow() {
        super("Suicide");
        Dimension size = new Dimension();

        buttonVT.setText("View table");
        buttonID.setText("Input data");
        MainMenuLabel.setText("Main Menu");
        listOfTable = new DefaultListModel<>();

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

    public void setActionListenerVT(ActionListener al){
        buttonVT.addActionListener(al);
    }

    public String getSelectedValue(){
        return ListOfTable.getSelectedValue().toString();
    }

    public ArrayList<String> getAllTableName(){
        ArrayList<String> outputArray = new ArrayList<>();

        for(int i = 0; i < listOfTable.getSize(); i++){
            outputArray.add(listOfTable.get(i));
        }

        return outputArray;
    }

    public void addTableInList(String table){
        listOfTable.add(0,table);
        ListOfTable.setModel(listOfTable);
    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }

}
