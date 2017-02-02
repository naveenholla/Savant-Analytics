import yahoofinance.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Stock tesla = YahooFinance.get("TSLA", true);
        System.out.println(tesla.getHistory());

        Stock apple = YahooFinance.get("AAPL");
        BigDecimal price = apple.getQuote().getPrice();
        BigDecimal change = apple.getQuote().getChangeInPercent();
        BigDecimal peg = apple.getStats().getPeg();
        BigDecimal dividend = apple.getDividend().getAnnualYieldPercent();
        apple.print();
        System.out.println(price);
    }
}
