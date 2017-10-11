package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engineering.software.advanced.cantoolapp.Converter.Entity.CanMessage;
import engineering.software.advanced.cantoolapp.Converter.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.database.CanDecoding;
import engineering.software.advanced.cantoolapp.Converter.database.Impl.CanDecodingImpl;

/**
 * Created by lhr on 2017/10/11.
 */

public class Testlhr {

    CanDecoding decoding = new CanDecodingImpl();
    @Test
    public void database(){
        CanMessage message = null;
        File filename = new File("C:\\Users\\lhr\\Desktop\\a.txt");
        CanSignal signal = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            //读取一行
            while ((line = bufferedReader.readLine()) != null) {

                //判断是不是message，message类似BO_ 856 CDU_1: 8 CDU格式
                Pattern pattern = Pattern.compile("^BO_.*");
                Matcher m1 = pattern.matcher(line);
                message = decoding.messageDecoding(line);
                if(m1.matches() && message.getId() == id){
                    return message;
            }
            bufferedReader.close();// 关闭输入流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return signal;    }
}
