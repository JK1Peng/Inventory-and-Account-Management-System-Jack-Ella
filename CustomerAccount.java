public class CustomerAccount extends Account {
	private String profile;
	
	//client account constructor
	public CustomerAccount(int id, String username,String password,String profile) {
		super(username,password);
		this.profile = profile;
	}
	
	public String getProfile() {
		return profile;
	}
}
