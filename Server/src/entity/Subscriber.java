package entity;

import java.io.Serializable;

public class Subscriber implements Serializable{
	private String FirstNameSub;
	private String LastNameSub;
	private String  IdSub;
	private String PhoneNumberSub;
	private String EmailAddressSub;
	private String CreditCardSub;
	private String SubscriberNumber;
	
	public Subscriber
	(String FirstNameSub,String LastNameSub,String  IdSub, String PhoneNumberSub,String EmailAddressSub,String CreditCardSub,String SubscriberNumber) {
		this.FirstNameSub = FirstNameSub;
		this.LastNameSub = LastNameSub;
		this.IdSub = IdSub;
		this.PhoneNumberSub = PhoneNumberSub;
		this.EmailAddressSub = EmailAddressSub;
		this.CreditCardSub = CreditCardSub;
		this.SubscriberNumber = SubscriberNumber;
	}
	
	public String getSubscriberNumber() {
		return SubscriberNumber;
	}

	public void setSubscriberNumber(String subscriberNumber) {
		SubscriberNumber = subscriberNumber;
	}

	public String getFirstNameSub() {
		return FirstNameSub;
	}

	public void setFirstNameSub(String firstNameSub) {
		FirstNameSub = firstNameSub;
	}

	public String getLastNameSub() {
		return LastNameSub;
	}

	public void setLastNameSub(String lastNameSub) {
		LastNameSub = lastNameSub;
	}

	public String getIdSub() {
		return IdSub;
	}

	public void setIdSub(String idSub) {
		IdSub = idSub;
	}

	public String getPhoneNumberSub() {
		return PhoneNumberSub;
	}

	public void setPhoneNumberSub(String phoneNumberSub) {
		PhoneNumberSub = phoneNumberSub;
	}

	public String getEmailAddressSub() {
		return EmailAddressSub;
	}

	public void setEmailAddressSub(String emailAddressSub) {
		EmailAddressSub = emailAddressSub;
	}

	public String getCreditCardSub() {
		return CreditCardSub;
	}

	public void setCreditCardSub(String creditCardSub) {
		CreditCardSub = creditCardSub;
	}
	
}
