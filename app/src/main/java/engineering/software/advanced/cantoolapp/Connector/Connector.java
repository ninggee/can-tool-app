package engineering.software.advanced.cantoolapp.Connector;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by ningge on 09/10/2017.
 *
 * this interface defined connector to connect remote device
 */

public interface Connector {
    /**
     * connect to a device
     * @param path some info to identify a devide
     * @param rate baud rate of serial port
     */
    public abstract void connect(String path, int rate);

    /**
     * list all available devices
     * @return
     */
    public abstract Map listAll();

    /**
     * get inputstream to read data
     * @return
     */
    public abstract InputStream getInputStream();

    /**
     * get outputstream to write data
     * @return
     */
    public abstract OutputStream getOutputStream();

    /**
     * close connection to device
     */
    public abstract void close();
}
