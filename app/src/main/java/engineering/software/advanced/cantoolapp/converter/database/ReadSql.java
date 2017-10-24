package engineering.software.advanced.cantoolapp.converter.database;

import engineering.software.advanced.cantoolapp.converter.database.Impl.Tree.SqlTree;

/**
 * Created by lhr on 2017/10/23.
 */

public interface ReadSql {
    public abstract SqlTree readAstree();
    public abstract void buildTree(String s);
}
