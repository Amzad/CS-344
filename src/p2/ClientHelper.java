package p2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
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

	public ClientHelper(Socket socket, InputStreamReader inputStreamReader, PrintWriter outputStreamWriter) {
		this.socket = socket;
		// this.inputStreamReader = inputStreamReader;
		// this.outputStreamWriter = outputStreamWriter;

	}

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
					thread = "Attendant";
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
						break;

					case 1:
						c.joinLane();
						break;

					case 2:
						c.payToll();
						break;

					case 3:
						c.parkAtGarage();
						break;

					case 4:
						c.waitForTrain();
						break;
					}
					outputStreamWriter.println("Done");
					outputStreamWriter.flush();

				} else if (thread.equals("Attendant")) {
					new Thread(new garageAttendant(attendantCount.incrementAndGet())).start();
					outputStreamWriter.println("Confirmed");
					outputStreamWriter.flush();

				} else if (thread.equals("Train")) {
					
					if (name == 1) {
						subwayTrain t = new subwayTrain("A");
						
						switch (methodNumber) {
						case 0:
							t.resetTrain();
							break;

						case 1:
							t.inTransit();
							break;

						case 2:
							t.atStop();
							break;

						case 3:
							t.inTransit();
							break;

						case 4:
							t.lastStop();
							break;
						}
						outputStreamWriter.println("Done");
						outputStreamWriter.flush();
						
						
						
					} else if (name == 2) {
						subwayTrain t = new subwayTrain("B");
						
						switch (methodNumber) {
						case 0:
							t.resetTrain();
							break;

						case 1:
							t.inTransit();
							break;

						case 2:
							t.atStop();
							break;

						case 3:
							t.inTransit();
							break;

						case 4:
							t.lastStop();
							break;
						}
						outputStreamWriter.println("Done");
						outputStreamWriter.flush();

					} else if (name == -1) {
						print("Server: Maximum number of Trains allowed. Dropping new connection.");
						outputStreamWriter.write("Maximum number of Trains allowed. Dropping new connection.");
						outputStreamWriter.close();
						break;
					}
				}

			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void print(String input) {
		System.out.println(input);
	}

}
