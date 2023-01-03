package Entities;

import java.io.Serializable;

public class ProductForOrder implements Serializable{
	
	private String productName, price;
	public ProductForOrder(String productName, String price) {
		this.productName=productName;
		this.price=price;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setProductName(String productName) {
		 this.productName = productName;
	}
	public void setPrice(String price) {
		 this.price = price;
	}

}
