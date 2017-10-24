package engineering.software.advanced.cantoolapp.converter.database.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
            isr = new InputStreamReader(new FileInputStream(fileName),"GBK");
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
            SqlNode sonsNode = new SqlNode();
            sonsNode.name = s;
            sonsNode.sons = new ArrayList<>();
            node.sons.add(sonsNode);//添加子节点
            node =  sonsNode; //node转换成刚刚添加的message。
            return;
        }



        //是signal信息
        Pattern patternSignal = Pattern.compile(signalPa);
        Matcher mSignal = patternSignal.matcher(s);
        if(mSignal.matches()){

            SqlNode sonsNode = new SqlNode();
            sonsNode.name = s;
            sonsNode.sons = new ArrayList<>();
            node.sons.add(sonsNode);
            return;
        }

        //以上都没有匹配，说明是空行，一条CAN信息已经结束
        node = null;

    }

    //返回树
    public SqlTree getSqlTree(){
        return this.tree;
    }
}
