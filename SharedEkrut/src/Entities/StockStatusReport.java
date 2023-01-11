package Entities;

import java.util.ArrayList;
import java.util.List;

public class StockStatusReport extends Report {
	private static final long serialVersionUID = 3804980031254229265L;
	private List<VendingMachine> vendingMachines;
	private List<ArrayList<Product>> stocks;
	
	public StockStatusReport(String month, String year,ArrayList<VendingMachine> vendingMachines,ArrayList<ArrayList<Product>> stocks) {
		super(ReportType.Stock_Status, month, year);
		this.vendingMachines = vendingMachines;
		this.stocks=stocks;
	}

	public List<VendingMachine> getVendingMachines() {
		return vendingMachines;
	}
	
	public ArrayList<Product> getStocks(int index){
		return stocks.get(index);
	}

}
