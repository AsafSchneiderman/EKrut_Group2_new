package Entities;

import java.io.Serializable;

import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;


public class ProductForOrder {
	
	private String productName, price;
	private ImageView imgSrc;
	private String bntToAdd;
	public ProductForOrder(String productName, String price,ImageView imgSrc, String bntToAdd ) {
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
	public String getBntToAdd() {
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
	public void setBntToAdd(String bntToAdd) {
		 this.bntToAdd = bntToAdd;
	}
	
	

}
