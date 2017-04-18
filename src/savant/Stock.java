package savant;

/**
 * Created by arnavgudibande on 2/3/17.
 */

public class Stock {
    private int timestamp;
    private double close, high, low ,open, volume;
    private double VWAP, TWAP;
    private boolean wentUp;

    public Stock(int t, double c, double h, double l ,double o, double v) {
        this.timestamp = t;
        this.close = c;
        this.high = h;
        this.open = o;
        this.low = l;
        this.volume = v;
        //this.TWAP = getPrice();
    }

    public int getTimestamp() { return this.timestamp; }
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

    public double getTWAP(double prev, int num) {
        double sum = prev + getPrice();
        if(num<1) {
            this.TWAP = sum;
            return this.TWAP;
        } else {
            this.TWAP = sum / 2;
            return this.TWAP;
        }
    }

    public void setStatus(boolean wentUp) {
        this.wentUp = wentUp;
    }

    public boolean getStatus() {
        return wentUp;
    }
}
