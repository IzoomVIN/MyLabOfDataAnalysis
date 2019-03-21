package ru.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddTableViewWindow extends JFrame{
    private JPanel ITPanel;
    private JLabel FileNameLabel;
    private JLabel TableNameLabel;
    private JTextField FileNameText;
    private JTextField TableNameText;
    private JButton OkButton;
    private JButton CancelButton;

    public AddTableViewWindow(){
        super("Add new table");
        Dimension size = new Dimension();

        FileNameLabel.setText("File name");
        TableNameLabel.setText("Table name");
        OkButton.setText("OK");
        CancelButton.setText("Cancel");

        size.height = 400;
        size.width = 400;

        //Set the size of window and close resized;
        setResizable(false);
        setSize(size);

        // Realization of creating frame in center of Window;
        setLocationRelativeTo(null);

        setContentPane(ITPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String getTableName(){
        return TableNameText.getText();
    }

    public String getDBName(){
        return FileNameText.getText();
    }

    public void setActionListenerOK(ActionListener al){
        OkButton.addActionListener(al);
    }

    public void setActionListenerCancel(ActionListener al){
        CancelButton.addActionListener(al);
    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }
}
