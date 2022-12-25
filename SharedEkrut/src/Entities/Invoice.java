package Entities;
import java.io.Serializable;
public class Invoice implements Serializable {
	private static final long serialVersionUID = 4087249272654584954L;
	private int orderNum;
	private String vendingMachineLocation;
	private float totalPrice;
	private float priceAfterDisscount;
	public Invoice(int orderNum,String vendingMachineLocation,float totalPrice, float priceAfterDisscount) {
		this.orderNum=orderNum;
		this.vendingMachineLocation=vendingMachineLocation;
		this.totalPrice=totalPrice;
		this.priceAfterDisscount=priceAfterDisscount;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getVendingMachineLocation() {
		return vendingMachineLocation;
	}
	public void setVendingMachineLocation(String vendingMachineLocation) {
		this.vendingMachineLocation = vendingMachineLocation;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public float getPriceAfterDisscount() {
		return priceAfterDisscount;
	}
	public void setPriceAfterDisscount(float priceAfterDisscount) {
		this.priceAfterDisscount = priceAfterDisscount;
	}
}
