package ru.SourceFiles;

public class PlotDataRow {
    private final String date;
    private final double count;

    PlotDataRow(String date, double count){
        this.date = date;
        this.count = count;
    }

    public double getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }
}
