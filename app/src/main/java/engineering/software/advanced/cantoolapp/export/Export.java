package engineering.software.advanced.cantoolapp.export;

import java.util.List;

import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by lhr on 2017/10/25.
 */

public interface Export {

    /**
     *
     * @param path  路径
     * @param name  文件名称
     * @param format  文件格式.csv或者其他
     * @param canList   接收到的信息
     * @return  1表示成功，0表示失败
     */
    public int export(String path, String name, String format, List<MessagesWrapper> canList);
}
