package Entities;

import java.io.Serializable;
import javafx.scene.control.Button;

public class OrderToDeliveryDetails implements Serializable {

	private String orderId;
	private String addressToDelivey;
	private String date; 
	private String accept; 
	private String done; 
	private Button btnDeliverAccept;
	private Button btnOrderIsDone;
	
	
	public OrderToDeliveryDetails(String orderId, String addressToDelivey, String date, String accept, String done) {
		
		this.orderId = orderId;
		this.addressToDelivey = addressToDelivey;
		this.date = date;
		this.accept = accept;
		this.done = done;
	}
	

	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getDone() {
		return done;
	}
	public void setDone(String done) {
		this.done = done;
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

