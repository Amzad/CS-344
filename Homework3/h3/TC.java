package h3;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class TC extends Thread {

	PipedInputStream pisCfromA;
	PipedInputStream pisCfromB;
	PipedOutputStream posCtoA;
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	public TC(PipedInputStream pisCfromA, PipedInputStream pisCfromB, PipedOutputStream posCtoA, ObjectInputStream ois,
			ObjectOutputStream oos) {
		this.pisCfromA = pisCfromA;
		this.pisCfromB = pisCfromB;
		this.posCtoA = posCtoA;
		
		this.ois = ois;
		this.oos = oos;
		
	}
	
	public void run() {
		System.out.println("TC: Starting execution.");
		
		// TA sends primitive data to TC. 
		try {
			System.out.println("TC: Receiving Primitive Data from TA.");
			System.out.println("TC: " + pisCfromA.read());
			pisCfromA.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// TB will send primitive data to TC.
		try {
			System.out.println("TC: Receiving Primitive Data from TB.");
			System.out.println("TC: " + pisCfromB.read());
			pisCfromA.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// TC will send objects data to TA. 
		System.out.println("TC: Sending Object Data to TA.");
		try {
			Message message = new Message();
			message.theMessage = "Hello World!";
			String[] array = {"My", "Name", "Is", "Amzad"};
			message.someLines = array;
			message.someNumber = 344;
			oos = new ObjectOutputStream(posCtoA);
			System.out.println("TC: Sending Message Object to TA with data: " + message.toString());
			oos.writeObject(message);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
