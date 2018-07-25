package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static String serverIP = "127.0.0.1";
	public static int portNumber = 3000;
	public static Socket socket;
	public static ServerSocket serverSocket;
	public static BufferedReader inputStream;
	public static boolean connectionOpen = true;

	public static void main(String[] args) {
		if (args.length != 0) {
			serverIP = args[0];
			portNumber = Integer.parseInt(args[1]);
			
		}
		
		print("Server started");
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber, 50);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		print("Waiting for incoming connections.");
		while(connectionOpen) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				new Thread(new ClientHelper(socket)).start();
				
			}
			catch (IOException e) {
				print("Unable to open socket.");
			}
		}	
	}

	public static void print(String input) {
		System.out.println(input);
	}

}
