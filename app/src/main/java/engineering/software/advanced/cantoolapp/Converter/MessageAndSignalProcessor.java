package engineering.software.advanced.cantoolapp.Converter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import engineering.software.advanced.cantoolapp.Converter.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.Entity.Data;
import engineering.software.advanced.cantoolapp.Converter.Entity.Frame;
import engineering.software.advanced.cantoolapp.Converter.Entity.Message;
import engineering.software.advanced.cantoolapp.Converter.Entity.Signal;
import engineering.software.advanced.cantoolapp.Converter.Enum.FrameType;
import engineering.software.advanced.cantoolapp.Converter.analyze.DataConverter;
import engineering.software.advanced.cantoolapp.Converter.analyze.Impl.DataConverterImpl;
import engineering.software.advanced.cantoolapp.Converter.database.DataBase;
import engineering.software.advanced.cantoolapp.Converter.database.Impl.DataBaseImpl;
import engineering.software.advanced.cantoolapp.Converter.transmission.Impl.ReceiverImpl;
import engineering.software.advanced.cantoolapp.Converter.transmission.Receiver;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */

public class MessageAndSignalProcessor {
    private Receiver receiver = new ReceiverImpl();
    private DataBase dataBase = new DataBaseImpl();
    private DataConverter converter = new DataConverterImpl();

    //将接收到的总线数据一步步处理，最终变成信息信号
    public Message decode(String canMessageStr) throws IOException {
        FrameType canMessageType = receiver.identifyType(canMessageStr);
        Frame frame;
        switch (canMessageType) {
            case StandardFrame:
                frame = receiver.parseStandardFrame(canMessageStr);
                break;
            case ExtensionFrame:
                frame = receiver.parseExtensionFrame(canMessageStr);
                break;
            default:
                throw new RuntimeException("Unknown message type.");
        }

        Long frameId = Long.parseLong(frame.getId(), 16);

        CanMessage canMessage = dataBase.searchMessageUseId(frameId);
        Set<CanSignal> canSignals = dataBase.searchSignalUseMessage(canMessage);

        Set<Signal> signals = new HashSet<Signal>();
        for (CanSignal canSignal : canSignals) {
//            int origin;
//            switch (canSignal.getEndian()) {
//                case BIG_ENDIAN:
//                    origin = converter.bigEndianDecodeSignal(new Data(frame.getData()), canSignal.getStart(), canSignal.getLength());
//                    break;
//                case LITTLE_ENDIAN:
//                    origin = converter.littleEndianDecodeSignal(new Data(frame.getData()), canSignal.getStart(), canSignal.getLength());
//                    break;
//                default:
//                    throw new RuntimeException("this kind of endian is not set yet.");
//            }
            int origin = new Data(frame.getData()).getSignal(canSignal.getStart(), canSignal.getLength(), canSignal.getEndian());

            double value = converter.signalToValue(origin, canSignal.getA(), canSignal.getB());

            Signal signal = new Signal(canSignal.getSignalName(), value, origin, canSignal.getC(), canSignal.getD(), canSignal.getNodes());

            signals.add(signal);
        }

        Message message = new Message(canMessage.getId(), canMessage.getMessageName(), canMessage.getNodeName(), signals);

        return message;
    }

    public String encode(Message message) {


        return null;
    }
}
