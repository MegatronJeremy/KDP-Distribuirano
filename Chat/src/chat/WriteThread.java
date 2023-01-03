package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {

	Socket client;

	public WriteThread(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try (Socket client = this.client;
				OutputStream out = client.getOutputStream();
				PrintWriter pout = new PrintWriter(out, true);

				// tok podataka kao bilo koji drugi
				InputStream is = System.in;
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);) {

			String s;

			// proveravam dok nemam greske sa izlaznim tokom
			while (!pout.checkError() && (s = br.readLine()) != null) {
				// eventualno mogu dodati i poruku za slanje - ko salje
				pout.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
