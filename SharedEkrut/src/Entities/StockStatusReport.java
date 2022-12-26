package Entities;

public class StockStatusReport extends Report {
	private static final long serialVersionUID = 3804980031254229265L;
	private Product[] products;
	
	public StockStatusReport(String month, String year,Product[] products) {
		super("Stock status", month, year);
		this.products = products;
	}

	public Product[] getProducts() {
		return products;
	}

}
