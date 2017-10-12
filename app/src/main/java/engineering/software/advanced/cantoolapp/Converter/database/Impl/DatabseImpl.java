package engineering.software.advanced.cantoolapp.Converter.database.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engineering.software.advanced.cantoolapp.Converter.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.database.CanDecoding;
import engineering.software.advanced.cantoolapp.Converter.database.DataBase;

/**
 * Created by lhr on 2017/10/11.
 */

public class DatabseImpl implements DataBase {

    CanDecoding decoding = new CanDecodingImpl();//解析用

    String messagePa = "^BO_.*";//需要匹配的message信息

    String signalPa = "^ SG_.*";//需要匹配的signal信息

    File filename = new File("C:\\Users\\lhr\\Desktop\\a.txt");//读取文件

    @Override
    public CanMessage searchMessageUseId(Long id) {

        CanMessage message = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            //读取一行
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                //判断是不是message，message类似BO_ 856 CDU_1: 8 CDU格式
                Pattern pattern = Pattern.compile(messagePa);
                Matcher m1 = pattern.matcher(line);
                message = decoding.messageDecoding(line);
                if (m1.matches() && message.getId() == id) {
                    return message;
                }
            }
            bufferedReader.close();// 关闭输入流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public List<CanSignal> searchSignalUseMessage(CanMessage message) {
        CanSignal signal = null;
        CanMessage messageStart = null;
        int start = 0;//表示开始
        List<CanSignal> signalList = new ArrayList<CanSignal>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            //读取一行
            while ((line = bufferedReader.readLine()) != null) {
                //寻找对应的message
                Pattern patternMessage = Pattern.compile(messagePa);
                Matcher mMessage = patternMessage.matcher(line);
                messageStart = decoding.messageDecoding(line);
                if (mMessage.matches() && messageStart.getId() == message.getId() && start == 0) {
                    start = 1;
                    continue;
                }
                if(start == 1){
                    //寻找message以下 的signal
                    Pattern patternSignal = Pattern.compile(signalPa);
                    Matcher mSignal = patternSignal.matcher(line);
                    if (!mSignal.matches()) {
                        start = 0;
                        break;
                    }
                    signal = decoding.signalDecoding(line);
                    signalList.add(signal);
                }
            }
            bufferedReader.close();// 关闭输入流
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return signalList;
    }
}
