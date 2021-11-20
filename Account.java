//Author : Jack Peng
abstract class Account {
	private String username;
	private String password;
	private int	id;

	// Constructor for account
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getID() {
		return id+"";
	}
	// username getter
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	// verify password
	public Boolean verifyPassword(String password) {
		if (this.password.equals(password)) {
			return true;
		} else {
			return false;
		}

	}

	// print username and class type
	public String toString() {
		String result = "Username: " + username + ", " + this.getClass();
		return result;
	}

	// set a new password
	public void setPassword(String newPassword) {
		password = newPassword;

	}

}
