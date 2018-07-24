package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AttendantClient implements Runnable {
	public static int portNumber = 0;
	public static String serverIP = "";
	public int attendantNumber;

	public AttendantClient(int attendantCount, String serverIP, int portNumber) {
		print("Attendant Running");
		this.attendantNumber = attendantCount;
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
				out.println("A");
				out.flush();
				
				while ((reply = bReader.readLine()) == null) {
				}
				if (reply.equals("Confirmed")) break;
				
			}
			out.close();
			socket.close();

		} catch (Exception e) {
			print(e.toString());
		}

	}

	public void print(String input) {
		System.out.println(input);
	}

}
