
//Author: Jack P
import java.net.ServerSocket;
import java.net.Socket;

public class StoreServer {

	private static int LISTENING_PORT = 32007;

	public static void main(String[] args) {
		ServerSocket listener;
		Socket client;
		StoreThread[] worker = new StoreThread[100];
		int count = 0;
		try {
			listener = new ServerSocket(LISTENING_PORT);
			System.out.println("Listening on port " + LISTENING_PORT);
			while (true) {
				client = listener.accept();
				worker[count] = new StoreThread(client);
				worker[count].start();
			}
		} catch (Exception e) {
			System.out.println("Sorry, the server has shut down.");
			System.out.println("Error:  " + e);
			return;
		}

	}
}
