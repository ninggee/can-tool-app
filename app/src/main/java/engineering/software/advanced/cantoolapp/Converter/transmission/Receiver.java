package engineering.software.advanced.cantoolapp.Converter.transmission;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public interface Receiver {

    //解析收到的信号属于哪一类指令
    public String identifyType(String message);

    //解析是否类应答
    public boolean parseYN(String message);

    //解析版本应答
    public String parseVersion(String message);

    //解析标准帧应答
    public String parseStandardFrame(String message);

    //解析扩展帧应答
    public String parseExtensionFrame(String message);
}
