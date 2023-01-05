package Entities;
import java.io.Serializable;


public class Product implements Serializable {
	private static final long serialVersionUID = -2028421550470538558L;//???????

	private String productID;
	private String productName;
	private String price;
	private String stockQuantity;
	private String imgSrc;
	public Product(String productID, String productName, String price, String stockQuantity, String imgSrc) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.imgSrc = imgSrc;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;

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
	public String getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
}