import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthScrollBarUI;

import gnu.io.SerialPort;
import serialException.SendDataToSerialPortFailure;
import serialException.SerialPortOutputStreamCloseFailure;
import serialPort.SerialTool;


public class Write {

    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        String temp;
        //以波特率115200打开串口COM11
        SerialPort serialPort = SerialTool.openPort("COM4", 115200);
        while(scan.hasNext())
        {
            temp=scan.nextLine();
            SerialTool.sendToPort(serialPort, temp.getBytes());
//            sentTo(serialPort, temp.getBytes());//自己写的
        }

    }

	public static void sentTo(SerialPort serialPort, byte[] order) throws SerialPortOutputStreamCloseFailure, SendDataToSerialPortFailure {
		OutputStream out = null;
		try {
			out = serialPort.getOutputStream();
			System.out.println(out.toString());
			out.write(order);
			System.out.println(order);
			out.flush();
			System.out.println(out);
		} catch (IOException e) {
			throw new SendDataToSerialPortFailure();
		}finally {
			try {
				if(out != null){
					out.close();
					out = null;
				}
			} catch (Exception e2) {
				throw new SerialPortOutputStreamCloseFailure();
			}
		}
		
	}

}