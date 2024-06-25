package Modelos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class PieChart extends JPanel {

    public PieChart() {
        // Set preferred size of the panel
        setPreferredSize(new Dimension(400, 300));

        // Create the pie chart and add it to the panel
        JFreeChart chart = createPieChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    private JFreeChart createPieChart() {
        // Create a dataset for the pie chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Exemplares Requisitados", 50);
        dataset.setValue("Exemplares Disponiveis", 30);


        // Create the pie chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart Exemplares",
                dataset,
                true, // Include legend
                true,
                false);

        // Customize the plot if needed
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Exemplares Requisitados", Color.RED);
        plot.setSectionPaint("Exemplares Disponiveis", Color.GREEN);


        return chart;
    }

    public void updateDataset() {
        // Update the pie chart dataset
        JFreeChart chart = createPieChart();
        PiePlot plot = (PiePlot) chart.getPlot();
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Exemplares Requisitados", 50);
        dataset.setValue("Exemplares Disponiveis", 30);

        plot.setDataset(dataset);

        // Refresh the chart panel
        removeAll();
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
        revalidate();
        repaint();
    }

}
