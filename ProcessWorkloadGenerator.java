/* Operating Systems AE
 * Critical Evaluation of Scheduling Algorithms
 * 
 * Gabriela Georgieva
 * matric #: 2130120g
 * 
 */

import java.util.Random;

/* custom class to generate process workload */
public class ProcessWorkloadGenerator {
	
	final static int N = 1000; // number of processes
	public static int[] PID = new int[N]; // process IDs
	public static double[] CBT = new double[N]; // CPU Burst times
	public static int[] AAT = new int[N]; //Absolute Arrival times
	
	public static void main(String[] args) {
		
		/* generate W1 */
		int mean = 20; // mean value used in Gaussian distribution
		String name = "W1.csv";
		generateW(mean, name);
		
		/* generate W2
		int mean = 60; // mean value used in Gaussian distribution
		String name = "W2.csv";
		generateW(mean, name);
		*/
		
	}
	
	/* method to generate a workload of processes */
	public static void generateW(int M, String name) {
		final int CBT_MIN = 5; // min CBT value
		Random r = new Random(System.currentTimeMillis());
		PID[0] = 0;
		CBT[0] = Math.ceil(Math.max(CBT_MIN, Gaussian(r, M)));
		AAT[0] = 0;
		System.out.println(PID[0] + ", " + CBT[0] + ", " + AAT[0]);
		
		for (int k = 1; k <= N-1; k++) {
			Random random = new Random(System.currentTimeMillis());
			PID[k] = k;
			CBT[k] = Math.ceil(Math.max(CBT_MIN, Gaussian(random, M)));
			AAT[k] = AAT[k-1] + Poisson(random);
			System.out.println(PID[k] + ", " + CBT[k] + ", " + AAT[k]);
		}
		
		// write process data to a csv file
		CSVManipulator.writeCSVFile(name, N, PID, CBT, AAT);
	}
	
	/* Gaussian distribution to be used for generating process CBTs */
	private static double Gaussian(Random random, int M) {
		final int STD = 3; // standard deviation
		return random.nextGaussian() * STD + M;
	}
	
	/* Poisson distribution to be used for generating process AATs */
	private static int Poisson(Random random) {
		final int L = 5; // mean value
		int k = 0;
		double p = 1.0;
		double expLambda = Math.exp(-L);
		do {
			k++;
			p *= random.nextDouble();
		} while (p >= expLambda);
		return k - 1;
	}
}
