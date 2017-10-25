package engineering.software.advanced.cantoolapp.converter.database.Impl;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engineering.software.advanced.cantoolapp.converter.database.CanDecoding;
import engineering.software.advanced.cantoolapp.converter.database.DataBase;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by lhr on 2017/10/11.
 */

public class DataBaseImpl implements DataBase {

    CanDecoding decoding = CanDecodingImpl.getInstance();//解析用

    String messagePa = "^BO_.*$";//需要匹配的message信息

    String signalPa = "^ SG_.*$";//需要匹配的signal信息


    private static DataBase dataBase = new DataBaseImpl();

    File filename = null;


    DataBaseImpl() {
//        filename = new File("/data/data/engineering.software.advanced.cantoolapp/files/canmsg-sample.dbc");//读取文件
                filename = new File("C:\\Users\\lhr\\Desktop\\canmsg-sample.dbc");//读取文件
    }

    DataBaseImpl(String  path) {
        filename = new File(path);//读取文件
    }

    public static DataBase getInstance() {
        return dataBase;
    }

    @Override
    public CanMessage searchMessageUseId(Long id) {

        CanMessage message = null;
        BufferedReader bufferedReader = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(filename), "GBK");
            bufferedReader = new BufferedReader(isr);
            String line = "";
            //读取一行
            while ((line = bufferedReader.readLine()) != null) {
                //判断是不是message，message类似BO_ 856 CDU_1: 8 CDU格式
                Pattern pattern = Pattern.compile(messagePa);
                Matcher m1 = pattern.matcher(line);
                message = decoding.messageDecoding(line);
                if (m1.matches() && message.getId() == id) {
                    return message;
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();// 关闭输入流
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    @Override
    public Set<CanSignal> searchSignalUseMessage(CanMessage message)  {
        CanSignal signal = null;
        CanMessage messageStart = null;
        int start = 0;//表示开始
        Set<CanSignal> signalList = new HashSet<CanSignal>();//用于存放所有查找到的signal信息
        BufferedReader bufferedReader = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(filename), "GBK");
            bufferedReader = new BufferedReader(isr);
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
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            try {
                bufferedReader.close();// 关闭输入流
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return signalList;
    }


}
