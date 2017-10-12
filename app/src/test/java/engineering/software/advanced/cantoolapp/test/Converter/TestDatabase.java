package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import engineering.software.advanced.cantoolapp.Converter.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.database.Impl.DataBaseImpl;

/**
 * Created by lhr on 2017/10/11.
 */

public class TestDatabase {
    @Test
    public void testDatabase(){
<<<<<<< HEAD
        DataBaseImpl db = new DataBaseImpl();
        System.out.println(db.searchMessageUseId((long)1056));
        System.out.println("----------------------");
        Set<CanSignal> list = db.searchSignalUseMessage(db.searchMessageUseId((long)1056));
=======
        DatabaseImpl db = new DatabaseImpl();
        System.out.println(db.searchMessageUseId((long)856));
        System.out.println("----------------------");
        List<CanSignal> list = db.searchSignalUseMessage(db.searchMessageUseId((long)856));
>>>>>>> d38393274c15b92b883b4b542764e92695cc354f
        System.out.println(list.size());
        for(CanSignal cs : list)
            System.out.println(cs);
    }
}
