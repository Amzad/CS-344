package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TrainClient implements Runnable {
	public static int portNumber = 0;
	public static String serverIP = "";
	public int trainNumber;

	public TrainClient(int trainCount, String serverIP, int portNumber) {
		print("Client running");
		this.trainNumber = trainCount;
		this.serverIP = serverIP;
		this.portNumber = portNumber;
	}

	public void run() {
		if ((serverIP.length() == 0) && (portNumber == 0)) {
			print("Invalid server ip and port.");
			return;
		}
		try {
			Socket socket = new Socket(serverIP, portNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String reply;
			while (true) {
				for (int i = 0; i <= 4; i++) {
					out.println("T" + i);
					out.flush();

					while ((reply = bReader.readLine()) == null) {
					}

				}

			}

		} catch (Exception e) {
			print(e.toString());
		}
	}

	public void print(String input) {
		System.out.println(input);
	}

}
