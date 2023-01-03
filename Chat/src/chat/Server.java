package chat;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);

		try (ServerSocket listener = new ServerSocket(port);) {

			Socket client = listener.accept();
			Chat chat = new Chat(client);
			chat.communicate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
