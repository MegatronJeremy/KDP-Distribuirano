package kdp;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	@SuppressWarnings({ "removal", "deprecation" })
	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
			;
		}
		try {
			Bank bank = new BankImpl();
			Bank stub = (Bank) UnicastRemoteObject.exportObject(bank, 0);
			String urlString = "/Bank";
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(urlString, stub);
		} catch (RemoteException e) {
		}
	}

	public static final int port = 4001;
	public static final String server = "localhost";
}
