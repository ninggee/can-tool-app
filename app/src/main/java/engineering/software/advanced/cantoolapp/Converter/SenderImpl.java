package engineering.software.advanced.cantoolapp.Converter;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public class SenderImpl implements Sender {

    public String requestVersion() {
        return "V\\r";
    }

    public String open() {
        return "O1\\r";
    }

    public String setSpeed(int n) {
        if (n < 0 || n > 8) {
            throw new RuntimeException("The value of n is illegal:" +
            "n should be an integer between 0 and 8.");
        }
        return String.format("S%d\\r", n);
    }

    public String close() {
        return "C\\r";
    }

    public String sendStandardFrame(String id, String value, String period) {
        if (id.length() != 3) {
            throw new RuntimeException("The length of id should be 3.");
        }
        if (period.length() != 4) {
            throw new RuntimeException("The length of period should be 4");
        }
        return String.format("t%s%d%s%s\\r", id, value.length() / 2, value, period);
    }

    public String sendExtensionFrame(String id, String value, String period) {
        if (id.length() != 8) {
            throw new RuntimeException("The length of id should be 8.");
        }
        if (period.length() != 4) {
            throw new RuntimeException("The length of period should be 4");
        }
        return String.format("t%s%d%s%s\\r", id, value.length() / 2, value, period);
    }

    public boolean send(String command) {
        //TODO operation about connection and sending message
        return false;
    }
}
