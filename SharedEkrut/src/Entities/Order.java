package Entities;
import java.io.Serializable;
public class Order implements Serializable{
	private static final long serialVersionUID = -8005643298429981017L;
	private int orderNum;
	private String orderDate, orderStatus, customerID, vendingMachineLocation, vendingMachineID,orderType;
	private float totalPrice;
	private String products;
	public Order (String vendingMachineLocation,String orderDate,String orderStatus,String customerID,String vendingMachineID,float totalPrice, String orderType ) {
		this.vendingMachineLocation=vendingMachineLocation;
		this.orderDate=orderDate;
		this.orderStatus=orderStatus;
		this.customerID=customerID;
		this.vendingMachineID=vendingMachineID;
		this.totalPrice=totalPrice;
		this.orderType=orderType;
	}
	public Order(int orderNum,String vendingMachineLocation,String orderDate) {
		this.orderNum=orderNum;
		this.vendingMachineLocation=vendingMachineLocation;
		this.orderDate=orderDate;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum=orderNum;
	}
	public String getVendingMachineLocation() {
		return vendingMachineLocation;
	}
	public void setVendingMachineLocation(String vendingMachineLocation) {
		this.vendingMachineLocation=vendingMachineLocation;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate=orderDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus=orderStatus;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID=customerID;
	}
	public String getVendingMachineID() {
		return vendingMachineID;
	}
	public void setVendingMachineID(String vendkingMachineID) {
		this.vendingMachineID=vendingMachineID;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice=totalPrice;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType=orderType;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products=products;
	}
}
