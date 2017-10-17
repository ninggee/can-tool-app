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

    //初始化data为全零
    public Data(int length) {
        this.data = 0;
        this.length = length;
        this.converter = DataConverterImpl.getInstance();
    }

    public Data(String dataStr) {
        converter = DataConverterImpl.getInstance();
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

    public boolean setByte(int i, int value) {
        if (this.getByte(i) != 0) {
            return false;
        }
        data = data | ((value & 255) << ((length - i - 1) * 8));
        return true;
    }

    private void setByteWithoutCheck(int i, int value) {
        System.out.println("set " + i + " to " + value);
        data = data | (((long)value & 255) << ((length - i - 1) * 8));
    }

    public int getBit(int i) {
        int by = this.getByte(i / 8);

        return (by >> (i % 8)) & 1;
    }

    public boolean setBit(int i, int value) {
        if (this.getBit(i) != 0) {
            return false;
        }

        System.out.println("set bit: " + i + " value: " + value);

        int by = this.getByte(i / 8);
        by = by | ((value & 1) << (i % 8));
        this.setByteWithoutCheck(i / 8, by);
        return true;
    }

    public int getSignal(int start, int length, Endian endian) {
        if (endian.equals(Endian.BIG_ENDIAN)) {
            return converter.bigEndianDecodeSignal(this, start, length);
        }
        else if (endian.equals(Endian.LITTLE_ENDIAN)) {
            return converter.littleEndianDecodeSignal(this, start, length);
        }
        else {
            throw new RuntimeException("this type of endian is not set yet.");
        }
    }

    public boolean setSignal(int start, int length, int value, Endian endian) {
        switch (endian) {
            case BIG_ENDIAN:
                return converter.bigEndianEncodeSignal(this, start, length, value);
            case LITTLE_ENDIAN:
                return converter.littleEndianEncodeSignal(this, start, length, value);
            default:
                throw new RuntimeException("this type of endian is not set yet.");
        }
    }

    public String toString() {
        String result = "Data {\n";
        for (int i = 0; i < length; i++) {
            int by = this.getByte(i);

            String byStr = "\t";
            for (int j = 0; j < 8; j++) {
                byStr += ((by >> (7 - j)) & 1) + " ";
            }
            result += byStr + "\n";
        }
        result += "}";

        return result;
    }

    public String toHexString() {
        String result = "";
        for (int i = 0; i < length; i++) {
            int by = this.getByte(i);
            result += String.format("%02x", by);
        }
        return result;
    }
}
