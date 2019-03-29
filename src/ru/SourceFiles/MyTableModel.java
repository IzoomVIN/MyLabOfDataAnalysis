package ru.SourceFiles;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MyTableModel extends AbstractTableModel {
    private List<SuicideStatisticsRow> data;
    private String[] columnsName;

    public MyTableModel(List<SuicideStatisticsRow> data, String[] columnsName){
        this.data = data;
        this.columnsName = columnsName;
    }

    @Override
    public String getColumnName(int column) {
        return columnsName[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnsName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SuicideStatisticsRow row = data.get(rowIndex);

        switch (columnIndex){
            case 0:
                return row.getCountry();
            case 1:
                return row.getYear();
            case 2:
                return row.getSex();
            case 3:
                return row.getAge();
            case 4:
                return row.getSuicidesCount();
            case 5:
                return row.getPopulation();
            case 6:
                return row.getSuicidesTo100KPopulation();
        }
        return null;
    }
}
