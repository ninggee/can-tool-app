package engineering.software.advanced.cantoolapp.export;

import java.util.List;

import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by Zhang Dongdi on 2017/10/25.
 */

public interface DataToFile {

    public String toXml(List<MessagesWrapper> wrappers);

    public boolean toFile(String str);
}
