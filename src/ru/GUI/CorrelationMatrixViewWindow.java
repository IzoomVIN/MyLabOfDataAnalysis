package ru.GUI;

import ru.SourceFiles.MyCorrelationTableModel;
import ru.SourceFiles.MyGVWCallBack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class CorrelationMatrixViewWindow extends JFrame {
    private JTable CorrTable;
    private JPanel panel1;
    private double[][] data;
    private String[] columnsName;
    private MyGVWCallBack myGVWCallBack;

    public CorrelationMatrixViewWindow(double[][] data, MyGVWCallBack myGVWCallBack){
        columnsName = new String[]{"Year", "Suicide_count"};
        this.data = data;
        this.myGVWCallBack = myGVWCallBack;

        // Realization of creating frame in center of Window;
        setMinimumSize(new Dimension(200,90));
        setLocationRelativeTo(null);

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    @Override
    protected void processWindowEvent(final WindowEvent e) {
        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch (this.getDefaultCloseOperation()) {
                case JFrame.HIDE_ON_CLOSE:
                    setVisible(false);
                    break;
                case JFrame.DISPOSE_ON_CLOSE:
                    dispose();
                    break;
                case JFrame.EXIT_ON_CLOSE:
                    // This needs to match the checkExit call in
                    // setDefaultCloseOperation
                    System.exit(0);
                    break;
                case JFrame.DO_NOTHING_ON_CLOSE:
                    myGVWCallBack.clickOnCloseButton();
                default:
            }
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        CorrTable = new JTable(new MyCorrelationTableModel(
                this.data,
                this.columnsName
        ));
    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }
}
