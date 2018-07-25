package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AttendantClient implements Runnable {
	public static int portNumber = 0;
	public static String serverIP = "";
	public int attendantNumber;
	Socket socket;

	public AttendantClient(int attendantCount, String serverIP, int portNumber) {
		this.attendantNumber = attendantCount;
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
				for (int i = 0; i <= 1; i++) {
					out.println("A" + i);
					print("Client: " + "Attendant " + attendantNumber + " has sent a request for method " + i);
					out.flush();

					while ((reply = bReader.readLine()) == null) {
					}
					print(reply);

				}

			}
			//out.close();
			//socket.close();

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
