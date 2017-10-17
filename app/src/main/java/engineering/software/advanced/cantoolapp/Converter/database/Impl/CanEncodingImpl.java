package engineering.software.advanced.cantoolapp.Converter.database.Impl;

import engineering.software.advanced.cantoolapp.Converter.database.CanEncoding;
import engineering.software.advanced.cantoolapp.Converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.entity.CanSignal;

/**
 * Created by Zhang Dongdi on 2017/10/11.
 */

public class CanEncodingImpl implements CanEncoding {

    private static CanEncoding encoding = new CanEncodingImpl();

    private CanEncodingImpl() {}

    public static CanEncoding getInstance() {
        return encoding;
    }

    @Override
    public String messageEncoding(CanMessage message) {
        return String.format("%s %d %s%s %s %s",
                message.getBo(), message.getId(), message.getMessageName(), message.getDivide(),
                message.getDlc(), message.getNodeName());
    }

    @Override
    public String signalEncoding(CanSignal signal) {
        return String.format("%s %s %s %s (%s,%s) [%s|%s] %s %s",
                signal.getSg(), signal.getSignalName(), signal.getDivide(), signal.getSlt(),
                deleteZeroAndDot(signal.getA()), deleteZeroAndDot(signal.getB()),
                deleteZeroAndDot(signal.getC()), deleteZeroAndDot(signal.getD()),
                signal.getUnit(), signal.getNodeName());
    }

    //去除浮点数后面多余的零，并转为字符串
    private String deleteZeroAndDot(double floatNumber) {
        String s = String.valueOf(floatNumber);
//        System.out.println("before delete: " + s);
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+$", "");
            s = s.replaceAll(".$", "");
        }
//        System.out.println("after delete: " + s);
        return s;
    }
}
