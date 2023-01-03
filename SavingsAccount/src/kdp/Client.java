package kdp;

import java.rmi.registry.*;

public class Client {
	public static void main(String[] args) {
		try {
			Bank bank = null;
			UserAccount userAccount = null;
			String name = args[0];

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			String urlString = "/Bank";

			// za ovo mi treba registar - ako nemam remote metod koji ce mi vracati objekat
			// vec ja zelim da mu pristupim DIREKTNO NA SERVERU (ili bilo gde)
			Registry registry = LocateRegistry.getRegistry(host, port);
			bank = (Bank) registry.lookup(urlString);

			userAccount = bank.getUserAccount(name);

			for (int m = 0; m < 100; m++) {
				float nstatus = (float) (50 + m - (int) (Math.random() * 100));
				try {
					System.out.println("Status: " + userAccount.getStatus());
					System.out.println("Promena statusa za " + nstatus);
					userAccount.transaction(nstatus);
					System.out.println("Novi status: " + userAccount.getStatus());
				} catch (Exception e) {
					System.err.println("Greska pri transakciji za " + name);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String host = "localhost";
	public static final int port = 4001;

}
