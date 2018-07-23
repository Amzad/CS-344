package p2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AttendantClient implements Runnable {
	public static final int portNumber = 3000;
	public static final String serverIP = "127.0.0.1";
	public int attendantNumber;

	public AttendantClient(int attendantCount) {
		print("Attendant Running");
		this.attendantNumber = attendantCount;

	}

	public void run() {
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
