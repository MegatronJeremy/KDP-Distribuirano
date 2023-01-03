package chat;

import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		try (Socket server = new Socket(host, port);) {

			Chat chat = new Chat(server);
			chat.communicate();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}