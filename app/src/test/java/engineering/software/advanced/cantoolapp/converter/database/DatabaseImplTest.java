package engineering.software.advanced.cantoolapp.converter.database;

import org.junit.Test;

import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.Impl.DatabaseImpl;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

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

    @Test
    public void searchSignalUseMessage() throws Exception {
        Database db = new DatabaseImpl("F:/Comfort.dbc");
        CanMessage canMessage = new CanMessage();
        canMessage.setId(272);
        Set<CanSignal> set = db.searchSignalUseMessage(canMessage);
        System.out.println(set.size());
        for (CanSignal canSignal : set) {
            System.out.println(canSignal);
        }
    }
}