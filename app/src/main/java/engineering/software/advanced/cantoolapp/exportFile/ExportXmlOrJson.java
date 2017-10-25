package engineering.software.advanced.cantoolapp.exportFile;

import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by lhr on 2017/10/25.
 */

public interface ExportXmlOrJson {

    //转换成Json文件
    public void exportJson(Set<Map<String,MessagesWrapper>> canSet, String filename, String path);

    //转换成xml文件
    public void exportXml(Set<Map<String,MessagesWrapper>> canSet, String filename, String path);
}
