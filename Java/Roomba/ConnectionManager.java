import java.util.*;
import gnu.io.*;
import java.io.*;



public class ConnectionManager {
	
	SerialPort activePort;
	CommPortIdentifier cpi;
	
	InputStream is;
	OutputStream output;
	
	ArrayList<CommPortIdentifier> ports;
	boolean connected;
	
	
	public ConnectionManager(){
		
		ports = getAvailableSerialPorts();
		connected = false;
	}

	public ConnectionManager(String portName){
	}
	
	
 	public ArrayList<CommPortIdentifier> getAvailableSerialPorts(){
		
		ArrayList<CommPortIdentifier> availPorts = 
				new ArrayList<CommPortIdentifier>();
		
		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		
		while(ports.hasMoreElements()){
			CommPortIdentifier port = (CommPortIdentifier)ports.nextElement();
			if(port.getPortType() == CommPortIdentifier.PORT_SERIAL){
				availPorts.add(port);
				}
			}
		return availPorts;
	}
		
	//public boolean connect(String port){
		
	//}
	
	public boolean autoConnect(){
		
		int count = 0; //find a better way to do this.
		
		if(ports.isEmpty()==true){ //throw an exception
			return false;}
		
		else{
			count = ports.size()-1;
			
			while(connected == false && count > -1){
				
				if(ports.get(count).isCurrentlyOwned()==false){
				
					if(connected = testPort(ports.get(count))==true){
						return connected;
					}
					else{
						count--;
						activePort.close();
						}
				}
				else{
					count--;
				}
			}
		}
		
		return connected;
	}
		
	
	
	
			
	private boolean testPort(CommPortIdentifier cpi){
		try {
			activePort = (SerialPort)cpi.open("test1",1000);//this needs to be changed
			System.out.println(activePort.getName());//this will be removed
			
			try {									//handle exceptions properly
				is = activePort.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				output = activePort.getOutputStream();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			} catch (PortInUseException e) {
		
			e.printStackTrace();
		}
		testRoutine();
		return false;
	
}
	
	public boolean send(int b){
		try {
			output.write(b & 0xff);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
		
	public void testRoutine(){
		send(128); //initialize
		send(129); //setbaudrate
		send(10);  //baud rate
		send(142); //poll sensor
		send(34);  //which sensor
	}
	}

	
