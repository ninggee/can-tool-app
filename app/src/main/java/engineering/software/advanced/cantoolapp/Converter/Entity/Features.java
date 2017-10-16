package engineering.software.advanced.cantoolapp.Converter.Entity;

/**
 * Created by Zhang Dongdi on 2017/10/16.
 */

public class Features {
    private int firstLow;
    private int firstHigh;
    private int midStartByte;
    private int midEndByte;
    private int lastLow;
    private int lastHigh;

    private boolean hasFirst;
    private boolean hasMid;
    private boolean hasLast;

    public Features(int firstLow, int firstHigh, int midStartByte, int midEndByte, int lastLow,
                    int lastHigh, boolean hasFirst, boolean hasMid, boolean hasLast) {
        this.firstLow = firstLow;
        this.firstHigh = firstHigh;
        this.midStartByte = midStartByte;
        this.midEndByte = midEndByte;
        this.lastLow = lastLow;
        this.lastHigh = lastHigh;
        this.hasFirst = hasFirst;
        this.hasMid = hasMid;
        this.hasLast = hasLast;
    }

    public int getFirstLow() {
        return firstLow;
    }

    public int getFirstHigh() {
        return firstHigh;
    }

    public int getMidStartByte() {
        return midStartByte;
    }

    public int getMidEndByte() {
        return midEndByte;
    }

    public int getLastLow() {
        return lastLow;
    }

    public int getLastHigh() {
        return lastHigh;
    }

    public boolean isHasFirst() {
        return hasFirst;
    }

    public boolean isHasMid() {
        return hasMid;
    }

    public boolean isHasLast() {
        return hasLast;
    }
}
