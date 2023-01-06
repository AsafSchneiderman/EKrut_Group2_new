package Entities;

import java.io.Serializable;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class ProductForOrder {
	
	private String productName, price;
	private ImageView imgSrc;
	public ProductForOrder(String productName, String price,ImageView imgSrc) {
		this.productName=productName;
		this.price=price;
		this.imgSrc = imgSrc;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public String getPrice() {
		return price;
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

}
