package p2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client implements Runnable {
	public static final int portNumber = 3000;
	public static final String serverIP = "127.0.0.1";
	public static Socket socket;
	public static OutputStreamWriter out;
	public static BufferedReader bReader;
	public int commuterCount;
	public int attendantCount;
	public int trainCount;

	public Client(int commuterCount, int attendantCount, int trainCount) {
		print("Client running");
		this.commuterCount = commuterCount;
		this.attendantCount = attendantCount;
		this.trainCount = trainCount;
	}

	public void run() {
		openSocket();

	}

	public void openSocket() {
		try {
			socket = new Socket(serverIP, portNumber);
			out = new OutputStreamWriter(socket.getOutputStream());
			bReader = new BufferedReader(new InputStreamReader(System.in));

			for (int i = 0; i < commuterCount; i++) {
				out.write("C" + i);
				Thread.sleep(1000);
			}
			print("Done");
			
			
			for (int i = 0; i < attendantCount; i++) {
				out.write("A" + i);
			}
			Thread.sleep(500);
			
			for (int i = 0; i < trainCount; i++) {
				out.write("T" + i);
			}
			Thread.sleep(500);
			
			out.close();

		} catch (Exception e) {
			print(e.toString());
		}
	}

	public void print(String input) {
		System.out.println(input);
	}

}
