package Entities;

import java.io.Serializable;

public class ClubMember  implements Serializable {
	private static final long serialVersionUID = 7782388398415318676L;
	private String userID;
	private int isNew;
	
	
	public ClubMember(String userID, int isNew) {
		super();
		this.userID = userID;
		this.isNew = isNew;
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
