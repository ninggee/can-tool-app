package engineering.software.advanced.cantoolapp.converter.database.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import engineering.software.advanced.cantoolapp.converter.database.CanDecoding;
import engineering.software.advanced.cantoolapp.converter.database.Database;
import engineering.software.advanced.cantoolapp.converter.database.Impl.Tree.SqlNode;
import engineering.software.advanced.cantoolapp.converter.database.Impl.Tree.SqlTree;
import engineering.software.advanced.cantoolapp.converter.database.ReadSql;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

/**
 * Created by lhr on 2017/10/23.
 */

public class ReadSqlImp implements ReadSql {

    String messagePa = "^BO_.*$";//需要匹配的message信息
    String signalPa = "^ SG_.*$";//需要匹配的signal信息
    CanDecoding decoding = CanDecodingImpl.getInstance();//解析用
    //创建一个树
    SqlTree tree = new SqlTree();
    //创建节点
    SqlNode node = new SqlNode();

    @Override
    public SqlTree readAstree(String filename) {

        return tree;
    }


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
