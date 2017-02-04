/**
 * Created by arnavgudibande on 2/3/17.
 */

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph extends ApplicationFrame
{
    public Graph( String applicationTitle , String chartTitle )
    {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Time","VWAP",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        JFreeChart lineChart2 = ChartFactory.createLineChart(chartTitle, "Time", "Price", createDataset2(), PlotOrientation.VERTICAL, true, true, false);


        ChartPanel chartPanel = new ChartPanel( lineChart );
        ChartPanel chartPanel1 = new ChartPanel( lineChart2 );

        chartPanel.setMinimumDrawHeight(799);
        chartPanel.setMaximumDrawHeight(804);
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 900 ) );
        setContentPane( chartPanel );

        chartPanel1.setMinimumDrawHeight(799);
        chartPanel1.setMaximumDrawHeight(804);
        chartPanel1.setPreferredSize( new java.awt.Dimension( 1000 , 900 ) );
        setContentPane( chartPanel1 );

    }

    private DefaultCategoryDataset createDataset( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int c = 0;
        for(Double i: Main.VWPAPs) {
            dataset.addValue(i, "VWAP", Main.time.get(c).toString());
            c++;
        }
        //dataset.addValue( 300 , "schools" , "2014" );
        return dataset;
    }

    private DefaultCategoryDataset createDataset2( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int c = 0;
        for(Double i: Main.prices) {
            dataset.addValue(i, "Price", Main.time.get(c).toString());
            c++;
        }
        //dataset.addValue( 300 , "schools" , "2014" );
        return dataset;
    }
}
