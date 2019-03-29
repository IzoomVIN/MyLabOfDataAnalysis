package ru.GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.SourceFiles.PlotDataRow;

import java.awt.*;
import java.util.List;

public class GraphicViewWindow extends ChartFrame{

    private JFreeChart createChart(DefaultCategoryDataset dataset){
        JFreeChart chart = ChartFactory.createBarChart("", "Year",
                "Count of suicides",  dataset,
                PlotOrientation.VERTICAL, false, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot p = chart.getCategoryPlot();
        p.setDomainGridlinePaint(Color.BLACK);
        return chart;
    }

    private DefaultCategoryDataset createDataSet(List<PlotDataRow> data){
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for(PlotDataRow row: data){
            dataSet.addValue(row.getCount(), "", row.getDate());
        }

        return dataSet;
    }

    private GraphicViewWindow(String title, JFreeChart chart) {
        super(title, chart);
    }

    public GraphicViewWindow GraphicViewWindowCreate(String title, List<PlotDataRow> data) {
        DefaultCategoryDataset dataSet = createDataSet(data);
        JFreeChart chart = createChart(dataSet);

        return new GraphicViewWindow(title, chart);

    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }

}
