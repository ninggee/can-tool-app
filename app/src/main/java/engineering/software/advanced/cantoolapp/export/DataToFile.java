package engineering.software.advanced.cantoolapp.export;

import java.util.List;

import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by Zhang Dongdi on 2017/10/25.
 */

public interface DataToFile {

    //将Can信息转换成xml文件
    public String toXml(List<MessagesWrapper> wrappers);

    //将String写入写文件
    public boolean toFile(String str, String  path, String filename, String fomat);

    //将CAN信息转换成json文件
    public String toJson(List<MessagesWrapper> wrappers);



    /**
     *  数据库转json
     * @param filename 要转换的数据库的完整路径
     * @return
     */
    public String dbToJson(String filename);


    /**
     *  数据库转xml
     * @param filename
     * @return
     */
    public String dbToXml(String filename);

    /**
     * json文件转成dbc文件
     * @param filename  完整文件路径
     * @return  是否成功
     */
    public boolean JsonToDbc(String filename);
}
