package engineering.software.advanced.cantoolapp.Converter.analyze.Impl;

import engineering.software.advanced.cantoolapp.Converter.Entity.Data;
import engineering.software.advanced.cantoolapp.Converter.analyze.DataConverter;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public class DataConverterImpl implements DataConverter {
    @Override
    public int bigEndianConvertSignal(Data data, int start, int length) {
        System.out.println(data);
        System.out.println("start: " + start + ", length: " + length);

        if (start / 8 == (start - length  + 1) / 8) {
            //数据同一个字节中，那么可以简单求出signal的值

            int signal = 0;
            int by = data.getByte(start / 8);
            for (int i = start; i >= start - length + 1; i--) {
                System.out.println("next bit: " + data.getBit(i));
                signal = (signal << 1) + data.getBit(i);
            }
            return signal;
        }
        else {
            //数据不在同一个字节中，此时signal的值需要分3部求出

            int firstRowCount = start - start / 8 * 8 + 1;
            int middleRow = (length - firstRowCount - 1) / 8;
            int last = (start / 8/*起始行*/ + middleRow/*中间行数*/ + 2) * 8 - (length - firstRowCount) % 8;
            if ((length - firstRowCount) % 8 == 0) {
                last -= 8;
            }
            System.out.println("last:" + last);

            System.out.println("next bit: " + data.getBit(start));
            int signal = data.getBit(start);

            //第一步是完成第一行未占满的byte的计算
            for (int i = start - 1; i % 8 != 7; i--) {
                System.out.println("next bit: " + data.getBit(i));
                signal = (signal << 1) + data.getBit(i);
            }

            //第二部是完成中间占满的所有行的计算
            for (int i = start / 8 + 1; i <= last / 8 - 1; i++) {
                //i表示行，i遍历所有满行
                for (int j = (i + 1) * 8 - 1; j >= i * 8; j--) {
                    System.out.println("next bit: " + data.getBit(j));
                    signal = (signal << 1) + data.getBit(j);
                }
            }

            //第三步 完成最后一行的计算
            for (int i = (last / 8 + 1) * 8 - 1; i >= last; i--) {
                System.out.println("next bit: " + data.getBit(i));
                signal = (signal << 1) + data.getBit(i);
            }

            return signal;
        }
    }

    @Override
    public int littleEndianConvertSignal(Data data, int start, int length) {
        int signal = 0;
        for (int i = start + length - 1; i >= start; i--) {
            int by = data.getByte(i / 8);
            int index = i % 8;
            int bit = (by & (1 << index)) >> index;
            signal = (signal << 1) + bit;
        }
        return signal;
    }

    @Override
    public double signalToValue(int signal, double a, double b) {
        return a * signal + b;
    }
}
