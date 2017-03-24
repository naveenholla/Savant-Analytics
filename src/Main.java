import au.com.bytecode.opencsv.CSVReader;
import org.jfree.ui.RefineryUtilities;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Double> prices = new ArrayList<>();
    public static ArrayList<Double> VWPAPs = new ArrayList<>();
    public static ArrayList<Integer> time = new ArrayList<>();
    private static String TICKER = "TSLA";

    public static void main(String[] args) throws IOException {
	// write your code here
        loadData();

        Graph chart = new Graph (
                "Savant Analytics" ,
                TICKER + " Price v. VWAP");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }

    public static void loadData() throws IOException {
        URL stockURL = new URL("http://chartapi.finance.yahoo.com/instrument/1.0/" + TICKER +"/chartdata;type=quote;range=1d/csv");
        BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));

        CSVReader reader = new CSVReader(in);
        String [] nextLine;
        int count=0;
        ArrayList<Stock> stocks = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            count++;
            if(count>=18) stocks.add(new Stock(Integer.parseInt(nextLine[0]), Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[2]), Double.parseDouble(nextLine[3]), Double.parseDouble(nextLine[4]), Double.parseDouble(nextLine[4])));
        }

        double pv = 0;
        double v = 0;

        double cp=0;

        for(Stock s : stocks) {
            if(s.getPrice()>cp) System.out.println("Actual: Trend up\n");
            else if(s.getPrice()<cp) System.out.println("Actual: Trend down\n");

            System.out.println(s.getPrice() + "     " + s.getVWAP(pv, v));
            prices.add(s.getPrice());
            VWPAPs.add(s.getVWAP(pv,v));
            time.add(s.getTimestamp());



            if(s.getVWAP(pv, v)<s.getPrice()) {
                System.out.println("Prediction: Trend Up\n");
                cp = s.getPrice();
            }
            else {
                System.out.println("Prediction: Trend down\n");
                cp = s.getPrice();
            }

            pv+=s.getPrice()*s.getVolume();
            v+=s.getVolume();
        }
    }
}
