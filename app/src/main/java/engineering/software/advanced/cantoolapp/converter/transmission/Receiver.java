package engineering.software.advanced.cantoolapp.converter.transmission;

import engineering.software.advanced.cantoolapp.converter.entity.ExtensionFrame;
import engineering.software.advanced.cantoolapp.converter.entity.StandardFrame;
import engineering.software.advanced.cantoolapp.converter.enumeration.FrameType;

/**
 * Created by Zhang Dongdi on 2017/10/10.
 */

public interface Receiver {

    //解析收到的信号属于哪一类指令
    public FrameType identifyType(String message);

    //解析是否类应答
    public boolean parseYN(String message);

    //解析版本应答
    public String parseVersion(String message);

    //解析标准帧应答
    public StandardFrame parseStandardFrame(String message);

    //解析扩展帧应答
    public ExtensionFrame parseExtensionFrame(String message);

    //将一个消息加入到应答消息队列中
    public boolean addAnswerMessage(String message);

    //将一个消息加入到数据消息队列中
    public boolean addDataMessage(String message);

    //从应答消息队列中取出下一个应答消息，队列为空则阻塞
    public String takeAnswerMessage(String message) throws InterruptedException;

    //从数据消息队列中去除下一个数据消息，队列为空则阻塞
    public String takeDataMessage(String message) throws InterruptedException;
}
