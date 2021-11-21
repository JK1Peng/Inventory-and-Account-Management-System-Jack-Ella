
//Author : Jack Peng
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Test_viewOrders_helper {

	public static void main(String[] args) {

		PrintWriter outgoing;
		int LISTENING_PORT = 32007;
		ServerSocket listener;
		Socket connection;

		try {
			listener = new ServerSocket(LISTENING_PORT);
			connection = listener.accept();
			outgoing = new PrintWriter(connection.getOutputStream());

			StoreThread.viewOrders(outgoing);
			connection.close();
			listener.close();

		} catch (Exception e) {
			System.out.println("Error: " + e);
			return;
		}
	}

}
