//Author : Jack Peng
import java.util.ArrayList;
public class AdminAccount extends Account {
	private ArrayList<Account>accounts;
	
	//admin account constructor
	public AdminAccount(int id,String username,String password, ArrayList<Account>accounts) {
		super(username,password);
		this.accounts = accounts;
	}
}
