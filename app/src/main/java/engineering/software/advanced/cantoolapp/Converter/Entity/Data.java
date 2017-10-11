package engineering.software.advanced.cantoolapp.Converter.Entity;

import engineering.software.advanced.cantoolapp.Converter.Enum.Endian;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public class Data {
    private int[] data;

    public Data(String dataStr) {
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
            //TODO 大端方法取数据
            return -1;
        }
        else if (endian.equals(Endian.LITTLE_ENDIAN)) {
            //TODO 小端方法取数据
            return -1;
        }
        else {
            throw new RuntimeException("the type of endian is not set yet.");
        }
    }
}
