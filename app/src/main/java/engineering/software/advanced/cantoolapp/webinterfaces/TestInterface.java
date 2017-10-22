package engineering.software.advanced.cantoolapp.webinterfaces;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONObject;

import engineering.software.advanced.cantoolapp.connector.Connector;

/**
 * Created by ningge on 20/10/2017.
 */

public class TestInterface {

    Context __context;
    Connector __connector;

    public TestInterface(Context c, Connector connector) {
        __context = c;
        __connector =connector;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(__context, toast, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public String getDevices() {
        JSONObject result = new JSONObject(__connector.listAll());
        Log.d("interface", result.toString());
        return  result.toString();
    }

    @JavascriptInterface
    public boolean connect(String path) {
        return  __connector.connect(path, 115200);
    }

    @JavascriptInterface
    public boolean close() {
        return __connector.close();
    }
}
