package savant;

/**
 * Created by arnavgudibande on 2/3/17.
 */

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.NumberAxis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Graph extends ApplicationFrame
{
    public static ChartPanel chartPanel;

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
        chartPanel.restoreAutoRangeBounds();

        chartPanel.setMinimumDrawHeight(799);
        chartPanel.setMaximumDrawHeight(804);
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 900 ) );
        setContentPane( chartPanel );
        Frame.basePanel.add(chartPanel);
        Frame.frame.setVisible(true);

    }

    private DefaultCategoryDataset createVWAPDataset( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int c = 0;
        for(Double i: Main.VWPAPs) {

            int hours = (Integer.parseInt(Main.time.get(c))) / 60 + 6;
            int minutes = (Integer.parseInt(Main.time.get(c)) % 60) + 30;

            if(minutes >= 60) {
                hours++;
                minutes-=60;
            }

            if(hours>12) {
                hours = 1;
            }

            String formattedDate = hours + ":" + minutes;
            if(minutes<9) formattedDate+="0";

            dataset.addValue(i, "VWAP", formattedDate);
            c++;
        }

        int d = 0;
        for(Double i: Main.prices) {

            int hours = (Integer.parseInt(Main.time.get(d))) / 60 + 6;
            int minutes = (Integer.parseInt(Main.time.get(d)) % 60) + 30;

            if(minutes >= 60) {
                hours++;
                minutes-=60;
            }

            if(hours>12) {
                hours = 1;
            }

            String formattedDate = hours + ":" + minutes;
            if(minutes<9) formattedDate+="0";


            dataset.addValue(i, "Price", formattedDate);
            d++;
        }

        return dataset;
    }

}
