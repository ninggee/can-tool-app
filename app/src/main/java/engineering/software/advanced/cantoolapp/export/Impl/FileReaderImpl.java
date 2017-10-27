package engineering.software.advanced.cantoolapp.export.Impl;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.Impl.CanMessageUnionSignal;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
import engineering.software.advanced.cantoolapp.export.FileReader;

/**
 * Created by Zhang Dongdi on 2017/10/27.
 */

public class FileReaderImpl implements FileReader {

    private static FileReader fileReader = new FileReaderImpl();

    private FileReaderImpl() {

    }

    public static FileReader getInstance() {
        return fileReader;
    }

    @Override
    public Set<CanMessageUnionSignal> xmlToCanMessageUnionSignal(InputStream stream) {
        try {

            XmlPullParserFactory pullParserFactory= XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setInput(stream, "UTF-8");
            int eventType = parser.getEventType();// 获取事件类型

            Set<CanMessageUnionSignal> unions = null;
            CanMessageUnionSignal union = null;
            CanMessage canMessage = null;
            Set<CanSignal> canSignalSet = null;
            CanSignal canSignal = null;


            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG://开始读取某个标签
                        String name = parser.getName();
                        System.out.println("read " + name);

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
                            canMessage.setBo(parser.getText());
                        }
                        else if (name.equals("id")) {
                            System.out.println();
                            canMessage.setId(Long.parseLong(parser.getText()));
                        }
                        else if (name.equals("messageName")) {
                            canMessage.setMessageName(parser.getText());
                        }
                        else if (name.equals("divide")) {
                            canMessage.setDivide(parser.getText());
                        }
                        else if (name.equals("dlc")) {
                            canMessage.setDlc(Integer.parseInt(parser.getText()));
                        }
                        else if (name.equals("nodeName")) {
                            canMessage.setNodeName(parser.getText());
                        }

                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        System.out.println("end");
                        break;
                }
                eventType = parser.next();
            }


            stream.close();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<CanMessageUnionSignal> jsonToCanMessageUnionSignal(InputStream stream) {
        return null;
    }
}
