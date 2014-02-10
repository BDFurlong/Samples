package TokenRing;

public class WorkStation {
	
	private int address;
	public boolean waiting;
	
	public WorkStation(int addr){
		address = addr;
		waiting = false;
	}
	
	public void setWaiting(boolean wait){
		waiting = wait;
	}
	
	public boolean waiting(){
		return waiting;
	}
	
	public int getAddress(){
		return address;
	}
	
	

}
