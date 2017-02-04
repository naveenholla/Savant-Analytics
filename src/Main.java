import au.com.bytecode.opencsv.CSVReader;
import org.jfree.ui.RefineryUtilities;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Double> prices = new ArrayList<>();
    public static ArrayList<Double> VWPAPs = new ArrayList<>();
    public static ArrayList<Integer> time = new ArrayList<>();

    public static void main(String[] args) throws IOException {
	// write your code here
        CSVReader reader = new CSVReader(new FileReader("in/data.csv"));
        String [] nextLine;
        int count=0;
        ArrayList<Stock> stocks = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            count++;
            if(count>=18) stocks.add(new Stock(Integer.parseInt(nextLine[0]), Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[2]), Double.parseDouble(nextLine[3]), Double.parseDouble(nextLine[4]), Double.parseDouble(nextLine[4])));
        }

        double pv = 0;
        double v = 0;



        for(Stock s : stocks) {
            System.out.println(s.getPrice() + "     " + s.getVWAP(pv, v));
            prices.add(s.getPrice());
            VWPAPs.add(s.getVWAP(pv,v));
            time.add(s.getTimestamp());

            if(s.getVWAP(pv, v)<s.getPrice()) System.out.println("Trend Up\n");
            else System.out.println("Trend down\n");
            pv+=s.getPrice()*s.getVolume();
            v+=s.getVolume();
        }

        Graph chart = new Graph (
                "VWAP" ,
                "VWAP over time");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );


    }
}
