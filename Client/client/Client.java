package client;

public class Client {
	
	public static void main(String[] args) {
		
		int commuterCount = 0;
		int attendantCount = 0;
		int trainCount = 0;
		String serverIP = "127.0.0.1";
		int portNumber = 3000;
		
		if (args.length != 0) {
			serverIP = args[0];
			portNumber = Integer.parseInt(args[1]);
			commuterCount = Integer.parseInt(args[2]);
			attendantCount = Integer.parseInt(args[3]);
			trainCount = Integer.parseInt(args[4]);		
		} else {
			commuterCount = 15;
			attendantCount = 2;
			trainCount = 2;
		}
		
		//serverIP = "hawk.cs.qc.edu";

		// Start Client Thread
		System.out.println("Runnning on server " + serverIP + ":" + portNumber);
		for (int i = 1; i <= attendantCount; i++) {
			Thread b = new Thread(new AttendantClient(i, serverIP, portNumber));
			b.start();
		}
		
		for (int i = 1; i <= trainCount; i++) {
			Thread c = new Thread(new TrainClient(trainCount, serverIP, portNumber));
			c.start();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 1; i <= commuterCount; i++) {
			CommuterClient cclient = new CommuterClient(i, serverIP, portNumber);
			Thread a = new Thread(cclient);
			a.start();
		}

		
		

	}

}

