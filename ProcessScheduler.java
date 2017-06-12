/* Operating Systems AE
 * Critical Evaluation of Scheduling Algorithms
 * 
 * Gabriela Georgieva
 * matric #: 2130120g
 * 
 */

/* custom class to simulate process scheduling */
public class ProcessScheduler {
	
	final static int N = 1000; // number of processes
	public static int[] PID = new int[N]; // process IDs
	public static double[] CBT = new double[N]; // CPU Burst Times
	public static int[] AAT = new int[N]; // Absolute Arrival Times
	public static double[] WT = new double[N]; // Waiting Time
	public static double[] TT = new double[N]; // Turn-around Time
	public static double AWT = 0; // Average Waiting Time
	public static double ATT = 0; // Average Turn-around Time
	
	public static void main(String[] args) {
		
		// give path to file as a command line arg
		String csv = args[0];
		
		/* populate process data */
		CSVManipulator.readCSVFile(csv, N, PID, CBT, AAT);
		
		/* choose scheduling algorithm */
		FirstComeFirstServed();
		// ShortJobFirst();
		// RoundRobin(5);
		
		/* print generated statistics */
		for (int k = 0; k <= N-1; k++) {
			System.out.println(PID[k] + ", " + WT[k] + ", " + TT[k]);
		}
		
		System.out.println("Average Waiting Time is:  " + AWT);
		System.out.println("Average Turnaround Time is:  " + ATT);
	}
	
	/* scheduling algorithm 1: FCFS */
	public static void FirstComeFirstServed() {
		
		double totalExecTime = 0;
		
		for (int k = 0; k < N; k++) {
			
			// WT = time when execution starts - time of arrival
			WT[k] = totalExecTime - AAT[k];
			
			totalExecTime += CBT[k];
			
			// TT = time when execution finished - time of arrival
			TT[k] = totalExecTime - AAT[k];
			
			AWT += WT[k];
			ATT += TT[k];
		}
		
		AWT = AWT/N;
		ATT = ATT/N;
	}
	
	/* scheduling algorithm 2: SJF */
	public static void ShortJobFirst() {
		
		double totalExecTime = 0;
		int tempInt;
		double tempDouble;
		
        /* order processes by CBTs, considering arrival time */
        for (int i = 0; i < N; i++) {
        	
        	// for each process check all processes on its right in the array
        	for (int j = i; j < N; j++) {
        		// if the process has arrived and its CBT is smaller
        		if (totalExecTime >= AAT[j] && CBT[j] < CBT[i]) {
        			
        			// swap positions of processes
        			tempInt = PID[i];
        			PID[i] = PID[j];
        			PID[j] = tempInt;
        			
        			tempDouble = CBT[i];
        			CBT[i] = CBT[j];
        			CBT[j] = tempDouble;
        			
        			tempInt = AAT[i];
        			AAT[i] = AAT[j];
        			AAT[j] = tempInt;
        		}
        	}
        	
        	WT[i] = totalExecTime - AAT[i];
        	totalExecTime += CBT[i];
        	TT[i] = totalExecTime - AAT[i];
        	AWT += WT[i];
        	ATT += TT[i];
        }
        
		AWT = AWT/N;
		ATT = ATT/N;
	}
	
	/* scheduling algorithm 3: RR */
	public static void RoundRobin(double quantum) {
		
		int current = 0;
		int remaining = N;
		double totalExecTime = 0;
		
		/* initialise remaining times to CBTs */
		double[] remainingTimes = new double[N];
		for (int j = 0; j < N; j++) {
			remainingTimes[j] = CBT[j];
		}
		
		// until all processes have finished execution
		while (remaining != 0) {
			// if the process have not finished yet
			if (remainingTimes[current] > 0) {
				
				// give quantum time and continue
				if (remainingTimes[current] > quantum) {
					remainingTimes[current] -= quantum;
					totalExecTime += quantum;
					
			    // or finish process execution
				} else if (remainingTimes[current] <= quantum) {
					
					totalExecTime += remainingTimes[current];
					WT[current] = totalExecTime - AAT[current] - CBT[current];
					TT[current] = totalExecTime - AAT[current];
					
					AWT += WT[current];
					ATT += TT[current];
					
					remainingTimes[current] = 0;
					remaining--;
			    }
			}
			// if end of array, go back to beginning
			current = (current + 1) % N;
		}
		
		ATT = ATT/N;
		AWT = AWT/N;
	}
}