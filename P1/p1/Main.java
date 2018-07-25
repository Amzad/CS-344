package p1;


public class Main {
	
	public static void main(String[] args) {
		int numCommuter;
		if (args.length != 0)
		{
			numCommuter = Integer.parseInt(args[0]); // Number of commuters
			
		}
		else {
			numCommuter = 15; // Number of commuters
		}
		int trainCapacity = 10; // Max passenger capacity for trains.
		int numAttendant = 2; // Number of Attendants
		
		// Start attendant objects
		for (int i = 1; i <= numAttendant; i++) {
			garageAttendant c = new garageAttendant(i, numCommuter);
			Thread t = new Thread(c);
			t.start();
		}
		
		// Start commuter objects
		for (int i = 1; i <= numCommuter; i++) {
			Commuter c = new Commuter(i);
			Thread t = new Thread(c);
			t.start();
		}
		
				// Start train objects.
		subwayTrain m = new subwayTrain("A", trainCapacity, numCommuter);
		Thread o = new Thread(m);
		o.start();
		
		sleep(10); // Artificial delay between trains. Not needed but makes it more realistic
		subwayTrain n = new subwayTrain("B", trainCapacity, numCommuter);
		Thread p = new Thread(n);
		p.start();
		
	}
	
	private static void sleep(int x) {
		try {
			Thread.sleep(x * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
