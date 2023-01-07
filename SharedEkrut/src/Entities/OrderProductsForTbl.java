package Entities;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class OrderProductsForTbl {
	
	private String productName, price, Quantity;
	private ImageView imgSrc;
	private Button bntToAdd, bntToSub;
	public OrderProductsForTbl(String productName, String price, String quantity, ImageView imgSrc, Button bntToAdd,
			Button bntToSub) {
		super();
		this.productName = productName;
		this.price = price;
		Quantity = quantity;
		this.imgSrc = imgSrc;
		this.bntToAdd = bntToAdd;
		this.bntToSub = bntToSub;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public ImageView getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(ImageView imgSrc) {
		this.imgSrc = imgSrc;
	}
	public Button getBntToAdd() {
		return bntToAdd;
	}
	public void setBntToAdd(Button bntToAdd) {
		this.bntToAdd = bntToAdd;
	}
	public Button getBntToSub() {
		return bntToSub;
	}
	public void setBntToSub(Button bntToSub) {
		this.bntToSub = bntToSub;
	}

}
