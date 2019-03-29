package ru.GUI;

import ru.SourceFiles.MyTableModel;
import ru.SourceFiles.SuicideStatisticsRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewTableWindow extends JFrame {
    private JPanel panel1;
    private JTable Table;
    private JButton CancelButton;
    private JButton VGButton;
    private JButton VCMButton;
    private String[] headers;
    private List<SuicideStatisticsRow> data;
    private String tableName;

    public  ViewTableWindow(List<SuicideStatisticsRow> data, String tableName){
        headers = new String[]{"Country", "Year", "Sex", "Age",
                "Suicide_count", "Population", "Suicide_count_to_100k_population"};
        this.data = data;
        this.tableName = tableName;

        CancelButton.setText("Cancel");
        VGButton.setText("Graphics");
        VCMButton.setText("Correlation matrix");

        // Realization of creating frame in center of Window;
        setMinimumSize(new Dimension(1000,600));
        setLocationRelativeTo(null);

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Table = new JTable(new MyTableModel(data, headers));
    }

    public String getTableName(){
        return this.tableName;
    }

    public void setActionListenerToVGButton(ActionListener al){
        this.VGButton.addActionListener(al);
    }

    public void setActionListenerToVCMButton(ActionListener al){
        this.VCMButton.addActionListener(al);
    }

    public void setActionListenerToCancelButton(ActionListener al){
        this.CancelButton.addActionListener(al);
    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }
}
