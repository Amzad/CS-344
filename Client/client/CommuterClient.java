package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommuterClient implements Runnable {
	public static int portNumber = 0;
	public static String serverIP = "";
	public BufferedReader bReader;
	public int commuterNumber;

	public CommuterClient(int commuterCount, String serverIP, int portNumber) {
		this.commuterNumber = commuterCount;
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
			bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String reply;
			for (int i = 0; i <= 4; i++) {
				out.println("C" + i);
				out.flush();
				
				while ((reply = bReader.readLine()) == null) {
				}
	
			}
			print("Thread Ended");
			// out.close();

		} catch (Exception e) {
			print(e.toString());
		}

	}

	public void print(String input) {
		System.out.println(input);
	}

}
