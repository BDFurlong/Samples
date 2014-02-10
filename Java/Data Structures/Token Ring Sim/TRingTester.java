package TokenRing;

public class TRingTester {

	
	public static void main(String[] args) {
		
		TRSim mysim = new TRSim(10,10);
		
		
		mysim.runSimulation();
		mysim.runSimulationFDDI();
		

	}

}
