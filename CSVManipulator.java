/* Operating Systems AE
 * Critical Evaluation of Scheduling Algorithms
 * 
 * Gabriela Georgieva
 * matric #: 2130120g
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* custom class to do csv file writing and reading */
public class CSVManipulator {
	
	/* write process workload to csv file */
	public static void writeCSVFile(String fileName, int N, int[] PID, double[] CBT, int[] AAT) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileName);
			for (int i = 0; i < N; i++) {
				fileWriter.append(String.valueOf(PID[i]));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(CBT[i]));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(AAT[i]));
				fileWriter.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* read process workload and populate process data */
	public static void readCSVFile(String file, int N, int[] PID, double[] CBT, int[] AAT) {
		BufferedReader br = null;
        String processData = "";
        try {
        	br = new BufferedReader(new FileReader(file));
        	for (int k = 0; k < N; k++) {
        		processData = br.readLine();
        		String[] process = processData.split(",");
        		PID[k] = Integer.parseInt(process[0]);
        		CBT[k] = Double.parseDouble(process[1]);
        		AAT[k] = Integer.parseInt(process[2]);
        	}
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
        	if (br != null) {
        		try {
        			br.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }
    }
}
