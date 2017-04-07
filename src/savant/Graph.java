package savant;

/**
 * Created by arnavgudibande on 2/3/17.
 */

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Graph extends ApplicationFrame implements ActionListener
{
    private ChartPanel chartPanel;

    public Graph( String applicationTitle , String chartTitle )
    {
        super(applicationTitle);

        JFreeChart VWAPlineChart = ChartFactory.createLineChart(
                chartTitle,
                "Time","VWAP",
                createVWAPDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        chartPanel = new ChartPanel( VWAPlineChart );

        chartPanel.setMinimumDrawHeight(799);
        chartPanel.setMaximumDrawHeight(804);
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 900 ) );
        setContentPane( chartPanel );

        //chartPanel.repaint();
    }

    private DefaultCategoryDataset createVWAPDataset( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int c = 0;
        for(Double i: Main.VWPAPs) {

            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(Long.parseLong(Main.time.get(c).toString()), 0, ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm", Locale.ENGLISH);
            String formattedDate = dateTime.format(formatter);

            dataset.addValue(i, "VWAP", formattedDate);
            c++;
        }

        int d = 0;
        for(Double i: Main.prices) {

            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(Long.parseLong(Main.time.get(d).toString()), 0, ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm", Locale.ENGLISH);
            String formattedDate = dateTime.format(formatter);

            dataset.addValue(i, "Price", formattedDate);
            d++;
        }

        return dataset;
    }

    public void actionPerformed(ActionEvent e) {
        try { Main.loadData(); }
        catch (IOException d) {}
        chartPanel.repaint();
    }
}
