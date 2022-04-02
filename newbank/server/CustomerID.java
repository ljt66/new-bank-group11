package newbank.server;

public class CustomerID {
	private String key;
	private boolean admin;

	public CustomerID(String key, boolean admin) {
		this.key = key;
		this.admin = admin;
	}

	public CustomerID(String key) {
		this(key, false);
	}

	public String getKey() {
		return key;
	}

	public boolean isAdmin() {
		return admin;
	}
}