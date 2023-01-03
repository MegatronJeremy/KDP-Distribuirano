package chat;

import java.net.Socket;

public class Chat {
	Socket client;

	public Chat(Socket client) {
		this.client = client;
	}

	public void communicate() {
		// fora koja moze da se iskoristi
		/**
		 * Lokalni try objekat klijent koga postavim na this.client Zasto? Jer ce se na
		 * kraju pozvati client.close Zapravo this.client se poziva Jer je to objekat
		 * koji je prosledjen Exception - moze ako zatvaramo nesto sto je vec zatvoreno
		 * (na drugoj strani) - ali nema veze - imamo catch
		 */
		try (Socket client = this.client) {
			ReadThread read = new ReadThread(client);
			WriteThread write = new WriteThread(client);
			read.start();
			write.start();
			read.join();
			write.join();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
