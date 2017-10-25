package engineering.software.advanced.cantoolapp.export.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.entity.Signal;
import engineering.software.advanced.cantoolapp.export.Export;
import engineering.software.advanced.cantoolapp.webinterfaces.MessagesWrapper;

/**
 * Created by lhr on 2017/10/25.
 */

public class ExportImpl implements Export{
    @Override
    public void export(String path, String name, String format, List<MessagesWrapper> canList) {


        if(format.equals(".csv"))
            exportCsv(path, name, format,canList);
        else
            exportFile(path, name, format,canList);

    }

    private void exportFile(String path, String name, String format, List<MessagesWrapper> canList) {
        File otherFile = new File(path + name + format);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(otherFile);
            osw = new OutputStreamWriter(fos, "GBK");
            bw = new BufferedWriter(osw);
            //写入
            for(int i = 0;i < canList.size(); i++){
                MessagesWrapper thismessage = canList.get(i);

                bw.write(thismessage.getTime());


                bw.write(thismessage.getId());


                bw.write(thismessage.getChn());


                bw.write(thismessage.getName());


                bw.write(thismessage.getDir());


                bw.write(thismessage.getDlc());


                bw.write(thismessage.getData());


                //写入signal数据
                Set<Signal> signals = thismessage.getSignals();

                for(Signal s : signals){
                    bw.write('\n');//换行

                    bw.write("signal");


                    bw.write(s.getName());


                    bw.write(s.getValue()+"");


                    bw.write(s.getOrigin());

                }

                if(i < (canList.size() -1))//没到最后一行之前都要换行
                    bw.write('\n');

            }
            bw.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                osw.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private void exportCsv(String path, String name, String format, List<MessagesWrapper> canList) {
        File csvFile = new File(path + name + format);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile),"GBK"));

            //写入
            for(int i = 0;i < canList.size(); i++){
                MessagesWrapper thismessage = canList.get(i);
                bw.write(thismessage.getTime());
                bw.write(",");

                bw.write(thismessage.getId());
                bw.write(",");

                bw.write(thismessage.getChn());
                bw.write(",");

                bw.write(thismessage.getName());
                bw.write(",");

                bw.write(thismessage.getDir());
                bw.write(",");

                bw.write(thismessage.getDlc());
                bw.write(",");

                bw.write(thismessage.getData());
                bw.write(",");

                //写入signal数据
                Set<Signal> signals = thismessage.getSignals();

                for(Signal s : signals){
                    bw.write('\n');//换行

                    bw.write("signal");
                    bw.write(",");

                    bw.write(s.getName());
                    bw.write(",");

                    bw.write(s.getValue()+"");
                    bw.write(",");

                    bw.write(s.getOrigin());
                    bw.write(",");
                }
                if(i < (canList.size() -1))//没到最后一行之前都要换行
                    bw.write('\n');

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
