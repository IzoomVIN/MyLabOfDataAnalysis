package ru.SourceFiles;

import javax.swing.table.AbstractTableModel;

public class MyCorrelationTableModel extends AbstractTableModel{
    private double[][] data;
    private String[] columnsName;

    public MyCorrelationTableModel(double[][] data, String[] columnsName){
        this.data = data;
        this.columnsName = columnsName;
    }

    @Override
    public String getColumnName(int column) {
        return columnsName[column];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnsName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
