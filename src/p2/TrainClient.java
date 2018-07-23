package p2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TrainClient implements Runnable {
	public static final int portNumber = 3000;
	public static final String serverIP = "127.0.0.1";
	public int trainNumber;

	public TrainClient(int trainCount) {
		print("Client running");
		this.trainNumber = trainCount;
	}

	public void run() {
		openSocket();

	}

	public void openSocket() {
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
