package engineering.software.advanced.cantoolapp.converter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.analyze.DataConverter;
import engineering.software.advanced.cantoolapp.converter.analyze.Impl.DataConverterImpl;
import engineering.software.advanced.cantoolapp.converter.database.Database;
import engineering.software.advanced.cantoolapp.converter.database.Impl.DatabaseImpl;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
import engineering.software.advanced.cantoolapp.converter.entity.Data;
import engineering.software.advanced.cantoolapp.converter.entity.Frame;
import engineering.software.advanced.cantoolapp.converter.entity.Message;
import engineering.software.advanced.cantoolapp.converter.entity.Signal;
import engineering.software.advanced.cantoolapp.converter.enumeration.Endian;
import engineering.software.advanced.cantoolapp.converter.enumeration.FrameType;
import engineering.software.advanced.cantoolapp.converter.transmission.Impl.ReceiverImpl;
import engineering.software.advanced.cantoolapp.converter.transmission.Receiver;

/**
 * Created by Zhang Dongdi on 2017/10/12.
 */

public class MessageAndSignalProcessor implements Processor {
    private Receiver receiver = ReceiverImpl.getInstance();
    private Database database = new DatabaseImpl();
    private DataConverter converter = DataConverterImpl.getInstance();

    private static MessageAndSignalProcessor processor = new MessageAndSignalProcessor();

    private MessageAndSignalProcessor() {

    }

    public static MessageAndSignalProcessor getInstance() {
        return processor;
    }

    public Message decode(String canMessageStr) {
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



        CanMessage canMessage = database.searchMessageUseId(frameId);
        Set<CanSignal> canSignals = database.searchSignalUseMessage(canMessage);

        Set<Signal> signals = new HashSet<Signal>();
        Data data = new Data(frame.getData());
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

            Endian endian = canSignal.getEndian();

            if (endian == Endian.ONE_MINUS || endian == Endian.ZERO_MINUS) {
                continue;
            }

            int origin = data.getSignal(canSignal.getStart(), canSignal.getLength(), endian);

            double value = converter.signalToValue(origin, canSignal.getA(), canSignal.getB());

            Signal signal = new Signal(canSignal.getSignalName(), String.format("%x", origin), value, canSignal);

            signals.add(signal);
        }

        Message message = new Message(canMessage.getId(), canMessage.getMessageName(), canMessage.getNodeName(),canMessage, data, signals);

        return message;
    }

    @Override
    public Set<Message> decodeMultiple(String strs) {
        String[] strList = strs.split("\\r");
        Set<Message> result = new HashSet<Message>();
        for (String str : strList) {
//            System.out.println("next str: " + str);
            result.add(decode(str));
        }
        return result;
    }

    @Override
    public String encode(long messageId, Map<String, Double> signalMap, int period) {
        String result = "";
        CanMessage canMessage = database.searchMessageUseId(messageId);
        Set<CanSignal> canSignals = database.searchSignalUseMessage(canMessage);
        Set<Signal> signals = new HashSet<>();
        for (CanSignal canSignal : canSignals) {
            Double value = signalMap.get(canSignal.getSignalName());
            if (value == null) {
                throw new RuntimeException(String.format("%s is not in the signal map.", canSignal.getSignalName()));
            }
            int origin = (int)((value - canSignal.getB()) / canSignal.getA() + 0.5);
            Signal signal = new Signal(canSignal.getSignalName(), String.format("%x", origin), value, canSignal);
            signals.add(signal);
        }

        String idStr;
        switch (canMessage.getFrameType()) {
            case StandardFrame:
                result += "t";
                idStr = String.format("%03x", messageId);
                break;
            case ExtensionFrame:
                result += "T";
                idStr = String.format("%08x", messageId);
                break;
            default:
                throw new RuntimeException("this type of frame is not set yet.");
        }
        result += idStr + canMessage.getDlc();

        Data data = new Data(canMessage.getDlc());
        for (Signal signal : signals) {
            for (CanSignal canSignal : canSignals) {
                //System.out.println(canSignal.getSignalName() + " " + signal.getName());
                if (canSignal.getSignalName().equals(signal.getName())) {
                    int originValue = (int)((signal.getValue() - canSignal.getB()) / canSignal.getA() + 0.5);
                    if (!data.setSignal(canSignal.getStart(), canSignal.getLength(), originValue, canSignal.getEndian())) {
                        return null;
                    }
                    break;
                }
            }
        }

        result += data.toHexString();

        result += String.format("%04x", period);

        return result;
    }

    @Override
    public void setDatabase(String path) {
        this.database = new DatabaseImpl(path);
    }

    /*
    public String encode(long messageId, Set<Signal> signals, int period) {
        String result = "";
        CanMessage canMessage = database.searchMessageUseId(messageId);
        Set<CanSignal> canSignals = database.searchSignalUseMessage(canMessage);
        if (signals.size() != canSignals.size()) {
            throw new RuntimeException("The number of signals doesn't fit the number in the database.");
        }

        switch (canMessage.getFrameType()) {
            case StandardFrame:
                result += "t";
                break;
            case ExtensionFrame:
                result += "T";
                break;
            default:
                throw new RuntimeException("this type of frame is not set yet.");
        }
        String idStr;
        switch (canMessage.getFrameType()) {
            case StandardFrame:
                idStr = String.format("%03x", messageId);
                break;
            case ExtensionFrame:
                idStr = String.format("%08x", messageId);
                break;
            default:
                throw new RuntimeException("this type of frame is not set yet.");
        }
//        System.out.println(idStr);

        result += idStr;

        result += canMessage.getDlc();

        Data data = new Data(canMessage.getDlc());
//        for (Signal signal : message.getSignals()) {
//            CanSignal canSignal = signal.getCanSignal();
//            int originValue = (int)((signal.getValue() - canSignal.getB()) / canSignal.getA() + 0.5);
//            if (!data.setSignal(canSignal.getStart(), canSignal.getLength(), originValue, canSignal.getEndian())) {
//                return null;
//            }
//        }
        for (Signal signal : signals) {
            for (CanSignal canSignal : canSignals) {
                //System.out.println(canSignal.getSignalName() + " " + signal.getName());
                if (canSignal.getSignalName().equals(signal.getName())) {
                    int originValue = (int)((signal.getValue() - canSignal.getB()) / canSignal.getA() + 0.5);
//                    System.out.println("signal: " + signal.getName() + " value: " + signal.getValue() + " origin: " + originValue);
                    if (!data.setSignal(canSignal.getStart(), canSignal.getLength(), originValue, canSignal.getEndian())) {
                        return null;
                    }
                    break;
                }
            }
        }
//        System.out.println(data.toString());

        result += data.toHexString();

        result += String.format("%04x", period);

        return result;
    }
    */


}
