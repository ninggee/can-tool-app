package engineering.software.advanced.cantoolapp.webinterfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by ningge on 20/10/2017.
 */

public class TestInterface {

    Context __context;

    public TestInterface(Context c) {
        __context = c;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(__context, toast, Toast.LENGTH_LONG).show();
    }
}
