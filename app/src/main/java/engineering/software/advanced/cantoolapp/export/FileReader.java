package engineering.software.advanced.cantoolapp.export;

import java.io.InputStream;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.Impl.CanMessageUnionSignal;

/**
 * Created by Zhang Dongdi on 2017/10/27.
 */

public interface FileReader {
    public Set<CanMessageUnionSignal> xmlToCanMessageUnionSignal(InputStream stream);

    public Set<CanMessageUnionSignal> jsonToCanMessageUnionSignal(InputStream stream);
}
