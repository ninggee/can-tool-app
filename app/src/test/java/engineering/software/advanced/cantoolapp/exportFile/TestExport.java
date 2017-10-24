package engineering.software.advanced.cantoolapp.exportFile;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engineering.software.advanced.cantoolapp.exportFIle.Export;
import engineering.software.advanced.cantoolapp.exportFIle.Impl.ExportImp;

/**
 * Created by lhr on 2017/10/25.
 */

public class TestExport {
    @Test
    public void export(){
        List<Map> canList = new ArrayList<>();
        Export export = new ExportImp();

        Map<String,String> m = new HashMap();
        m.put("1","2");
        m.put("2","2");
        m.put("3","2");
        canList.add(m);
        m = new HashMap<>();
        m.put("4","21");
        m.put("5","21");
        m.put("6","21");
        canList.add(m);
        export.export("d:/","test",".csv",canList);
    }
}
