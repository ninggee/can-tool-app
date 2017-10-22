package engineering.software.advanced.cantoolapp.communicator;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import engineering.software.advanced.cantoolapp.communicator.handler.Handler;

/**
 * Created by ningge on 2017/10/12.
 */

public class ReaderThread extends Thread implements Reader {

    private InputStream in = null;
    private Handler handler = null;
    private static String TAG = "ReaderThread";

    public ReaderThread(InputStream in, Handler handler){
        this.in = in;
        this.handler = handler;
    }

    @Override
    public void read() {

        byte[] buffer = new byte[1024];

        int bytes; // bytes read from inputstream
        try {

            bytes = in.read(buffer);
            handler.handle(new String(buffer, 0, bytes));

        } catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "read from input stream failed");
        }

    }

    @Override
    public void run() {
        super.run();
        //keep listening to the input stream until thread is interrupted
        while(!interrupted()) {
            read();
        }
     }
}
