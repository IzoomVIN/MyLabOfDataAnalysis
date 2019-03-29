package ru.GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import ru.SourceFiles.MyGVWCallBack;
import ru.SourceFiles.PlotDataRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class GraphicViewWindow extends ChartFrame{
    private MyGVWCallBack myGVWCallBack;

    private static JFreeChart createChart(DefaultCategoryDataset dataset){
        JFreeChart chart = ChartFactory.createBarChart("", "Year",
                "Count of suicides",  dataset,
                PlotOrientation.VERTICAL, false, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot p = chart.getCategoryPlot();
        p.setDomainGridlinePaint(Color.BLACK);
        return chart;
    }

    private static DefaultCategoryDataset createDataSet(List<PlotDataRow> data){
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for(PlotDataRow row: data){
            dataSet.addValue(row.getCount(), "", row.getDate());
        }

        return dataSet;
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

    private void setMyGVWCallBack(MyGVWCallBack myGVWCallBack){
        this.myGVWCallBack = myGVWCallBack;
    }

    private GraphicViewWindow(String title, JFreeChart chart) {
        super(title, chart);
    }

    public static GraphicViewWindow GraphicViewWindowCreate(String title, List<PlotDataRow> data,
                                                     MyGVWCallBack myGVWCallBack) {
        DefaultCategoryDataset dataSet = createDataSet(data);
        JFreeChart chart = createChart(dataSet);

        GraphicViewWindow grWin = new GraphicViewWindow(title, chart);

        // Realization of creating frame in center of Window;
        grWin.setMinimumSize(new Dimension(1000,600));
        grWin.setLocationRelativeTo(null);

        grWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        grWin.setMyGVWCallBack(myGVWCallBack);

        return grWin;
    }

    public void start(){
        this.setVisible(true);
    }

    public void stop(){
        this.setVisible(false);
    }

}
