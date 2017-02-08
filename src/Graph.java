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

        JFreeChart VWAPlineChart = ChartFactory.createLineChart(
                chartTitle,
                "Time","VWAP",
                createVWAPDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( VWAPlineChart );

        chartPanel.setMinimumDrawHeight(799);
        chartPanel.setMaximumDrawHeight(804);
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 900 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createVWAPDataset( )
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int c = 0;
        for(Double i: Main.VWPAPs) {
            dataset.addValue(i, "VWAP", Main.time.get(c).toString());
            c++;
        }

        int d = 0;
        for(Double i: Main.prices) {
            dataset.addValue(i, "Price", Main.time.get(d).toString());
            d++;
        }

        return dataset;
    }
}
