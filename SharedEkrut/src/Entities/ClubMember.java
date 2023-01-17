package Entities;

import java.io.Serializable;

public class ClubMember  implements Serializable {
	private static final long serialVersionUID = 7782388398415318676L;
<<<<<<< HEAD
	private String userID;
	private int isNew;
	
	
	public ClubMember(String userID, int isNew) {
		super();
		this.userID = userID;
		this.isNew = isNew;
=======
	private float priceForTheEndOfTheMonth;

	public ClubMember(String userID, String id, String firstName, String lastName, String userName, String password,
			String role, String email, String phoneNumber, int isLoggedIn, int accountNum, String creditCard,
			float priceForTheEndOfTheMonth) {
		super(userID, firstName, lastName, id, phoneNumber, email, userName, password, role, isLoggedIn, accountNum,
				creditCard, creditCard);
		this.priceForTheEndOfTheMonth = priceForTheEndOfTheMonth;
>>>>>>> branch 'main' of https://github.com/AsafSchneiderman/EKrut_Group2_new.git
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	
	
	


}
