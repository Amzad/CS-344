package h3;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;

public class TB extends Thread {
	
	PipedOutputStream posBtoA;
	PipedOutputStream posBtoC;
	
	ObjectOutputStream oos;

	public TB(PipedOutputStream posBtoA, PipedOutputStream posBtoC, ObjectOutputStream oos) {
		this.posBtoA = posBtoA;
		this.posBtoC = posBtoC;
		
		this.oos = oos;
	}
	
	public void run() {
		
		// TB will send objects to TA 
		System.out.println("TB: Sending Object Data to TA.");
		try {
			Message message = new Message();
			message.theMessage = "Hello World!";
			String[] array = {"My", "Name", "Is", "Amzad"};
			message.someLines = array;
			message.someNumber = 344;
			oos = new ObjectOutputStream(posBtoA);
			System.out.println("TB: Sending Message Object to TA with data: " + message.toString());
			oos.writeObject(message);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// TB will send primitive data to TC
		System.out.println("TB: Sending Primative Data to TC.");
		try {
			posBtoC.write(1);
			System.out.println("TB: Sending 1 to TC.");
			posBtoC.flush();
			posBtoC.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	

}
