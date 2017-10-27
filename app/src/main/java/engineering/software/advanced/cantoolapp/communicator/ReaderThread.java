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
    private String message_buffer = "";

    public ReaderThread(InputStream in, Handler handler){
        this.in = in;
        this.handler = handler;
    }

    @Override
    public void read() throws IOException {

        byte[] buffer = new byte[1024];

        int bytes; // bytes read from inputstream


        bytes = in.read(buffer);



        message_buffer = message_buffer + new String(buffer, 0, bytes);
        Log.i("debug", message_buffer + "\r");
        if(is_valid(message_buffer)) {

            handler.handle(getMessage());
        }


    }

    @Override
    public void run() {
        super.run();
        //keep listening to the input stream until thread is interrupted
        while(!interrupted()) {
            try{
               read();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "read from inputstram failed");
                break;
            }
        }
    }

    private boolean is_valid(String message){
        if(message.indexOf("\r") > 0 || message.indexOf("\007") > 0) {
            return true;
        } else {
            return false;
        }
    }

    private String getMessage() {
        String result = "";


        int lastB = message_buffer.lastIndexOf("\007");
        int lastR = message_buffer.lastIndexOf("\r");

        if(lastB > lastR) {
            result = message_buffer.substring(0, lastB);
            this.message_buffer = this.message_buffer.substring(lastB + 1);
        } else {
            result = message_buffer.substring(0, lastR);
            this.message_buffer = this.message_buffer.substring(lastR + 1);
        }

        return  result;

    }
}
