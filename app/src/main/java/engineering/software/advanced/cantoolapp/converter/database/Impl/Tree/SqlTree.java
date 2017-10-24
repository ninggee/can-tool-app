package engineering.software.advanced.cantoolapp.converter.database.Impl.Tree;

import java.util.ArrayList;

/**
 * Created by lhr on 2017/10/23.
 */

public class SqlTree {
    public SqlNode root;

    public SqlTree() {
        super();
        root = new SqlNode();
        root.name = "Start";
//        root.father = null;
        root.sons = new ArrayList();
    }
}
