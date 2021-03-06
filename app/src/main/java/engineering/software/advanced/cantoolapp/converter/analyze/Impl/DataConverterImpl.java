package engineering.software.advanced.cantoolapp.converter.analyze.Impl;

import engineering.software.advanced.cantoolapp.converter.analyze.DataConverter;
import engineering.software.advanced.cantoolapp.converter.entity.Data;
import engineering.software.advanced.cantoolapp.converter.entity.Features;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public class DataConverterImpl implements DataConverter {

    private static DataConverter converter = new DataConverterImpl();

    private DataConverterImpl() {}

    public static DataConverter getInstance() {
        return converter;
    }

    @Override
    public int bigEndianDecodeSignal(Data data, int start, int length) {
//        System.out.println(data);
//        System.out.println("start: " + start + ", length: " + length);
//
//        if (start / 8 == (start - length  + 1) / 8) {
//            //数据同一个字节中，那么可以简单求出signal的值
//
//            int signal = 0;
//            int by = data.getByte(start / 8);
//            for (int i = start; i >= start - length + 1; i--) {
//                System.out.println("statements: 1");
//                System.out.println("next bit: " + data.getBit(i));
//                signal = (signal << 1) + data.getBit(i);
//            }
//            return signal;
//        }
//        else {
//            //数据不在同一个字节中，此时signal的值需要分3部求出
//
//            int firstRowCount = start - start / 8 * 8 + 1;
//            int middleRow = (length - firstRowCount - 1) / 8;
//            int last = (start / 8/*起始行*/ + middleRow/*中间行数*/ + 2) * 8 - (length - firstRowCount) % 8;
//            if ((length - firstRowCount) % 8 == 0) {
//                last -= 8;
//            }
//            System.out.println("last:" + last);
//
//            System.out.println("next bit: " + data.getBit(start));
//            int signal = data.getBit(start);
//
//            //第一步是完成第一行未占满的byte的计算
//            for (int i = start - 1; i % 8 != 7 && i >= 0; i--) {
//                System.out.println("statements: 2");
//                System.out.println("next bit: " + data.getBit(i));
//                signal = (signal << 1) + data.getBit(i);
//            }
//
//            //第二部是完成中间占满的所有行的计算
//            for (int i = start / 8 + 1; i <= last / 8 - 1; i++) {
//                //i表示行，i遍历所有满行
//                for (int j = (i + 1) * 8 - 1; j >= i * 8; j--) {
//                    System.out.println("statements: 3");
//                    System.out.println("next bit: " + data.getBit(j));
//                    signal = (signal << 1) + data.getBit(j);
//                }
//            }
//
//            //第三步 完成最后一行的计算
//            for (int i = (last / 8 + 1) * 8 - 1; i >= last; i--) {
//                System.out.println("statements: 4");
//                System.out.println("next bit: " + data.getBit(i));
//                signal = (signal << 1) + data.getBit(i);
//            }
//
//            return signal;
//        }

        //LOGIC REWRITE
        Features features = getBigEndianFeatures(start, length);

        int signal = 0;
        if (features.isHasFirst()) {
            for (int i = features.getFirstHigh(); i >= features.getFirstLow(); i--) {
                signal = (signal << 1) + data.getBit(i);
//                System.out.println("first row, next bit: " + data.getBit(i));
            }
        }

        if (features.isHasMid()) {
            for (int i = features.getMidStartByte(); i <= features.getMidEndByte(); i++) {
                int by = data.getByte(i);
                signal = (signal << 8) + by;
//                System.out.println(String.format("mid rows, next row: %x", by));
            }
        }

        if (features.isHasLast()) {
            for (int i = features.getLastHigh(); i >= features.getLastLow(); i--) {
                signal = (signal << 1) + data.getBit(i);
//                System.out.println("last row, next bit: " + data.getBit(i));
            }
        }

        return signal;
    }

    @Override
    public boolean bigEndianEncodeSignal(Data data, int start, int length, int value) {
        Features features = getBigEndianFeatures(start, length);

        if (features.isHasFirst()) {
//            System.out.println("has first. high - low: " + features.getFirstHigh() + " - " + features.getFirstLow());
            int firstValue = value >> (length - (features.getFirstHigh() - features.getFirstLow() + 1));
//            System.out.println("first value: " + firstValue);

            for (int i = features.getFirstLow(); i <= features.getFirstHigh(); i++) {
                if (!data.setBit(i, firstValue & 1)) {
                    return false;
                }
                firstValue = firstValue >> 1;
            }
        }
        if (features.isHasMid()) {
            for (int i = features.getMidStartByte(); i <= features.getMidEndByte(); i++) {
                int rMove = length - (features.getFirstHigh() - features.getFirstLow() + 1)
                        - (i - features.getMidStartByte() + 1) * 8;
                int by = (value >> rMove) & 255;
//                System.out.println("rMove: " + rMove + " byte: " + by);
                if (!data.setByte(i, by)) {
                    return false;
                }
            }
        }
        if (features.isHasLast()) {
            int lastValue = value & getAllOneMask(features.getLastHigh() - features.getLastLow() + 1);
//            System.out.println("last value:" + lastValue);

            for (int i = features.getLastLow(); i <= features.getLastHigh(); i++) {
                if (!data.setBit(i, lastValue & 1)) {
                    return false;
                }
                lastValue = lastValue >> 1;
            }
        }

        return true;
    }

    @Override
    public int littleEndianDecodeSignal(Data data, int start, int length) {
//        int signal = 0;
//        for (int i = start + length - 1; i >= start; i--) {
//            int by = data.getByte(i / 8);
//            int index = i % 8;
//            int bit = (by & (1 << index)) >> index;
//            signal = (signal << 1) + bit;
//        }
//        return signal;

        int signal = 0;
        for (int i = start + length - 1; i >= start; i--) {
            signal = (signal << 1) + data.getBit(i);
        }
        return signal;
    }

    @Override
    public boolean littleEndianEncodeSignal(Data data, int start, int length, int value) {
        for (int i = start; i <= start + length - 1; i++) {
            if (!data.setBit(i, (value >> (i - start)) & 1)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double signalToValue(int signal, double a, double b) {
        return a * signal + b;
    }

    public Features getBigEndianFeatures(int start, int length) {
        int firstLow;
        int firstHigh;
        int midStartByte;
        int midEndByte;
        int lastLow;
        int lastHigh;

        boolean hasFirst;
        boolean hasMid;
        boolean hasLast;

        //first step
        if (start % 8 == 7) {
            hasFirst = false;
            firstHigh = firstLow = 0;
        }
        else {
            hasFirst = true;
            firstHigh = start;
            if (start % 8 >= length) {
                firstLow = start - length + 1;
                hasMid = hasLast = false;
                midStartByte = midEndByte = lastHigh = lastLow = 0;
                return new Features(firstLow, firstHigh, midStartByte, midEndByte, lastLow,
                        lastHigh, hasFirst, hasMid, hasLast);
            }
            else {
                firstLow = start / 8 * 8;
            }
        }

        //second step
        int firstRowNumber;
        if (hasFirst) {
            firstRowNumber = firstHigh - firstLow + 1;
        }
        else {
            firstRowNumber = 0;
        }

        int leftLength = length - firstRowNumber;

        if (leftLength < 8) {
            hasMid = false;
            midStartByte = midEndByte = 0;
        }
        else {
            hasMid = true;
            midStartByte = hasFirst ? start / 8 + 1 : start / 8;
            midEndByte = leftLength / 8 + midStartByte - 1;
        }

        //third step
        if (leftLength % 8 == 0) {
            hasLast = false;
            lastHigh = lastLow = 0;
        }
        else {
            hasLast = true;
            int lastRowLength = leftLength - (midEndByte - midStartByte + 1) * 8;
            lastHigh = (midEndByte + 2) * 8 - 1;
            lastLow = lastHigh - lastRowLength + 1;
        }

        return new Features(firstLow, firstHigh, midStartByte, midEndByte, lastLow,
                lastHigh, hasFirst, hasMid, hasLast);
    }

    //得到低n位全为1的数
    private int getAllOneMask(int n) {
        int mask = 1;
        for (int i = 1; i < n; ++i) {
            mask = (mask << 1) + 1;
        }
        return mask;
    }
}
