package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommuterClient implements Runnable {
	public static int portNumber = 0;
	public static String serverIP = "";
	public int commuterNumber;
	Socket socket;

	public CommuterClient(int commuterCount, String serverIP, int portNumber) {
		this.commuterNumber = commuterCount;
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
			for (int i = 0; i <= 4; i++) {
				out.println("C" + i);
				print("Client: " + "Commuter " + commuterNumber + " has sent a request for method " + i);
				out.flush();
				
				while ((reply = bReader.readLine()) == null) {
				}
				print(reply);
	
			}
			print("Client: " + "Commuter " + commuterNumber + " has ended");
			// out.close();

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
