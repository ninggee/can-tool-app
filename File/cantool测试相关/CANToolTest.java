package test;

import static org.junit.Assert.*;
import gnu.io.SerialPort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import serialPort.SerialTool;
import static org.mockito.Mockito.*;  
import CANTool.CANTool;

public class CANToolTest {
	private CANTool tool,spy;
	private SerialPort serialPort;
	private SerialTool serialTool;
	@Before
	public void setUp() throws Exception {
		serialPort=mock(SerialPort.class);
		tool = new CANTool(serialPort);
		spy = spy(tool);
		
	}

	@After
	public void tearDown() throws Exception {
	}
	//测试返回版本信息
	@Test
	public void readCommandTest1() {
		
		doNothing().when(spy).returnTheInfo(1,"SV2.5-HV2.0");
		spy.readCommand("V\r");
		verify(spy,times(1)).returnTheInfo(1,"SV2.5-HV2.0");
		
	}
	
	//测试正常开机
	@Test
	public void readCommandTest2() {
		
		doNothing().when(spy).returnTheInfo(1,"");
		spy.readCommand("O1\r");
		verify(spy,times(1)).open();
		verify(spy,times(1)).returnTheInfo(1,"");
		
	}
	
	//测试非正常开机
	@Test
	public void readCommandTest3() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1\r");
		spy.readCommand("O1\r");
		verify(spy,times(2)).open();
		verify(spy,times(1)).returnTheInfo(1,"");
		verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试正常关机
	@Test
	public void readCommandTest4() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1r");
		spy.readCommand("C\r");
		verify(spy,times(1)).open();
		verify(spy,times(1)).close();
		verify(spy,times(2)).returnTheInfo(1,"");
		
	}
	
	//测试非正常关机
	@Test
	public void readCommandTest5() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("C\r");
		verify(spy,times(1)).close();
		verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试关机状态下调节速度
	@Test
	public void readCommandTest6() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("S1\r");
		verify(spy,times(1)).changeSpeed('1');
		verify(spy,times(1)).returnTheInfo(1,"");
		
	}
	
	//测试关机状态下调节速度
	@Test
	public void readCommandTest7() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1r");
		spy.readCommand("S1\r");
		verify(spy,times(1)).changeSpeed('1');
		verify(spy,times(1)).returnTheInfo(1,"");
		verify(spy,times(1)).returnTheInfo(0,"");
		
	}

	//测试连续调节速度
	@Test
	public void readCommandTest8() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("S1\r");
		spy.readCommand("S2\r");
		spy.readCommand("S3\r");
		verify(spy,times(1)).changeSpeed('1');
		verify(spy,times(1)).changeSpeed('2');
		verify(spy,times(1)).changeSpeed('3');
		verify(spy,times(3)).returnTheInfo(1,"");
		
	}
	
	//测试发送1次标准帧
	@Test
	public void readCommandTest9() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1\r");
		spy.readCommand("t36380000000300000D500000\r");
		verify(spy,times(1)).open();
		try {
			verify(spy,times(1)).sendStandardFrame("t36380000000300000D500000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(spy,times(2)).returnTheInfo(1,"");
		//verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试发送多次标准帧
	@Test
	public void readCommandTest10() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1\r");
		spy.readCommand("t36380000000300000D500010\r");
		verify(spy,times(1)).open();
		try {
			verify(spy,times(1)).sendStandardFrame("t36380000000300000D500010");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(spy,times(2)).returnTheInfo(1,"");
		//verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试发送标准帧错误
	@Test
	public void readCommandTest11() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1\r");
		spy.readCommand("t3F380000000300000D500010\r");
		verify(spy,times(1)).open();
		try {
			verify(spy,times(1)).sendStandardFrame("t3F380000000300000D500010");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(spy,times(1)).returnTheInfo(1,"");
		verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试未开机发送标准帧
	@Test
	public void readCommandTest12() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		//spy.readCommand("O1\r");
		spy.readCommand("t359800301513034014880010\r");
		//verify(spy,times(1)).returnTheInfo(1,"");
		try {
			verify(spy,times(1)).sendStandardFrame("t359800301513034014880010");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试发送1次扩展帧
	@Test
	public void readCommandTest13() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1\r");
		spy.readCommand("T0000036380000000300000D500000\r");
		verify(spy,times(1)).open();
		try {
			verify(spy,times(1)).sendExtendedFrame("T0000036380000000300000D500000");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(spy,times(2)).returnTheInfo(1,"");
		//verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试发送多次扩展帧
	@Test
	public void readCommandTest14() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1\r");
		spy.readCommand("T0000036380000000300000D500010\r");
		verify(spy,times(1)).open();
		try {
			verify(spy,times(1)).sendExtendedFrame("T0000036380000000300000D500010");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(spy,times(2)).returnTheInfo(1,"");
		//verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试发送扩展帧错误
	@Test
	public void readCommandTest15() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		spy.readCommand("O1\r");
		spy.readCommand("T000003F380000000300000D500010\r");
		verify(spy,times(1)).open();
		try {
			verify(spy,times(1)).sendExtendedFrame("T000003F380000000300000D500010");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(spy,times(1)).returnTheInfo(1,"");
		verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	//测试未开机发送扩展帧错误
	@Test
	public void readCommandTest16() {
		
		doNothing().when(spy).returnTheInfo(anyInt(),anyString());
		//spy.readCommand("O1\r");
		spy.readCommand("T00000359800301513034014880010\r");
		try {
			verify(spy,times(1)).sendExtendedFrame("T00000359800301513034014880010");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//verify(spy,times(1)).returnTheInfo(1,"");
		verify(spy,times(1)).returnTheInfo(0,"");
		
	}
	
	

}
