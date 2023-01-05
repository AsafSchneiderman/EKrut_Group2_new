package Entities;
import java.io.Serializable;


public class Product implements Serializable {
	private static final long serialVersionUID = -2028421550470538558L;//???????
<<<<<<< Upstream, based on branch 'main' of https://github.com/AsafSchneiderman/EKrut_Group2_new.git
	private String productName,productCode;
	private float price;
	private int quantity;
	public Product(String productName, String productCode, float price,int quantity) {
		this.productName=productName;
		this.productCode=productCode;
		this.price=price;
		this.quantity=quantity;
=======
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
>>>>>>> deb21c7 changes in orderFrame
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