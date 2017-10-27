package engineering.software.advanced.cantoolapp.export.Impl;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.Impl.CanMessageUnionSignal;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
import engineering.software.advanced.cantoolapp.export.FileToData;

/**
 * Created by Zhang Dongdi on 2017/10/27.
 */

public class FileToDataImpl implements FileToData {

    private static FileToData fileToData = new FileToDataImpl();

    private FileToDataImpl() {

    }

    public static FileToData getInstance() {
        return fileToData;
    }

    @Override
    public Set<CanMessageUnionSignal> xmlToCanMessageUnionSignal(String path) {
        try {
            InputStream stream = new FileInputStream(path);

            XmlPullParserFactory pullParserFactory= XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setInput(stream, "UTF-8");
            int eventType = parser.getEventType();// 获取事件类型

            Set<CanMessageUnionSignal> unions = null;
            CanMessageUnionSignal union = null;
            CanMessage canMessage = null;
            Set<CanSignal> canSignalSet = null;
            CanSignal canSignal = null;
            String name;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG://开始读取某个标签
                        name = parser.getName();
                        //System.out.println("read " + name);

                        if (name.equals("CanMessageUnionSignalSet")) {
                            unions = new HashSet<>();
                        }
                        else if (name.equals("CanMessageUnionSignal")) {
                            union = new CanMessageUnionSignal();
                        }
                        else if (name.equals("CanMessage")) {
                            canMessage = new CanMessage();
                        }
                        else if (name.equals("bo")) {
                            canMessage.setBo(parser.nextText());
                        }
                        else if (name.equals("id")) {
                            canMessage.setId(Long.parseLong(parser.nextText()));
                        }
                        else if (name.equals("messageName")) {
                            canMessage.setMessageName(parser.nextText());
                        }
                        else if (name.equals("divide")) {
                            canMessage.setDivide(parser.nextText());
                        }
                        else if (name.equals("dlc")) {
                            canMessage.setDlc(Integer.parseInt(parser.nextText()));
                        }
                        else if (name.equals("nodeName")) {
                            canMessage.setNodeName(parser.nextText());
                        }
                        else if (name.equals("CanSignals")) {
                            canSignalSet = new HashSet<>();
                        }
                        else if (name.equals("CanSignal")) {
                            canSignal = new CanSignal();
                        }
                        else if (name.equals("sg")) {
                            canSignal.setSg(parser.nextText());
                        }
                        else if (name.equals("signalName")) {
                            canSignal.setSignalName(parser.nextText());
                        }
                        else if (name.equals("signalDivide")) {
                            canSignal.setDivide(parser.nextText());
                        }
                        else if (name.equals("slt")) {
                            canSignal.setSlt(parser.nextText());
                        }
                        else if (name.equals("a")) {
                            canSignal.setA(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("b")) {
                            canSignal.setB(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("c")) {
                            canSignal.setC(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("d")) {
                            canSignal.setD(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("unit")) {
                            canSignal.setUnit(parser.nextText());
                        }
                        else if (name.equals("signalNodeName")) {
                            canSignal.setNodeName(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        name = parser.getName();
                        //System.out.println("end " + name);

                        if (name.equals("CanMessageUnionSignalSet")) {

                        }
                        else if (name.equals("CanMessageUnionSignal")) {
                            unions.add(union);
                        }
                        else if (name.equals("CanMessage")) {
                            union.setCanMessage(canMessage);
                        }
                        else if (name.equals("bo")) {

                        }
                        else if (name.equals("id")) {

                        }
                        else if (name.equals("messageName")) {

                        }
                        else if (name.equals("divide")) {

                        }
                        else if (name.equals("dlc")) {

                        }
                        else if (name.equals("nodeName")) {

                        }
                        else if (name.equals("CanSignals")) {
                            union.setCanSignals(canSignalSet);
                        }
                        else if (name.equals("CanSignal")) {
                            canSignalSet.add(canSignal);
                        }
                        else if (name.equals("sg")) {

                        }
                        else if (name.equals("signalName")) {

                        }
                        else if (name.equals("signalDivide")) {

                        }
                        else if (name.equals("slt")) {

                        }
                        else if (name.equals("a")) {

                        }
                        else if (name.equals("b")) {

                        }
                        else if (name.equals("c")) {

                        }
                        else if (name.equals("d")) {

                        }
                        else if (name.equals("unit")) {

                        }
                        else if (name.equals("signalNodeName")) {

                        }
                        break;
                }
                eventType = parser.next();
            }

            stream.close();
            return unions;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<CanMessageUnionSignal> jsonToCanMessageUnionSignal(String path) {
        return null;
    }
}
