package TokenRing;

public class Packet {
	
	private int origin;
	private int destination;
	private int data;
	private boolean received;
	
	public Packet(int i, int d, int dat){
		origin = i;
		destination = d;
		data = dat;
		received = false;
	}
	
	public int getOrigin(){
		return origin;
	}
	
	public int getDesination(){
		return destination;
	}
	
	public int getData(){
		return data;
	}
	
	public boolean received(){
		return received;
		}
	
	public void sendreceipt(boolean receipt){
		received = receipt;
	}
}
