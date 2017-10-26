package engineering.software.advanced.cantoolapp.converter.database.Impl;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.Set;

import engineering.software.advanced.cantoolapp.converter.database.DataBase;
import engineering.software.advanced.cantoolapp.converter.entity.CanMessage;
import engineering.software.advanced.cantoolapp.converter.entity.CanSignal;

import static org.junit.Assert.*;

/**
 * Created by lhr on 2017/10/26.
 */
public class DataBaseImplTest {
    @Test
    public void searchAllMessage() throws Exception {

        DataBase db = new DataBaseImpl("C:\\Users\\lhr\\Desktop\\canmsg-sample.dbc");
        Set<CanMessage> set = db.searchAllMessage();
        Gson json = new Gson();
        for(CanMessage cm : set) {
           System.out.println(json.toJson(cm));
            Set<CanSignal> canSignals= db.searchSignalUseMessage(cm);
            for(CanSignal cs : canSignals)
                System.out.println(json.toJson(cs));
        }

    }

}