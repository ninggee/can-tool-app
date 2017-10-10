package engineering.software.advanced.cantoolapp.Converter.transmission.Impl;

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
        return false;
    }

    @Override
    public String parseVersion(String message) {
        return null;
    }

    @Override
    public String parseStandardFrame(String message) {
        return null;
    }

    @Override
    public String parseExtensionFrame(String message) {
        return null;
    }
}
