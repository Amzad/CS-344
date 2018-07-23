package p2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommuterClient implements Runnable {
	public static final int portNumber = 3000;
	public static final String serverIP = "127.0.0.1";
	public BufferedReader bReader;
	public int commuterNumber;

	public CommuterClient(int commuterCount) {
		this.commuterNumber = commuterCount;

	}

	public void run() {
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
