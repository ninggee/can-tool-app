package engineering.software.advanced.cantoolapp.converter.database;


import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by lhr on 2017/10/11.
 */

public interface DataBase {
    //根据ID寻找message
    public CanMessage searchMessageUseId(Long id, InputStreamReader isr);

    //根据message寻找后面的signal
    public Set<CanSignal> searchSignalUseMessage(CanMessage message, InputStreamReader isr);

}
