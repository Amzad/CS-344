package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TrainClient implements Runnable {
	public static int portNumber = 0;
	public static String serverIP = "";
	public String trainNumber;
	Socket socket;

	public TrainClient(int trainCount, String serverIP, int portNumber) {
		//print(Integer.toString(trainCount));
		if (trainCount == 1) {
			trainNumber = "A";
		} else {
			trainNumber = "B";
		}
		
		this.serverIP = serverIP;
		this.portNumber = portNumber;
	}

	public void run() {
		if ((serverIP.length() == 0) && (portNumber == 0)) {
			print("Client: " + "Invalid server ip and port.");
			return;
		}
		try {
			socket = new Socket(serverIP, portNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String reply;
			while (true) {
				for (int i = 0; i <= 4; i++) {
					out.println("T" + i);
					print("Client: " + "Train " + trainNumber + " has sent a request for method " + i);
					out.flush();

					while ((reply = bReader.readLine()) == null) {
					}
					print(reply);

				}

			}

		} catch (Exception e) {
			print(e.toString());
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void print(String input) {
		System.out.println(input);
	}

}
