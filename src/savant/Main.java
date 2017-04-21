package savant;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Double> prices = new ArrayList<>();
    public static ArrayList<Double> VWPAPs = new ArrayList<>();
    public static ArrayList<String> time = new ArrayList<>();

    private static String TICKER = "TSLA";

    public static void main(String[] args) throws IOException {
	// write your code here
        loadData();

        Graph chart = new Graph(
                "Savant Analytics" ,
                TICKER + " Price v. VWAP");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );

        NeuralNet n = new NeuralNet();
        n.runNetwork();
    }

    public static void loadData() throws IOException {
        URL currentStockURL = new URL("https://www.google.com/finance/getprices?i=30&p=1d&f=d,o,h,l,c,v&df=cpct&q="+ TICKER);
        BufferedReader in = new BufferedReader(new InputStreamReader(currentStockURL.openStream()));

        CSVReader reader = new CSVReader(in);
        String[] nextLine;

        int count=0;
        ArrayList<Stock> stocks = new ArrayList<>();
        while ((nextLine = reader.readNext()) != null) {
            count++;

            if(count>=9) {stocks.add(new Stock(nextLine[0], Double.parseDouble(nextLine[1]), Double.parseDouble(nextLine[2]), Double.parseDouble(nextLine[3]), Double.parseDouble(nextLine[4]), Double.parseDouble(nextLine[5])));
            }
        }

        URL trainingStockURL = new URL("https://www.google.com/finance/getprices?i=30&p=15d&f=d,o,h,l,c,v&df=cpct&q="+ TICKER);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(trainingStockURL.openStream()));

        CSVReader reader2 = new CSVReader(in2);
        String[] nextLine2;

        int count2=0;
        ArrayList<Stock> stocks2 = new ArrayList<>();
        while ((nextLine2 = reader2.readNext()) != null) {
            count2++;

            if(count2>=9) {
                stocks2.add(new Stock(nextLine2[0], Double.parseDouble(nextLine2[1]), Double.parseDouble(nextLine2[2]), Double.parseDouble(nextLine2[3]), Double.parseDouble(nextLine2[4]), Double.parseDouble(nextLine2[5])));
            }
        }

        double pv = 0;
        double v = 0;
        double cp=0;
        double pp = 0;
        boolean VWAPup=false;

        for(int i=1; i<stocks.size(); i++) {
            Stock d = stocks.get(i);
            prices.add(d.getPrice());
            VWPAPs.add(d.getVWAP(pv,v));
            time.add(d.getTimestamp());

            pv+=d.getPrice()*d.getVolume();
            v+=d.getVolume();
            pp=d.getPrice();

        }

        CSVWriter cw = new CSVWriter(new FileWriter("src/in/stock_test.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);

        String[] temp = new String[4];
        Stock b = stocks.get(stocks.size()-1);
        temp[0] = b.getTimestamp();
        temp[1] = Double.toString(b.getPrice());
        temp[2] = Double.toString(b.getVWAP(pv,v));
        temp[3] = Integer.toString(0);
        cw.writeNext(temp);


        cw.close();

        pv=v=cp=pp=0;

        for(int i=1; i<stocks2.size(); i++) {
            Stock s = stocks2.get(i);
            if(s.getPrice()>cp && VWAPup) {
                System.out.println("Actual: Trend up\n");
                stocks2.get(i-1).setStatus(true); // indicators worked!
            }
            else if(s.getPrice()<cp && !VWAPup) {
                System.out.println("Actual: Trend down\n");
                stocks2.get(i-1).setStatus(true); // indicator worked!!!
            }

            System.out.println(s.getPrice() + "     " + s.getVWAP(pv, v));

            if(s.getVWAP(pv, v)<s.getPrice()) {
                System.out.println("Prediction: Trend Up\n");
                cp = s.getPrice();
                VWAPup = true;
            }
            else {
                System.out.println("Prediction: Trend down\n");
                cp = s.getPrice();
                VWAPup = false;
            }

            pv+=s.getPrice()*s.getVolume();
            v+=s.getVolume();
            pp=s.getPrice();
        }


        CSVWriter cd = new CSVWriter(new FileWriter("src/in/stock_train.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);

        for(int g=0; g<stocks2.size()-1; g++) {
            Stock q = stocks2.get(g);
            String[] temp2 = new String[4];
            if(q.getTimestamp().startsWith("a")) continue;

            temp2[0] = q.getTimestamp();
            temp2[1] = Double.toString(q.getPrice());
            temp2[2] = Double.toString(q.getVWAP(pv,v));
            temp2[3] = Integer.toString(q.getStatus());

            cd.writeNext(temp2);
        }

        cd.close();
    }
}
