package engineering.software.advanced.cantoolapp.Converter.Entity;

import engineering.software.advanced.cantoolapp.Converter.Enum.Endian;
import engineering.software.advanced.cantoolapp.Converter.analyze.DataConverter;
import engineering.software.advanced.cantoolapp.Converter.analyze.Impl.DataConverterImpl;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public class Data {
    private long data;
    private int length;
    private DataConverter converter;

    public Data(String dataStr) {
        converter = new DataConverterImpl();
        length = dataStr.length() / 2;
        data = 0;
        for (int i = 0; i < length; i++) {
            //s为两位16进制的数字
            String s = dataStr.substring(i * 2, i * 2 + 2);
            int val = Integer.parseInt(s, 16);
            data = (data << 8) | val;
        }
    }

    public long getData() {
        return data;
    }

    public int getByte(int i) {
        return (int)((data >> ((length - i - 1) * 8)) & 255);
    }

    public int getBit(int i) {
        int index = length * 8 - i - 1;
        return (int)((data >> index) & 1);
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
        String result = "Data {\n";
        for (int i = 0; i < length; i++) {
            int by = (int)((data >> ((length - i - 1) * 8)) & 255);
            String byStr = "";
            for (int j = 0; j < 8; j++) {
                byStr = byStr + " " + (by & 1);
                by = by >> 1;
            }
            result += "\t" + byStr + "\n";
        }
        result += "}";

        return result;
    }
}
