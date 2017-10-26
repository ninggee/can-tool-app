package engineering.software.advanced.cantoolapp.webinterfaces;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import engineering.software.advanced.cantoolapp.communicator.ReaderThread;
import engineering.software.advanced.cantoolapp.communicator.handler.Handler;
import engineering.software.advanced.cantoolapp.connector.Connector;
import engineering.software.advanced.cantoolapp.converter.MessageAndSignalProcessor;
import engineering.software.advanced.cantoolapp.converter.Processor;
import engineering.software.advanced.cantoolapp.converter.transmission.Impl.ReceiverImpl;
import engineering.software.advanced.cantoolapp.converter.transmission.Impl.SenderImpl;
import engineering.software.advanced.cantoolapp.converter.transmission.Receiver;
import engineering.software.advanced.cantoolapp.converter.transmission.Sender;

/**
 * Created by ningge on 2017/10/25.
 */

public class CommandController {
    Connector connector;
    private String result ;
    private boolean state;
    private ReaderThread reader;
    private String command_type;

    public CommandController(Connector connector) {
        this.connector = connector;
    }

    public void sendCommand(String type, String value) {
        Sender sender = SenderImpl.getInstance();
        command_type = type;
        switch (type) {
            case "version":
                writeToDevice(sender.requestVersion());
                break;
            case "speed":
                writeToDevice(sender.setSpeed(Integer.parseInt(value)));
                break;
            case "open":
                if(Boolean.parseBoolean(value)) {
                    writeToDevice(sender.open());
                } else {
                    writeToDevice(sender.close());
                }
                break;
        }
    }

    private void writeToDevice(String message) {
        Log.d("send command", message);
        OutputStream outputStream = this.connector.getOutputStream();

        //start a new thread to wait result
        waitResult();

        //write message to device
        byte[] bytes = message.getBytes();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            Log.e("write","write to bluetooth failed");
        }


    }

    private void writeToDeviceWithoutWait(String message) {
        String newMessage = "";
        newMessage = message.substring(0, 1) +  message.substring(1).toUpperCase() + "\r";
        Log.d("send command", newMessage);
        OutputStream outputStream = this.connector.getOutputStream();

        this.command_type = "";
        //start a new thread to wait result
        waitResult();

        //write message to device
        byte[] bytes = newMessage.getBytes();
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            Log.e("write","write to bluetooth failed");
        }


    }

    public void sendMessage(String id, Map<String, Double> values, int period) {
        Processor processor = MessageAndSignalProcessor.getInstance();
        writeToDeviceWithoutWait(processor.encode(Long.parseLong(id), values, period));
    }

    public void waitResult() {
        final InputStream in = connector.getInputStream();
        final Receiver receiver = ReceiverImpl.getInstance();

        //start read command thread
        reader = new ReaderThread(in, new Handler() {
            @Override
            public void handle(String message) {
                if(message == null) {
                    return;
                }
                Log.d("command", message);
                switch (command_type) {
                    case "version":
                        result = receiver.parseVersion(message);
                        break;
                    default:
                        result = receiver.parseYN(message) + "";
                }

                state = true;

                Log.d("debug", result);
                Thread.currentThread().interrupt();

            }

        });

        reader.start();
    }

    public String getResult() {
        if(command_type == "" || result == "" || state == false) {
            return "";
        } else {

            // stop get result thread
//            reader.interrupt();
            String result  = this.result;

            //reset flag variable
            synchronized (this.result) {
                this.state = false;
                this.result = "";
                command_type = "";
            }
            return result;
        }
    }
}
