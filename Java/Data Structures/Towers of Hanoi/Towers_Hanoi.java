
public class Lab5 {
	
	public static void main(String args[]){
		
		System.out.println(gcd(2,3));
		towersHanoi(5,"a,","b","c");
		
		
	}
	
	private static int gcd(int a,int b){
		if(b == 0){
			return a;
		}
		else{
			return gcd(b, a %b);
		}
		}
	
	private static void towersHanoi(int n, String startPeg, String finishPeg, String usingPeg){
		if(n == 0){
			System.out.println("Nothing to solve");
		}
		if(n==1){
			System.out.println(startPeg+" => "+finishPeg);
		}
		if(n>1){
			towersHanoi(n-1, startPeg, usingPeg, finishPeg);
			System.out.println(startPeg+" => "+finishPeg);
			towersHanoi(n-1, usingPeg, finishPeg, startPeg);
			}
	}
	
	
	
}
