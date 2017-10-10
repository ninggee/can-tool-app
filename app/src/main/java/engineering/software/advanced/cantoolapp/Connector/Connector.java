package engineering.software.advanced.cantoolapp.Connector;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by ningge on 09/10/2017.
 */

public interface Connector {
    public abstract void connect(String path, int rate);
    public abstract Map listPort();
    public abstract InputStream getInputStream();
    public abstract OutputStream getOutputStream();
    public abstract void close();
}
