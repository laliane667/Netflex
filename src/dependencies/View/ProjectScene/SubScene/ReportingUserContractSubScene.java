package dependencies.View.ProjectScene.SubScene;

import dependencies.Controller.SceneController.ControlCenterController;
import dependencies.Model.UserContract;
import dependencies.View.ProjectWindow;
import org.jfree.chart.*;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.geom.Point2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingUserContractSubScene extends JPanel {
    public ReportingUserContractSubScene(ProjectWindow projectWindow, ControlCenterController controlCenterController, String error) {
        setPreferredSize(new Dimension(400, 220));
        setMaximumSize(new Dimension(400, 220));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(projectWindow.getBackgroundColor());

        List<UserContract> userContracts = controlCenterController.getApplicationController().getApplication().getReporting().getUserContracts();

        JLabel titleLabel = new JLabel();
        titleLabel.setText("New users over time");
        titleLabel.setBackground(projectWindow.getBackgroundColor());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.setForeground(Color.WHITE);
        titlePanel.setBackground(projectWindow.getBackgroundColor());
        add(titlePanel);

        // Create dataset for the bar diagram
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> dateCounts = new HashMap<>();

        LocalDate minDate = LocalDate.MAX;
        LocalDate maxDate = LocalDate.MIN;

        for (UserContract userContract : userContracts) {
            LocalDate date = userContract.getContractStart().toLocalDate();
            String dateString = date.toString();

            if (dateCounts.containsKey(dateString)) {
                dateCounts.put(dateString, dateCounts.get(dateString) + 1);
            } else {
                dateCounts.put(dateString, 1);
            }

            if (date.isBefore(minDate)) {
                minDate = date;
            }

            if (date.isAfter(maxDate)) {
                maxDate = date;
            }
        }

        LocalDate currentDate = minDate;
        while (!currentDate.isAfter(maxDate)) {
            String dateString = currentDate.toString();
            Integer count = dateCounts.getOrDefault(dateString, 0);
            dataset.addValue(count, "Users", dateString);
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        // Create the JFreeChart object
        JFreeChart chart = ChartFactory.createBarChart(
                "",
                "Date",
                "Number of Users",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Customize chart appearance
        chart.setBackgroundPaint(projectWindow.getBackgroundColor());
        chart.getTitle().setPaint(Color.WHITE);
        chart.getCategoryPlot().getDomainAxis().setLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getRangeAxis().setLabelPaint(Color.WHITE);
        chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.WHITE);
        chart.getCategoryPlot().setBackgroundPaint(projectWindow.getBackgroundColor());
        chart.getCategoryPlot().setOutlinePaint(Color.WHITE);
        // Add the chart to a ChartPanel and add it to the JPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 160));
        add(chartPanel);

        // Create a label to display the date when hovering over a stick
        JLabel dateLabel = new JLabel();
        dateLabel.setText("Date:");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        add(dateLabel);

// Add a ChartMouseListener to display the date when hovering over a stick
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                // Do nothing when the chart is clicked
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                int x = event.getTrigger().getX();
                int y = event.getTrigger().getY();
                Point2D p = chartPanel.translateScreenToJava2D(new Point(x, y));
                CategoryPlot plot = (CategoryPlot) event.getChart().getPlot();
                CategoryAxis domainAxis = plot.getDomainAxis();
                Rectangle2D dataArea = chartPanel.getScreenDataArea();
                int categoryCount = dataset.getColumnCount();
                double lowerBound = domainAxis.getCategoryStart(0, categoryCount, dataArea, plot.getDomainAxisEdge());
                double upperBound = domainAxis.getCategoryEnd(categoryCount - 1, categoryCount - 1, dataArea, plot.getDomainAxisEdge());
                double width = upperBound - lowerBound;

                int index = (int) Math.round((p.getX() - lowerBound) / width * (categoryCount - 1));
                if (index >= 0 && index < categoryCount) {
                    Comparable<?> category = dataset.getColumnKey(index);
                    // Display the category (date) in the label
                } else {
                    // The index is out of bounds; you can either ignore or handle this case differently
                }

                if (index >= 0 && index < dataset.getColumnCount()) {
                    Comparable columnKey = dataset.getColumnKey(index);
                    dateLabel.setText("Date: " + columnKey.toString());
                } else {
                    dateLabel.setText("Date:");
                }
            }

        });

    }
}
