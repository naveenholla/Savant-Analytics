package savant;

/**
 * Created by arnavgudibande on 2/3/17.
 */

public class Stock {
    private String timestamp;
    private double close, high, low ,open, volume;
    private double VWAP;
    private boolean wentUp;

    public Stock(String t, double c, double h, double l ,double o, double v) {
        this.timestamp = t;
        this.close = c;
        this.high = h;
        this.open = o;
        this.low = l;
        this.volume = v;
    }

    public String getTimestamp() { return this.timestamp; }
    public double getHigh() { return this.high; }
    public double getClose() { return this.close; }
    public double getLow() { return this.low; }
    public double getOpen() { return this.open; }
    public double getVolume() { return this.volume; }
    public double getPrice() { return (this.getHigh() + this.getClose() + this.getLow() + this.getOpen())/4; }

    public double getVWAP(double prevPV, double prevV) {
        double pv = getPrice()*getVolume();
        double x = (pv+prevPV)/(prevV+getVolume());
        this.VWAP = x;
        return x;
    }

    public void setStatus(boolean wentUp) {
        this.wentUp = wentUp;
    }

    public int getStatus() {
        if(this.wentUp) return 1;
        else return 0;
    }
}
