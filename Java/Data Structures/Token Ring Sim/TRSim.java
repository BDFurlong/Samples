package TokenRing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class TRSim {
	
	private LinkedList<WorkStation> ring = new LinkedList<WorkStation>();
	private StringBuilder results = new StringBuilder();
	private Random rand;
	private ArrayList<Packet> wpacks = new ArrayList<Packet>();
	
	public TRSim(int n, int p){
		rand = new Random();
		
		for(int i = 0; i < n; i++){
			ring.add(i,new WorkStation(i));
		}
		
		for(int i = 1; i < p; i++){
			wpacks.add(new Packet(rand.nextInt(n),rand.nextInt(n),rand.nextInt(2000)));
		}
	}
	
	public void runSimulation(){
		System.out.println("Token Ring Sim\n=========================");
		int time = 0;
		Packet currentpack;
		WorkStation currentStation = ring.getFirst();
		ListIterator<WorkStation> token = ring.listIterator();
		int packets = wpacks.size();
		while(packets > 0){
			if(ring.size()== token.nextIndex()){
				
				token = ring.listIterator();
			}
			
			currentpack = wpacks.get(packets-1);
			if(currentStation.getAddress()==currentpack.getOrigin()|| currentStation.getAddress()==currentpack.getDesination()){
				
				if(currentpack.received()){
					time++;
					packets--;
					System.out.println("Node =>"+currentStation.getAddress()+"Has received delivery confirmation\n\n");
					currentpack.sendreceipt(false);
				}
				
				else if(currentStation.getAddress()==currentpack.getDesination()){
					time++;
					 System.out.println("Node =>"+currentStation.getAddress()+
							"Has received =>"+currentpack.getData()+
							"And has sent an acknoldegement back\n");
					currentpack.sendreceipt(true);
				}
				else if(currentStation.getAddress()==currentpack.getOrigin()){
					time++;
					System.out.println("Node =>"+currentStation.getAddress()+
							"Has sent a packet to =>"+currentpack.getDesination()+
							"And is awaiting a receipt\n");
				}
			}
				else{
					time++;
					System.out.println(currentStation.getAddress()+"Has passed the token\n");
					
				}
			currentStation = (WorkStation)token.next();
			}
		System.out.println("Time to complete: "+time);
		time = 0;
		
		
		}
	
	public void runSimulationFDDI(){
		System.out.println("FDDI SIM\n++++++++++++++++++++++++++++++++");
		int time = 0;
		Packet currentpack;
		WorkStation currentStation = ring.getFirst();
		ListIterator<WorkStation> token = ring.listIterator();
		int packets = wpacks.size();
		while(packets > 0){
			if(ring.size()== token.nextIndex()){
				token = ring.listIterator();
			}
			
			currentpack = wpacks.get(packets-1);
			if(currentStation.getAddress()==currentpack.getOrigin()|| currentStation.getAddress()==currentpack.getDesination()){
				
				if(currentpack.received()){
					time++;
					packets--;
					System.out.println("Node =>"+currentStation.getAddress()+"Has received delivery confirmation\n\n");
				}
				
				else if(currentStation.getAddress()==currentpack.getDesination()){
					time++;
					 System.out.println("Node =>"+currentStation.getAddress()+
							"Has received =>"+currentpack.getData()+
							"And has sent an acknoldegement back\n");
					currentpack.sendreceipt(true);
				}
				else if(currentStation.getAddress()==currentpack.getOrigin()){
					time++;
					System.out.println("Node =>"+currentStation.getAddress()+
							"Has sent a packet to =>"+currentpack.getDesination()+
							"And is awaiting a receipt\n");
				}
			}
				else{
					time++;
					System.out.println(currentStation.getAddress()+"Has passed the token\n");
					
				}
			if((token.nextIndex()-1) - currentpack.getDesination()>=0){
			currentStation = (WorkStation)token.previous();
			}else{
			
			currentStation = (WorkStation)token.next();}
		}
		System.out.println("Time to complete: "+time);
		time = 0;
		
		
		}
	}

	

	


