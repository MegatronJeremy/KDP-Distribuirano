package kdp;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserAccountImpl extends UnicastRemoteObject 
	implements UserAccount, Serializable {
	public UserAccountImpl(String name) throws RemoteException {
		this.status = 0;
		this.name = name;
	}

	private void work() {
		try {
			Thread.sleep(500 + (int) (Math.random() * 1000));
		} catch (InterruptedException e) {

		}
	}

	public synchronized float getStatus() {
		work();
		return status;
	}

	public synchronized void transaction(float value) {
		work();
		while (status + value < 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		status += value;
		notifyAll();
	}

	// za udaljeni pristup koji nije upisan u registar
	private static final long serialVersionUID = 1L;
	private float status;
	private String name;

}
