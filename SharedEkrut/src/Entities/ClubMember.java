package Entities;
import java.io.Serializable;
public class ClubMember extends Customer implements Serializable {
	private static final long serialVersionUID = 7782388398415318676L;
	private float priceForTheEndOfTheMonth;
	public ClubMember(String Fname, String lastName, String id, String phoneNumber, String email, String userName,
			String password, String role, int isLoggedIn, int accountNum, String creditCard, float priceForTheEndOfTheMonth) {
		super(Fname, lastName, id, phoneNumber, email, userName, password, role, isLoggedIn, accountNum, creditCard);
		this.priceForTheEndOfTheMonth=priceForTheEndOfTheMonth;
	}
	public ClubMember(int accountNum) {
		super(accountNum);
		//this.priceForTheEndOfTheMonth=priceForTheEndOfTheMonth;
	}
	public float getPriceForTheEndOfTheMonth() {
		return priceForTheEndOfTheMonth;
	}
	
}
