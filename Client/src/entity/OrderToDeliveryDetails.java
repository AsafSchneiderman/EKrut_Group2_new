package entity;

import java.io.Serializable;

import javafx.scene.control.Button;

public class OrderToDeliveryDetails implements Serializable {

	private String orderId;
	private String addressToDelivey;
	private String date; //need to change to DateObject
	private Button btnDeliverAccept;
	private Button btnOrderIsDone;
	
	
	public OrderToDeliveryDetails(String orderId, String addressToDelivey, String date) {
		
		this.orderId = orderId;
		this.addressToDelivey = addressToDelivey;
		this.date = date;
		this.btnDeliverAccept= new Button("Accept");
		this.btnOrderIsDone = new Button("Done");
		
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAddressToDelivey() {
		return addressToDelivey;
	}
	public void setAddressToDelivey(String addressToDelivey) {
		this.addressToDelivey = addressToDelivey;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Button getBtnDeliverAccept() {
		return btnDeliverAccept;
	}
	public void setBtnDeliverAccept(Button btnDeliverAccept) {
		this.btnDeliverAccept = btnDeliverAccept;
	}
	public Button getBtnOrderIsDone() {
		return btnOrderIsDone;
	}
	public void setBtnOrderIsDone(Button btnOrderIsDone) {
		this.btnOrderIsDone = btnOrderIsDone;
	}
	
}
