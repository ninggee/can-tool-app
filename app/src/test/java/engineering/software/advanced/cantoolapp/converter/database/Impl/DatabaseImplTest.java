package engineering.software.advanced.cantoolapp.converter.database.Impl;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.database.Database;

/**
 * Created by lhr on 2017/10/26.
 */
public class DatabaseImplTest {
    @Test
    public void searchAllMessage() throws Exception {

        Database db = new DatabaseImpl("C:\\Users\\lhr\\Desktop\\canmsg-sample.dbc");
        String s = db.searchAllMessage();
        System.out.print(s);

    }

    @Test
    public void dbcTreeTojson() throws Exception {

        Database db = new DatabaseImpl("C:\\Users\\lhr\\Desktop\\canmsg-sample.dbc");
        String s = db.dbcTreeTojson();
        System.out.print(s);

    }
}