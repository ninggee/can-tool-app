package engineering.software.advanced.cantoolapp.export.Impl;

import java.util.List;

import engineering.software.advanced.cantoolapp.converter.entity.Signal;
import engineering.software.advanced.cantoolapp.export.DataToFile;
import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by Zhang Dongdi on 2017/10/25.
 */

public class DataToFileImpl implements DataToFile {
    @Override
    public String toXml(List<MessagesWrapper> wrappers) {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        result += "<MessagesWrapperList>\n";

        for (MessagesWrapper wrapper : wrappers) {
            result += "<MessagesWrapper>\n";
            result += "<time>" + wrapper.getTime() + "</time>\n";
            result += "<id>" + wrapper.getId() + "</id>\n";
            result += "<chn>" + wrapper.getChn() + "</chn>\n";
            result += "<name>" + wrapper.getName() + "</name>\n";
            result += "<dir>" + wrapper.getDir() + "</dir>\n";
            result += "<dlc>" + wrapper.getDlc() + "</dlc>\n";
            result += "<data>" + wrapper.getData() + "</data>\n";
            result += "<Signal>\n";
            for (Signal signal : wrapper.getSignals()) {
                result += "<name>" + signal.getName() + "</name>\n";
                result += "<origin>" + signal.getOrigin() + "</origin>\n";
                result += "<value>" + signal.getValue() + "</value>\n";
                result += "<CanSignal>\n";

                result += "<sg>" + signal.getCanSignal().getSg() + "</sg>\n";
                result += "<signalName>" + signal.getCanSignal().getSignalName() + "</signalName>\n";
                result += "<divide>" + signal.getCanSignal().getDivide() + "</divide>\n";
                result += "<slt>" + signal.getCanSignal().getSlt() + "</slt>\n";
                result += "<a>" + signal.getCanSignal().getA() + "</a>\n";
                result += "<b>" + signal.getCanSignal().getB() + "</b>\n";
                result += "<c>" + signal.getCanSignal().getC() + "</c>\n";
                result += "<d>" + signal.getCanSignal().getD() + "</d>\n";
                result += "<unit>" + signal.getCanSignal().getUnit() + "</unit>\n";
                result += "<nodeName>" + signal.getCanSignal().getNodeName() + "</nodeName>\n";

                result += "</CanSignal>\n";
            }
            result += "</Signal>\n";

            result += "</MessagesWrapper>\n";
        }

        result += "</MessagesWrapperList>\n";
        return result;
    }

    @Override
    public boolean toFile(String str) {
        return false;
    }
}