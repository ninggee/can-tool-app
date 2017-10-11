package engineering.software.advanced.cantoolapp.Converter.database;

import java.util.List;

import engineering.software.advanced.cantoolapp.Converter.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.Entity.CanSignal;

/**
 * Created by lhr on 2017/10/11.
 */

public interface DataBase {
    //根据ID寻找message
    public CanMessage searchMessageUseId(Long id);

    //根据message寻找后面的signal
    public List<CanSignal> searchSignalUseMessage(CanMessage message);
}
