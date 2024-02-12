import java.util.Random;

public class hw2 {

	public static void main(String[] args) {
		System.out.println("Average over 50 tests:");
		System.out.println("             C         C/n");
		collectorTester(200, 50);
		collectorTester(400, 50);
		collectorTester(800, 50);
		collectorTester(1600, 50);
		collectorTester(2500, 50);
		collectorTester(4000, 50);
		
		System.out.println("Average over 50 tests:");
		System.out.println("             V         V/n");
		collectorVarTester(200, 50);
		collectorVarTester(400, 50);
		collectorVarTester(800, 50);
		collectorVarTester(1600, 50);
		collectorVarTester(2500, 50);
		collectorVarTester(4000, 50);		
	}
	
	public static void collectorVarTester(int n, int k) {
		int total = 0;
		for (int i = 0; i < k; i++) {
			int test = collectorVar(n);
			total += test;
		}
		double avg = (double) total / (double) k;
		System.out.println("[n = "+n+"] "+avg+"    "+avg / (double) n);
	}
	
	public static int collectorVar(int n) {
		int count = 0;
		int[] tracker = new int[n];
		while (count < n) {
			Random rand = new Random();
			int coupon = rand.nextInt(n);
			int value = rand.nextInt(n) + 1;
			if (tracker[coupon] == 0) {
				tracker[coupon] = value;
				count += 1;
			} else if (value < tracker[coupon]) {
				tracker[coupon] = value;
			}
		}
		int sum = 0;
		for (int i : tracker) {
			sum += i;
		} 
		return sum; 
	}
	

	public static void collectorTester(int n, int k) {
		int total = 0;
		for (int i = 0; i < k; i++) {
			int test = collector(n);
			total += test;
		}
		double avg = (double) total / (double) k;
		System.out.println("[n = "+n+"] "+avg+"    "+avg / (double) n);
	}
	
	public static int collector(int n) {
		int count = 0;
		int iter = 0;
		int[] tracker = new int[n];
		while (count < n) {
			Random rand = new Random();
			int coupon = rand.nextInt(n);
			if (tracker[coupon] != 1) {
				tracker[coupon] = 1;
				count += 1;
			}
			iter += 1;
		}
		return iter;
	}
	
}
