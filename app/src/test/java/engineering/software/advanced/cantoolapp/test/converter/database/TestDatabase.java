package engineering.software.advanced.cantoolapp.test.converter.database;

import org.junit.Test;

import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;
import engineering.software.advanced.cantoolapp.converter.database.DataBase;
import engineering.software.advanced.cantoolapp.converter.database.Impl.DataBaseImpl;

/**
 * Created by lhr on 2017/10/11.
 */

public class TestDatabase {
    @Test
    public void testDatabase() throws Exception {
        DataBase db = DataBaseImpl.getInstance();
        System.out.println(db.searchMessageUseId((long)856));
        System.out.println("----------------------");
        Set<CanSignal> list = db.searchSignalUseMessage(db.searchMessageUseId((long)856));
        System.out.println(list.size());
        for(CanSignal cs : list)
            System.out.println(cs);
    }
}
