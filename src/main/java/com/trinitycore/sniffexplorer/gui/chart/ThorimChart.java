package com.trinitycore.sniffexplorer.gui.chart;

import com.trinitycore.sniffexplorer.game.data.Position;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Created by chaouki on 26-03-17.
 */
public class ThorimChart extends JFrame {

    private static final long serialVersionUID = 1L;

    public ThorimChart(String applicationTitle, String chartTitle, Collection<Position> positions, List<List<Position>> paths) {
        super(applicationTitle);
        // This will create the dataset
        DefaultXYDataset dataset = new DefaultXYDataset();
        createDataset(dataset, 1, positions, true);

//        for (int i = 0; i < 3 ; i++) {
//        for (int i = 0; i < 32 ; i++) {
//        for (int i = 0; i < paths.size() ; i++) {
//            List<Position> path = paths.get(i);
//            createDataset(dataset, i +2, path, false);
//        }

//        List<Position> path;
//        path = paths.get(0);
//        createDataset(dataset, 2, path, false);
//
//        path = paths.get(1);
//        createDataset(dataset, 3, path, false);
//
//        path = paths.get(2);
//        createDataset(dataset, 4, path, false);


        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle, positions);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createDataset(DefaultXYDataset dataset, int seriesKey, Collection<Position> positions, boolean isCycle) {
        int size = positions.size();
        double data[][] = isCycle ? new double[2][size+1] : new double[2][size];

        int i=0;
        for (Position position : positions) {
            data[0][i] = position.getX();
            data[1][i] = position.getY();

            i++;
        }

        if(isCycle){
            // close the loop: last point -> first point
            data[0][i] = positions.iterator().next().getX();
            data[1][i] = positions.iterator().next().getY();
        }

        dataset.addSeries(seriesKey, data);
    }

    private JFreeChart createChart(DefaultXYDataset dataset, String title, Collection<Position> positions) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,                                  // chart title
                "x",
                "y",
                dataset
        );

        XYPlot plot = chart.getXYPlot();

//        ValueAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setRange(getBottom(positions) - 100.0, getTop(positions) + 100.0);

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setRange(-290, -240);
//        rangeAxis.setRange(getLeft(positions)- 100.0, getRight(positions) + 100.0);


        /* Turn on scatter plot with our custom points */
        XYItemRenderer renderer = plot.getRenderer();
        ((XYLineAndShapeRenderer) renderer).setBaseShapesVisible(true);

        return chart;

    }

    private double getTop(Collection<Position> positions) {
        return positions.stream()
                .map(Position::getY)
                .max(Comparator.<Float>naturalOrder())
                .get();
    }

    private double getRight(Collection<Position> positions) {
        return positions.stream()
                .map(Position::getX)
                .max(Comparator.<Float>naturalOrder())
                .get();
    }

    private double getBottom(Collection<Position> positions) {
        return positions.stream()
                .map(Position::getY)
                .min(Comparator.<Float>naturalOrder())
                .get();
    }

    private double getLeft(Collection<Position> positions) {
        return positions.stream()
                .map(Position::getX)
                .min(Comparator.<Float>naturalOrder())
                .get();
    }
}