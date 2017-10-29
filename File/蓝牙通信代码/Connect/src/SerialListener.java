import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serialPort.SerialTool;


public class SerialListener implements SerialPortEventListener {
    public SerialPort serialPort;
    public SerialListener(SerialPort serialPort)
    {
        this.serialPort=serialPort;
    }
    
    public void serialEvent(SerialPortEvent serialPortEvent) {
        
        switch (serialPortEvent.getEventType()) {

            case SerialPortEvent.BI: // 10 通讯中断
                System.out.println("与串口设备通讯中断");
                break;

            case SerialPortEvent.OE: // 7 溢位（溢出）错误

            case SerialPortEvent.FE: // 9 帧错误

            case SerialPortEvent.PE: // 8 奇偶校验错误

            case SerialPortEvent.CD: // 6 载波检测

            case SerialPortEvent.CTS: // 3 清除待发送数据

            case SerialPortEvent.DSR: // 4 待发送数据准备好了

            case SerialPortEvent.RI: // 5 振铃指示

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                break;
            
            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                
                //System.out.println("found data");
                byte[] data = null;
                
                try {
                    if (serialPort == null) {
                        System.out.println("串口对象为空！监听失败");
                    }
                    else {
                    	
                        data = SerialTool.readFromPort(serialPort);    //读取数据，存入字节数组
                        
                        
                        String result = new String(data);
                        System.out.println(result);
                        String[] dataSplit = result.split("\\r");
                        for(String s :dataSplit)
                        	System.out.println("split:" +s);
                        for(String s : dataSplit){
                        	if(s.equals("V"))
                        		SerialTool.sendToPort(serialPort, "SV2.5-HV2.0\r".getBytes());
                        	
                        	else 
                        		SerialTool.sendToPort(serialPort, MyRandom().getBytes());
                        }
                       
//                        SerialTool.sendToPort(serialPort, "\007".getBytes());
                        
//                        JOptionPane.showInputDialog(new String(data));//弹窗
                        
                        //String dataOriginal = new String(data);    //将字节数组数据转换位为保存了原始数据的字符串
                    }                        
                    
                } catch (Exception e) {
                    System.exit(0);
                }    
                
                break;

        }

    }

	private String MyRandom() {
		double random = Math.random();
		if(random >= 0.5)
			return "\r";
		else
			return "\007";
	}

}