package Entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class StockStatusReport extends Report {
	private static final long serialVersionUID = 3804980031254229265L;
	private List<VendingMachine> vendingMachines;
	private List<ArrayList<Product>> stocks;
	
	public StockStatusReport(String month, String year,String region, ArrayList<VendingMachine> vendingMachines,ArrayList<ArrayList<Product>> stocks) {
		super(ReportType.Stock_Status, month, year, region);
		this.vendingMachines = vendingMachines;
		this.stocks=stocks;
	}

	public List<VendingMachine> getVendingMachines() {
		return vendingMachines;
	}
	
	public ArrayList<Product> getStocks(int index){
		return stocks.get(index);
	}
	
	public String toString() {
		String res = null;
		for (int i = 0 ; i < vendingMachines.size(); i++)
			res += "month: "+this.getMonth()+", year: "+this.getYear()
				+ "\nMachineLocation: " + vendingMachines.get(i).getLocation() + "\nstocks:\n[" +stocks.get(i).toString() + "]";
		return res + "\n";
	}
	
	
	/**
	 * 
	 * @return List of the names of all the vending machine locations
	 */
	public List<String> getVendingMachinesLocations(){
		List<String> locations = new ArrayList<>();
		for (VendingMachine vendingMachine : vendingMachines)
			locations.add(vendingMachine.getLocation());
		return locations;
	}
	
	/**
	 * 
	 * @param location the location of the vending machine
	 * @return List of the names of all the products in the vending machine
	 */
	public List<String> getNameOfProducts(String location) {
		List<String> productsNames = new ArrayList<>();
		List<String> names = getVendingMachinesLocations();
		if (names.indexOf(location) >= 0) {
			ArrayList<Product> products = getStocks(names.indexOf(location));
			for (Product product : products)
				productsNames.add(product.getProductName());
			return productsNames;
		}
		return null;
	}
	
	/**
	 * 
	 * @param location the location of the vending machine
	 * @return List of the stock quantity of each product in the vending machine
	 */
	public List<Integer> getQuantityOfPorducts(String location){
		List<Integer> productsStockQuantity = new ArrayList<>();
		List<String> names = getVendingMachinesLocations();
		ArrayList<Product> products = stocks.get(names.indexOf(location));
		for (Product product : products)
			productsStockQuantity.add(Integer.parseInt(product.getStockQuantity()));
		return productsStockQuantity;
		
	}

	/**
	 * 
	 * @param location the location of the vending machine
	 * @return Series of quantities per product in the vending machine
	 */
	public Series<String, Integer> getGraph(String location) {
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		List<String> productsNames = getNameOfProducts(location);
		if (productsNames == null)
			return null;
		List<Integer> productsStockQuantity = getQuantityOfPorducts(location);
		for (int i = 0 ; i < productsNames.size() ; i++)
			series.getData().add(new XYChart.Data<String, Integer>(productsNames.get(i), productsStockQuantity.get(i)));
		return series;
	}

}
