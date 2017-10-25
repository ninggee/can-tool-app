package engineering.software.advanced.cantoolapp.exportFIle;

import java.util.List;
import java.util.Map;

/**
 * Created by lhr on 2017/10/24.
 */

public interface Export {
    public void export(String path, String name, String format, List<Map> canList);
}
