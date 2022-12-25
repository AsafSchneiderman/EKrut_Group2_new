package Entities;
import java.io.Serializable;
public class VendingMachine implements Serializable{
	private static final long serialVersionUID = 7782388398415318676L;
	private String vendingMachineLocation;
	public VendingMachine(String vendingMachineLocation) {
		this.vendingMachineLocation=vendingMachineLocation;
	}
	public String getVendingMachineLocation() {
		return vendingMachineLocation;
	}
	public void setVendingMachineLocation(String vendingMachineLocation) {
		this.vendingMachineLocation = vendingMachineLocation;
	}
	
}
