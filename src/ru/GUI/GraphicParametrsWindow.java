package ru.GUI;

import javax.swing.*;
import java.awt.*;

public class GraphicParametrsWindow extends JFrame{
    private JButton buttAll;
    private JButton buttRus;
    private JButton butCancel;
    private JPanel panel;

    public  GraphicParametrsWindow(){
        buttAll.setText("Graphic of All country");
        buttRus.setText("Graphic of suicide in rus");
        butCancel.setText("cancel");

        // Realization of creating frame in center of Window;
        setSize(new Dimension(400,300));
        setLocationRelativeTo(null);
        setResizable(false);

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
