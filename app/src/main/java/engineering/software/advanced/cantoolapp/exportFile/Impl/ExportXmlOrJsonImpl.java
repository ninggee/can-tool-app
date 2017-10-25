package engineering.software.advanced.cantoolapp.exportFIle.Impl;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engineering.software.advanced.cantoolapp.exportFIle.ExportXmlOrJson;
import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by lhr on 2017/10/25.
 */

public class ExportXmlOrJsonImpl implements ExportXmlOrJson{
    @Override
    public void exportJson(Set<Map<String,MessagesWrapper>> canSet, String filename, String path) {
        Gson gson = new Gson();
        String jsonResult = gson.toJson(canSet);//装换成json文件
        File jsonFile = new File(path + filename + ".json");
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(jsonFile));
            bw  = new BufferedWriter(osw);
            bw.write(jsonResult);
            bw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bw.close();
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void exportXml(Set<Map<String, MessagesWrapper>> canSet, String filename, String path) {

    }
}
