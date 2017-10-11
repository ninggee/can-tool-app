package engineering.software.advanced.cantoolapp.Connector;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by ningge on 2017/10/10.
 */

public class BuletoothConnector implements Connector {
    @Override
    public void connect(String path, int rate) {

    }

    @Override
    public Map listPort() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public void close() {

    }
}
