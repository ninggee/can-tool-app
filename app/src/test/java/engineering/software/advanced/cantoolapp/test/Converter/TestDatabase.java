package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import java.util.List;

import engineering.software.advanced.cantoolapp.Converter.Entity.CanSignal;
import engineering.software.advanced.cantoolapp.Converter.database.DataBase;
import engineering.software.advanced.cantoolapp.Converter.database.Impl.DatabseImpl;

/**
 * Created by lhr on 2017/10/11.
 */

public class TestDatabase {
    @Test
    public void testDatabase(){
        DatabseImpl db = new DatabseImpl();
        System.out.println(db.searchMessageUseId((long)1056));
        System.out.println("----------------------");
        List<CanSignal> list = db.searchSignalUseMessage(db.searchMessageUseId((long)1056));
        System.out.println(list.size());
        for(CanSignal cs : list)
            System.out.println(cs);
    }
}
