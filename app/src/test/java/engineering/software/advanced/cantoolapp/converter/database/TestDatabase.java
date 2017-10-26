package engineering.software.advanced.cantoolapp.converter.database;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.converter.database.Impl.DatabaseImpl;

/**
 * Created by lhr on 2017/10/11.
 */

public class TestDatabase {
    @Test
    public void testDatabase() throws Exception {
        Database db = new DatabaseImpl();
//        System.out.println(db.searchMessageUseId((long)856, isr));
//        System.out.println("----------------------");
//        Set<CanSignal> list = db.searchSignalUseMessage(db.searchMessageUseId((long)856, isr),isr);
//        System.out.println(list.size());
//        for(CanSignal cs : list)
//            System.out.println(cs);
    }
}
