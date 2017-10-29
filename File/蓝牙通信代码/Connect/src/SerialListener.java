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

            case SerialPortEvent.BI: // 10 ͨѶ�ж�
                System.out.println("�봮���豸ͨѶ�ж�");
                break;

            case SerialPortEvent.OE: // 7 ��λ�����������

            case SerialPortEvent.FE: // 9 ֡����

            case SerialPortEvent.PE: // 8 ��żУ�����

            case SerialPortEvent.CD: // 6 �ز����

            case SerialPortEvent.CTS: // 3 �������������

            case SerialPortEvent.DSR: // 4 ����������׼������

            case SerialPortEvent.RI: // 5 ����ָʾ

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 ��������������
                break;
            
            case SerialPortEvent.DATA_AVAILABLE: // 1 ���ڴ��ڿ�������
                
                //System.out.println("found data");
                byte[] data = null;
                
                try {
                    if (serialPort == null) {
                        System.out.println("���ڶ���Ϊ�գ�����ʧ��");
                    }
                    else {
                    	
                        data = SerialTool.readFromPort(serialPort);    //��ȡ���ݣ������ֽ�����
                        
                        
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
                        
//                        JOptionPane.showInputDialog(new String(data));//����
                        
                        //String dataOriginal = new String(data);    //���ֽ���������ת��λΪ������ԭʼ���ݵ��ַ���
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