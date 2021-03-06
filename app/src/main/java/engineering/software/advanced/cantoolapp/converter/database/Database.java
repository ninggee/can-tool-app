package engineering.software.advanced.cantoolapp.converter.database;


import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.Impl.CanMessageUnionSignal;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by lhr on 2017/10/11.
 */

public interface Database {
    //根据ID寻找message
    public CanMessage searchMessageUseId(Long id);

    //根据message寻找后面的signal
    public Set<CanSignal> searchSignalUseMessage(CanMessage message);

    /**
     * 将数据库的的信息全部找到并且储存在一个set中
     * CanMessageUnionSignal是一条完整的信息
     */
    public Set<CanMessageUnionSignal> AllMessageToSet();

    //显示数据库中所有的message信息,并且转成json格式的字符串
    public String searchAllMessage();

    public String dbcTreeTojson();

}
