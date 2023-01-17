package Entities;

import java.io.Serializable;

import javafx.scene.control.Button;

public class PromotionSells implements Serializable{
	
	private String promotion;
	private String activated; 
	private String region;
	private Button btnactivated;
	private Button btndeactivated;
	
	public PromotionSells(String promotion, String activated,String region) {
		super();
		this.region = region;
		this.promotion = promotion;
		this.activated = activated;
	}

	public Button getBtnactivated() {
		return btnactivated;
	}

	public void setBtnactivated(Button btnactivated) {
		this.btnactivated = btnactivated;
	}

	public Button getBtndeactivated() {
		return btndeactivated;
	}

	public void setBtndeactivated(Button btndeactivated) {
		this.btndeactivated = btndeactivated;
	}

	public String getRegion() {
		return region;
	}
	
	

	public void setRegion(String region) {
		this.region = region;
	}

	public  String getPromotionWithPrecent() {
		return promotion +"%";
	}
	
	public  String getPromotion() {
		return promotion ;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getActivated() {
		return activated;
	}

	public void setActivated(String activated) {
		this.activated = activated;
	}
	

}
