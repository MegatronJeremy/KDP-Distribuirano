package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {

	Socket client;

	public ReadThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try (Socket client = this.client;
				InputStream in = client.getInputStream();
				BufferedReader pin = new BufferedReader(new InputStreamReader(in));) {

			String s;
			// citam dok ima nesto da se cita
			while ((s = pin.readLine()) != null) {
//				System.out.println("> " + s);
				// ako zelim da vidim od koga primam poruku
				System.out.println(String.format("%s:%d > %s", client.getInetAddress(), client.getPort(), s));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
