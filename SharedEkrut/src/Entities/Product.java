package Entities;
import java.io.Serializable;


public class Product implements Serializable {
	private static final long serialVersionUID = -2028421550470538558L;//???????
	private String productName,productCode;
	private float price;
	private int quantity;
	public Product(String productName, String productCode, float price) {
		this.productName=productName;
		this.productCode=productCode;
		this.price=price;
	}
	public String getProductName() {
		return productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public float getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	@Override
	public String toString() {
		return "Product [name: "+productName+" code: "+productCode+" price for one:"
				+ " "+price+" quantity: "+quantity+"final price: "+price*quantity+"]";
	}
}
