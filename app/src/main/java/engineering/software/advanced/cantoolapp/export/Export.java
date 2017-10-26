package engineering.software.advanced.cantoolapp.export;

import java.util.List;

import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by lhr on 2017/10/25.
 */

public interface Export {
    //1表示成功，0表示失败
    public int export(String path, String name, String format, List<MessagesWrapper> canList);
}
