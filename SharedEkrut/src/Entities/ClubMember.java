package Entities;

import java.io.Serializable;

public class ClubMember extends Customer implements Serializable {
	private static final long serialVersionUID = 7782388398415318676L;
	private float priceForTheEndOfTheMonth;

	public ClubMember(String userID, String id, String firstName, String lastName, String userName, String password,
			String role, String email, String phoneNumber, int isLoggedIn, int accountNum, String creditCard,
			float priceForTheEndOfTheMonth) {
		super(userID, firstName, lastName, id, phoneNumber, email, userName, password, role, isLoggedIn, accountNum,
				creditCard, creditCard);
		this.priceForTheEndOfTheMonth = priceForTheEndOfTheMonth;
	}

	public ClubMember(int accountNum) {
		super(accountNum);
		// this.priceForTheEndOfTheMonth=priceForTheEndOfTheMonth;
	}

	public float getPriceForTheEndOfTheMonth() {
		return priceForTheEndOfTheMonth;
	}

}
