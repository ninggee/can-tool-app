package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import java.util.List;

import engineering.software.advanced.cantoolapp.Converter.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.database.Impl.DatabaseImpl;

/**
 * Created by lhr on 2017/10/11.
 */

public class TestDatabase {
    @Test
    public void testDatabase(){
        DatabaseImpl db = new DatabaseImpl();
        System.out.println(db.searchMessageUseId((long)856));
        System.out.println("----------------------");
        List<CanSignal> list = db.searchSignalUseMessage(db.searchMessageUseId((long)856));
        System.out.println(list.size());
        for(CanSignal cs : list)
            System.out.println(cs);
    }
}
