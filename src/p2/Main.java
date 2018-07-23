package p2;

public class Main {
	
	public static void main(String[] args) {
		
		int commuterCount = 0;
		int attendantCount = 0;
		int trainCount = 0;
		
		if (args.length != 0) {
			commuterCount = Integer.parseInt(args[0]);
			attendantCount = Integer.parseInt(args[1]);
			trainCount = Integer.parseInt(args[2]);		
		} else {
			commuterCount = 5;
			attendantCount = 2;
			trainCount = 2;
		}
		
		// Start Server Thread
		Server server = new Server();
		Thread t = new Thread(server);
		
		// Set a delay to make sure server is up before client send information.
		t.start();
		
		for (int i = 1; i <= attendantCount; i++) {
			Thread b = new Thread(new AttendantClient(i));
			b.start();
		}
		
		for (int i = 1; i <= trainCount; i++) {
			Thread c = new Thread(new TrainClient(trainCount));
			c.start();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Start Client Thread
		for (int i = 1; i <= commuterCount; i++) {
			CommuterClient cclient = new CommuterClient(i);
			Thread a = new Thread(cclient);
			a.start();
		}

		
		

	}

}
