package engineering.software.advanced.cantoolapp.converter.database;

import com.google.gson.Gson;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.database.Impl.ReadSqlImp;
import engineering.software.advanced.cantoolapp.converter.database.Impl.Tree.SqlNode;
import engineering.software.advanced.cantoolapp.converter.database.Impl.Tree.SqlTree;

/**
 * Created by lhr on 2017/10/24.
 */

public class TestReadTree {
    @Test
    public void read(){
        ReadSqlImp readSqlImp = new ReadSqlImp();
        readSqlImp.readAstree();
        SqlTree sqlTree = readSqlImp.getSqlTree();
        SqlNode sqlNode = sqlTree.root;

        for(SqlNode sn: sqlNode.sons){
            System.out.println(sn.name);
            for (SqlNode sns : sn.sons)
                System.out.println(" " + sns.name);
        }

    }
}
