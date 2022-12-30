package Entities;

import java.util.ArrayList;
import java.util.List;

public class StockStatusReport extends Report {
	private static final long serialVersionUID = 3804980031254229265L;
	private List<VendingMachine> vendingMachines;
	
	public StockStatusReport(String month, String year,ArrayList<VendingMachine> vendingMachines) {
		super("Stock status", month, year);
		this.vendingMachines = vendingMachines;
	}

	public List<VendingMachine> getVendingMachines() {
		return vendingMachines;
	}

}
