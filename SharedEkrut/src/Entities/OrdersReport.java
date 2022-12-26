package Entities;

public class OrdersReport extends Report{
	private static final long serialVersionUID = 1983595595251314658L;
	//private String month,year;
	
	// TODO add array of locations
	// TODO add array of orders
	
	private String vendingMachineLocation;
	private String product;
	private int quantity;
	public OrdersReport(String month, String year,String vendingMachineLocation,String product,int quantity) {
		super("Order", month, year);
		//this.month=month;
		//this.year=year;
		this.vendingMachineLocation=vendingMachineLocation;
		this.product=product;
		this.quantity=quantity;
	}
	/*
	 * 
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	*/
	public String getVendingMachineLocation() {
		return vendingMachineLocation;
	}
	public void setVendingMachineLocation(String vendingMachineLocation) {
		this.vendingMachineLocation = vendingMachineLocation;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String toString() {
		return "Vending machine Location: "+vendingMachineLocation+
				", month: "+this.getMonth()+", year: "+this.getYear()+", product: "+product+", quantity: "+quantity;
	}
	
}
