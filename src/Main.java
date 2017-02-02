import yahoofinance.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -5); // from 5 years ago

        //Stock google = YahooFinance.get("GOOG", from, to, Interval.WEEKLY);
        Stock stock = YahooFinance.get("AAPL");
        BigDecimal price = stock.getQuote().getPrice();
        BigDecimal change = stock.getQuote().getChangeInPercent();
        BigDecimal peg = stock.getStats().getPeg();
        BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        stock.print();
    }
}
