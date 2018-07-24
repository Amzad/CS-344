package h3;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class TA extends Thread{

	PipedOutputStream posAtoC;
	PipedInputStream pisAfromB;
	PipedInputStream pisAfromC;
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public TA(PipedOutputStream posAtoC, PipedInputStream pisAfromB, PipedInputStream pisAfromC, ObjectInputStream ois,
			ObjectOutputStream oos) {
		this.posAtoC = posAtoC;
		this.pisAfromB = pisAfromB;
		this.pisAfromC = pisAfromC;
		
		this.ois = ois;
		this.oos = oos;
	}
	
	public void run() {
		System.out.println("TA: Starting execution.");
		
			// TA sends primitive data to TC. 
			System.out.println("TA: Sending Primative Data to TC.");
			try {
				posAtoC.write(0);
				System.out.println("TA: Sending 0 to TC.");
				posAtoC.flush();
				posAtoC.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// TB will send objects to TA.
			try {
				System.out.println("TA: Receiving Object Data from TB.");
				ois = new ObjectInputStream(pisAfromB);
				System.out.println("TA: " + ois.readObject().toString());
				ois.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			// TC will send objects data to TA
			try {
				System.out.println("TA: Receiving Object Data from TC.");
				ois = new ObjectInputStream(pisAfromC);
				System.out.println("TA: " + ois.readObject().toString());
				ois.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

	}

}
