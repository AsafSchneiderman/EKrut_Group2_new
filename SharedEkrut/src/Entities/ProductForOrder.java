package Entities;

import java.io.Serializable;

import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


public class ProductForOrder {
	
	private String productName, price;
	private ImageView imgSrc;
	private Button bntToAdd;
	public ProductForOrder(String productName, String price,ImageView imgSrc, Button bntToAdd ) {
		this.productName=productName;
		this.price=price;
		this.imgSrc = imgSrc;
		this.bntToAdd = bntToAdd;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public String getPrice() {
		return price;
	}
	public Button getBntToAdd() {
		return bntToAdd;
	}
	public ImageView getImgSrc() {
		return imgSrc;
	}
	
	public void setProductName(String productName) {
		 this.productName = productName;
	}
	public void setPrice(String price) {
		 this.price = price;
	}
	public void setPrice(ImageView imgSrc) {
		 this.imgSrc = imgSrc;
	}
	public void setBntToAdd(Button bntToAdd) {
		 this.bntToAdd = bntToAdd;
	}
	
	

}
