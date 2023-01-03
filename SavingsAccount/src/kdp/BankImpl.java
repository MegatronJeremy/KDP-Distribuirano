package kdp;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class BankImpl implements Bank {

	public BankImpl() {
		users = new HashMap<String, UserAccount>();
	}

	@Override
	public UserAccount getUserAccount(String name) throws RemoteException {
		UserAccount user = users.get(name);
		if (user != null) {
			return user;
		}
		try {
			// treba PRISTUPATI UDALJENOJ REFERENCI - NE SAMO
			// UDALJENOJ METODI (za to je dovoljno samo remote
			// da implementira)
			user = new UserAccountImpl(name);
		} catch (RemoteException e) {
		}
		users.put(name, user);
		return user;
	}

	// ako nam treba iz nekog razloga????
	private static final long serialVersionUID = 1L;
	private static transient Map<String, UserAccount> users;

}
