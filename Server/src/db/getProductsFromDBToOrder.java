package db;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import Entities.*;

public class getProductsFromDBToOrder {
	
	static Product product;
	static ArrayList<Product>listOfProducts;
	
	public static ArrayList<Product> getProducts() {
		ArrayList<String> str = new ArrayList<>();;
		Statement stmt;
		String query = "";
		try {
			if (mysqlConnection.conn != null) {
				stmt = mysqlConnection.conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM ortbraudeproducts");
				while (rs.next()) {
					str.add(rs.getString("productID") +","+rs.getString("productName")+","+rs.getString("price") + ","+rs.getString("stockQuantity") +","+rs.getString("imgSrc"));
					
					}
				rs.close();
			} else {
				System.out.println("Conn is null");
			}
			
			 String[] arrOfStr;
			 for(int i=0; i < str.size(); i++)
			 {
				 arrOfStr = str.get(i).split(",");
				 int num1 = Integer.parseInt(arrOfStr[3]); 
				 product.setProductCode(arrOfStr[0]);
				 product.setQuantity(num1);
				 product.setProductName(arrOfStr[1]);
				 float num2 = convertStringToFloat(arrOfStr[3]);
				 product.setPrice(num2);
				 listOfProducts.add(product);
			 }
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return listOfProducts;
	}
	
	 // Function to convert String to Float
    public static float convertStringToFloat(String str)
    {
  
        // Convert string to float
        // using valueOf() method
        return Float.valueOf(str);
    }

}
