package engineering.software.advanced.cantoolapp.test.Converter;

import org.junit.Test;

import engineering.software.advanced.cantoolapp.Converter.database.DataBase;
import engineering.software.advanced.cantoolapp.Converter.database.Impl.DatabseImpl;

/**
 * Created by lhr on 2017/10/11.
 */

public class TestDatabase {
    @Test
    public void testDatabase(){
        DatabseImpl db = new DatabseImpl();
        System.out.println(db.searchMessageUseId((long)61));
    }
}
