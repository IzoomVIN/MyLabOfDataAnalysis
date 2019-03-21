package ru.GUI;

import javax.swing.*;

public class ViewTableWindow extends JFrame {
    private JTable Table;
    private JPanel panel1;
    private JButton CancelButton;
    private JButton VGButton;
    private JButton VCMButton;

    public  ViewTableWindow(){

    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }
}
