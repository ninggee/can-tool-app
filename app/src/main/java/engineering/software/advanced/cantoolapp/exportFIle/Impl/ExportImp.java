package engineering.software.advanced.cantoolapp.exportFIle.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import engineering.software.advanced.cantoolapp.exportFIle.Export;

/**
 * Created by lhr on 2017/10/24.
 */

public class ExportImp implements Export {

    @Override
    public void export(String path, String name, String format, List<Map> canList) {

        if(format.equals(".csv"))
            exportCsv(path, name, format,canList);

    }

    private void exportCsv(String path, String name, String format, List<Map> canList) {
        File csvFile = new File(path + name + format);
        File parent = csvFile.getParentFile();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile),"GBK"));

            //写入
            for(int i = 0;i < canList.size(); i++){
                Map<String,String> m = canList.get(i);
                System.out.println(m);
                //用keyset()遍历
                for (String key : m.keySet()) {
                    bw.write("\"" + m.get(key) +"\"");
                    bw.write(",");
                }
                if(i < (canList.size() -1))//没到最后一行之前都要换行
                {bw.write('\n');System.out.print("行");
                }
            }
            bw.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
