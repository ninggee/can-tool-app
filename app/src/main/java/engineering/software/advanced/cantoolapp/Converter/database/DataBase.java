package engineering.software.advanced.cantoolapp.Converter.database;


import java.util.Set;

import engineering.software.advanced.cantoolapp.Converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.entity.CanSignal;

/**
 * Created by lhr on 2017/10/11.
 */

public interface DataBase {
    //根据ID寻找message
    public CanMessage searchMessageUseId(Long id);

    //根据message寻找后面的signal
    public Set<CanSignal> searchSignalUseMessage(CanMessage message);

}
