package h3;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Main {
	
	// TA sends primitive data to TC. 
	static private PipedOutputStream posAtoC; 
	static private PipedInputStream pisCfromA; 

	// TB will send objects to TA
	static private PipedOutputStream posBtoA; 
	static private PipedInputStream pisAfromB;
	
	// and primitive data to TC.
	static private PipedOutputStream posBtoC; 
	static private PipedInputStream pisCfromB;
	
	// TC will send objects data to TA
	static private PipedOutputStream posCtoA;
	static private PipedInputStream pisAfromC;
	

	static private ObjectOutputStream oos;
	static private ObjectInputStream ois;

	public static void main(String args[]) {
		try {
			// set up a pipe
			System.out.println("Pipe setup");
			
			posAtoC = new PipedOutputStream();
			pisCfromA = new PipedInputStream(posAtoC);

			posBtoA = new PipedOutputStream();
			pisAfromB = new PipedInputStream(posBtoA);
			
			posBtoC = new PipedOutputStream();
			pisCfromB = new PipedInputStream(posBtoC);
			
			posCtoA = new PipedOutputStream();
			pisAfromC = new PipedInputStream(posCtoA);
			
			oos = null;
			ois = null;

			System.out.println("Object creation");
			
			// TA sends to TC, then receives from TB, then receives from TC
			TA ta = new TA(posAtoC, pisAfromB, pisAfromC, ois, oos);
			
			// TB only sends, to TA and TC.	
			TB tb = new TB(posBtoA, posBtoC, oos);
			
			// TC receives from TA, from TB, send to TA.
			TC tc = new TC(pisCfromA, pisCfromB, posCtoA, ois, oos );


			System.out.println("Thread execution");
			ta.start();
			tb.start();
			tc.start();
		} // end TRY
		catch (Exception exc) {
			System.out.println(exc);
		} // end CATCH
	}

}
