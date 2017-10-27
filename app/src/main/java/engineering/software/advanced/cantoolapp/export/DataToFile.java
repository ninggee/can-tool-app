package engineering.software.advanced.cantoolapp.export;

import java.util.List;

import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by Zhang Dongdi on 2017/10/25.
 */

public interface DataToFile {

    //转换成xml文件
    public String toXml(List<MessagesWrapper> wrappers);

    //将String写入写文件
    public boolean toFile(String str, String  path, String filename, String fomat);

    //装换成json文件
    public String  toJson(List<MessagesWrapper> wrappers);
}
