package ru.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GraphicParametrsWindow extends JFrame{
    private JButton buttAll;
    private JButton buttRus;
    private JButton butCancel;
    private JPanel panel;
    private String tableName;

    public  GraphicParametrsWindow(String tableName){
        buttAll.setText("Graphic of All country");
        buttRus.setText("Graphic of suicide in rus");
        butCancel.setText("cancel");
        this.tableName = tableName;

        // Realization of creating frame in center of Window;
        setSize(new Dimension(400,300));
        setLocationRelativeTo(null);
        setResizable(false);

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getTableName(){
        return this.tableName;
    }

    public void setActionListenerToButtAll(ActionListener al){
        this.buttAll.addActionListener(al);
    }

    public void setActionListenerToButtRus(ActionListener al){
        this.buttRus.addActionListener(al);
    }

    public void setActionListenerToButCancel(ActionListener al){
        this.butCancel.addActionListener(al);
    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }
}
