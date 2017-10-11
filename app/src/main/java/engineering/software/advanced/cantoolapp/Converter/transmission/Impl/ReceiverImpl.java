package engineering.software.advanced.cantoolapp.Converter.transmission.Impl;

import engineering.software.advanced.cantoolapp.Converter.transmission.Entity.ExtensionFrame;
import engineering.software.advanced.cantoolapp.Converter.transmission.Entity.StandardFrame;
import engineering.software.advanced.cantoolapp.Converter.transmission.Enum.FrameDirection;
import engineering.software.advanced.cantoolapp.Converter.transmission.Receiver;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public class ReceiverImpl implements Receiver {
    @Override
    public String identifyType(String message) {
        return null;
    }

    @Override
    public boolean parseYN(String message) {
        if (message.equals("\\r")) {
            return true;
        }
        else if (message.equals("\\BEL")) {
            return false;
        }
        else {
            throw new RuntimeException("Unknown YN message.");
        }
    }

    @Override
    public String parseVersion(String message) {
        return message.replace("\\r", "");
    }

    @Override
    public StandardFrame parseStandardFrame(String message) {
        String raw = message.replace("\\r", "");
        String id = raw.substring(1, 4);
        int length = Integer.parseInt(raw.substring(4, 5));
        String data = raw.substring(5, 5 + length * 2);

        //data之后没有别的信息了，是从装置发送给上位机的信息
        FrameDirection direction;
        String period;
        if (raw.length() == 5 + length * 2) {
            direction = FrameDirection.DEVICE_TO_APP;
            period = null;
        }
        //data之后还有4位信息，是从上位机发送给装置的信息
        else if (raw.length() == 5 + length * 2 + 4) {
            direction = FrameDirection.APP_TO_DEVICE;
            period = raw.substring(5 + length * 2);
        }
        else {
            throw new RuntimeException("The length of this standard frame is illegal.");
        }
        return new StandardFrame(raw, id, String.valueOf(length), data, period, direction);
    }

    @Override
    public ExtensionFrame parseExtensionFrame(String message) {
        String raw = message.replace("\\r", "");
        String id = raw.substring(1, 9);
        int length = Integer.parseInt(raw.substring(9, 10));
        String data = raw.substring(10, 10 + length * 2);

        //data之后没有别的信息了，是从装置发送给上位机的信息
        FrameDirection direction;
        String period;
        if (raw.length() == 10 + length * 2) {
            direction = FrameDirection.DEVICE_TO_APP;
            period = null;
        }
        //data之后还有4位信息，是从上位机发送给装置的信息
        else if (raw.length() == 10 + length * 2 + 4) {
            direction = FrameDirection.APP_TO_DEVICE;
            period = raw.substring(10 + length * 2);
        }
        else {
            throw new RuntimeException("The length of this extension frame is illegal.");
        }
        return new ExtensionFrame(raw, id, String.valueOf(length), data, period, direction);
    }
}