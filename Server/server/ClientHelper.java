package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHelper extends Thread {
	public static AtomicInteger commuterCount = new AtomicInteger(0);
	public static AtomicInteger attendantCount = new AtomicInteger(0);
	public static AtomicInteger trainCount = new AtomicInteger(0);
	final Socket socket;
	public int methodNumber;
	public String thread;
	public String input;
	public int name = -1;

	public ClientHelper(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			String input;
			int methodNumber = 0;
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			PrintWriter outputStreamWriter = new PrintWriter(socket.getOutputStream());
			BufferedReader inputStream = new BufferedReader(inputStreamReader);

			while ((input = inputStream.readLine()) != null) {

				// Three options for clients.
				// C - Commuter
				// A - Attendant
				// T - Train
				if (input.charAt(0) == 'C') {
					if (name == -1) {
						name = commuterCount.incrementAndGet();
					}
					thread = "Commuter";
					methodNumber = Character.getNumericValue(input.charAt(1));
				}
				
				if (input.charAt(0) == 'A') {
					if (name == -1) {
						name = attendantCount.incrementAndGet();	
					}
					thread = "Attendant";
					methodNumber = Character.getNumericValue(input.charAt(1));
				}
				
				if (input.charAt(0) == 'T') {
					
					 if ((name == -1) && (trainCount.get() < 2)) {
						 name = trainCount.incrementAndGet();
					 }
					thread = "Train";
					methodNumber = Character.getNumericValue(input.charAt(1));
				}

				if (thread.equals("Commuter")) {
					Commuter c = new Commuter(name);
					switch (methodNumber) {

					case 0:
						c.commuteToNYC();
						outputStreamWriter.println("Server: commuteToNYC method completed for Commuter " + c.name);
						outputStreamWriter.flush();
						break;

					case 1:
						c.joinLane();
						outputStreamWriter.println("Server: joinLane method completed for Commuter " + c.name);
						outputStreamWriter.flush();
						break;

					case 2:
						c.payToll();
						outputStreamWriter.println("Server: payToll method completed for Commuter " + c.name);
						outputStreamWriter.flush();
						break;

					case 3:
						c.parkAtGarage();
						outputStreamWriter.println("Server: parkAtGarage method completed for Commuter " + c.name);
						outputStreamWriter.flush();
						break;

					case 4:
						c.waitForTrain();
						outputStreamWriter.println("Server: waitForTrain method completed for Commuter " + c.name);
						outputStreamWriter.flush();
						break;
					}
					//outputStreamWriter.println("Done");
					//outputStreamWriter.flush();

				} else if (thread.equals("Attendant")) {
					garageAttendant a =  new garageAttendant(name);
					//new Thread(new garageAttendant(attendantCount.incrementAndGet())).start();
					
					switch (methodNumber) {

					case 0:
						a.signalCommuter();
						outputStreamWriter.println("Server: signalCommuter method completed for Attendant " + a.name);
						outputStreamWriter.flush();
						break;

					case 1:
						a.waitForCommuter();
						outputStreamWriter.println("Server: waitForCommuter method completed for Attendant " + a.name);
						outputStreamWriter.flush();
						break;
					}

				} else if (thread.equals("Train")) {
					
					if (name == 1) {
						subwayTrain t = new subwayTrain("A");
						
						switch (methodNumber) {
						case 0:
							t.resetTrain();
							outputStreamWriter.println("Server: resetTrain method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 1:
							t.inTransit();
							outputStreamWriter.println("Server: inTransit method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 2:
							t.atStop();
							outputStreamWriter.println("Server: atStop method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 3:
							t.inTransit();
							outputStreamWriter.println("Server: inTransit method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 4:
							t.lastStop();
							outputStreamWriter.println("Server: lastStop method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;
						}
						//outputStreamWriter.println("Done");
						//outputStreamWriter.flush();
						
						
						
					} else if (name == 2) {
						subwayTrain t = new subwayTrain("B");
						
						switch (methodNumber) {
						case 0:
							t.resetTrain();
							outputStreamWriter.println("Server: resetTrain method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 1:
							t.inTransit();
							outputStreamWriter.println("Server: inTransit method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 2:
							t.atStop();
							outputStreamWriter.println("Server: atStop method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 3:
							t.inTransit();
							outputStreamWriter.println("Server: inTransit method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;

						case 4:
							t.lastStop();
							outputStreamWriter.println("Server: lastStop method completed for Train " + t.name);
							outputStreamWriter.flush();
							break;
						}

					} else if (name == -1) {
						print("Server: Maximum number of Trains allowed. Dropping new connection.");
						outputStreamWriter.write("Maximum number of Trains allowed. Dropping new connection.");
						outputStreamWriter.close();
						socket.close();
						break;
						
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			print("Connection abbruptedly closed");
			if (thread.equals("Train")) {
				trainCount.decrementAndGet();
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} 
	}

	public void print(String input) {
		System.out.println(input);
	}

}
