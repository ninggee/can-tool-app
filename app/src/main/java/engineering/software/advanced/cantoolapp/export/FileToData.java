package engineering.software.advanced.cantoolapp.export;

import java.io.InputStream;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.Impl.CanMessageUnionSignal;

/**
 * Created by Zhang Dongdi on 2017/10/27.
 */

public interface FileToData {
    public Set<CanMessageUnionSignal> xmlToCanMessageUnionSignal(String path);

    public Set<CanMessageUnionSignal> jsonToCanMessageUnionSignal(String path);
}
