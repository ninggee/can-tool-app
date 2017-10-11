package engineering.software.advanced.cantoolapp.Converter.Entity;

import engineering.software.advanced.cantoolapp.Converter.Enum.Endian;
import engineering.software.advanced.cantoolapp.Converter.analyze.DataConverter;
import engineering.software.advanced.cantoolapp.Converter.analyze.Impl.DataConverterImpl;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public class Data {
    private int[] data;
    private DataConverter converter;

    public Data(String dataStr) {
        converter = new DataConverterImpl();
        int length = dataStr.length() / 2;
        data = new int[length];
        for (int i = 0; i < length; i++) {
            String s = dataStr.substring(i * 2, i * 2 + 2);
            data[i] = Integer.parseInt(s, 16);
        }
    }

    public int[] getData() {
        return data;
    }

    public int getByte(int i) {
        return data[i];
    }

    public int getBit(int i) {
        int by = data[i / 8];
        int index = i % 8;
//        System.out.println(String.format("by: %x, index: %d",by, index));
//        System.out.println(String.format("%x", 1 << index));
//        System.out.println(String.format("%x", by & (1 << index)));
        return (by & (1 << index)) >> index;
    }

    public int getSignal(int start, int length, Endian endian) {
        if (endian.equals(Endian.BIG_ENDIAN)) {
            return converter.bigEndianConvertSignal(this, start, length);
        }
        else if (endian.equals(Endian.LITTLE_ENDIAN)) {
            return converter.littleEndianConvertSignal(this, start, length);
        }
        else {
            throw new RuntimeException("the type of endian is not set yet.");
        }
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < data.length * 8; i++) {
            result += this.getBit(i);
            if (i % 8 == 7) {
                result += "\n";
            }
        }
        return result;
    }
}
