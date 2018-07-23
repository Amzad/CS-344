package p2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	public static final String serverIP = "127.0.0.1";
	public static final int portNumber = 3000;
	public static Socket socket;
	public static ServerSocket serverSocket;
	public static BufferedReader inputStream;
	public static boolean connectionOpen = true;

	public Server() {
		//print("Server started");
	}

	public void run() {
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber, 50);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(connectionOpen) {
			
			Socket socket = null;
			try {
				
				//print("Waiting for incoming connections.");

				socket = serverSocket.accept();

				//print("Response received from " + socket.getRemoteSocketAddress());	
				
				new Thread(new ClientHelper(socket)).start();
				
			}
			catch (IOException e) {
				print("Unable to open socket.");
			}
		}
		//serverSocket.close();
	}


	public void print(String input) {
		System.out.println(input);
	}

}
