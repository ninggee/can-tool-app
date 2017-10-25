package engineering.software.advanced.cantoolapp.exportFile;

import java.util.List;
import java.util.Map;

import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by lhr on 2017/10/24.
 */

public interface Export {
    public void export(String path, String name, String format, List<MessagesWrapper> canList);
}
