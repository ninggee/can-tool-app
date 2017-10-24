package engineering.software.advanced.cantoolapp.converter.database.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engineering.software.advanced.cantoolapp.converter.database.Impl.Tree.SqlNode;
import engineering.software.advanced.cantoolapp.converter.database.Impl.Tree.SqlTree;
import engineering.software.advanced.cantoolapp.converter.database.ReadSql;

/**
 * Created by lhr on 2017/10/23.
 */

public class ReadSqlImp implements ReadSql {

    String messagePa = "^BO_.*$";//需要匹配的message信息
    String signalPa = "^ SG_.*$";//需要匹配的signal信息
    //创建一个树
    SqlTree tree = new SqlTree();
    //创建节点
    SqlNode node = new SqlNode();

    @Override
    public SqlTree readAstree() {
        File fileName = new File("C:\\Users\\lhr\\Desktop\\canmsg-sample.dbc");//路径应该修改
        InputStreamReader isr = null;
        BufferedReader br = null;
        String s = "";
        try {
            isr = new InputStreamReader(new FileInputStream(fileName));
            br = new BufferedReader(isr);
            while((s = br.readLine()) != null) {
                buildTree(s);//构建一个树
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                br.close();
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return tree;
    }

    @Override
    public void buildTree(String s) {

        //是message信息
        Pattern patternMessage = Pattern.compile(messagePa);
        Matcher mMessage = patternMessage.matcher(s);
        if(mMessage.matches()){
            node = tree.root;//获得根节点
            node.sons.add(s);
            return;
        }

        node = (SqlNode) node.sons.get(node.sons.size() - 1); //获得刚刚添加 的message。

        //是signal信息
        Pattern patternSignal = Pattern.compile(messagePa);
        Matcher mSignal = patternSignal.matcher(s);
        if(mSignal.matches()){
            node.sons.add(s);
            return;
        }

        //以上都没有匹配，说明是空行，一条CAN信息已经结束
        node = null;

    }
}
