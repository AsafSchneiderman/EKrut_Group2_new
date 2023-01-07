package Entities;

import javafx.scene.control.Button;

public class ButtonForUsersToSignup {
	private String id,firstName,lastName;
	private Button btnShow;
	public ButtonForUsersToSignup(String id, String firstName, String lastName, Button btnShow) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.btnShow = btnShow;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Button getBtnShow() {
		return btnShow;
	}
	public void setBtnShow(Button btnShow) {
		this.btnShow = btnShow;
	}
	
}
