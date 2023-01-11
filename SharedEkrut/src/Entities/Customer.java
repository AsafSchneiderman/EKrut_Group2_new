package Entities;

import java.io.Serializable;

public class Customer extends User implements Serializable {

	private static final long serialVersionUID = -8341341209307865291L;
	private int accountNum;
	private String creditCard;

	public Customer(String userID, String id, String firstName, String lastName, String userName, String password,
			String role, String email, String phoneNumber, int isLoggedIn, int accountNum, String creditCard) {
		super(userID, id, firstName, lastName, userName, password, role, email, phoneNumber, isLoggedIn);
		this.accountNum = accountNum;
		this.creditCard = creditCard;
	}

	public Customer(int accountNum) {
		super();
		this.accountNum = accountNum;
	}

	public int getAccountNum() {
		return accountNum;
	}

	public void setAccount(int accountNum) {
		this.accountNum = accountNum;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

}
