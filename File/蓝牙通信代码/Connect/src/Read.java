import gnu.io.SerialPort;
import serialPort.SerialTool;


public class Read {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        //以波特率115200打开串口COM12
        SerialPort serialPort = SerialTool.openPort("COM3", 115200);
        SerialTool.sendToPort(serialPort, "\007".getBytes());
        SerialTool.addListener(serialPort, new SerialListener(serialPort));
        while(true){
        	SerialTool.sendToPort(serialPort, "t03D19C\r".getBytes());
//        	SerialTool.sendToPort(serialPort, "t3458C4BD4525445265BD\r".getBytes());
        	Thread.sleep(1000L);
        }
    }

}