package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class OrdersReport extends Report{
	private static final long serialVersionUID = 1983595595251314658L;
	
	private List<Order> orders;
	
	public OrdersReport(String month, String year,String region,List<Order> orders) {
		super(ReportType.Order, month, year, region);
		this.orders=orders;
	}
	
	public String toString() {
		return "month: "+this.getMonth()+", year: "+this.getYear()+ "\nOrders:\n" + orders;
	}
		
	public List<String> getLocationOfOrders(){
		String location;
		List<String> machineLocations = new ArrayList<>();
		for (Order order : orders) {
			location = order.getVendingMachineLocation();
			if (!machineLocations.contains(location))
				machineLocations.add(location);
		}
		return machineLocations;
	}
	
	/**
	 * 
	 * @return Quantities of pickup orders made in each machine
	 */
	public List<Integer> getQuantityOfPickupOrdersPerMachine(List<String> machineLocations){
		List<Integer> quantityOfOrdersPerMachine = new ArrayList<>();
		for (String location : machineLocations) {
			int pickupQuantity = 0;
			for (Order order : orders) {
				if (order.getVendingMachineLocation().equals(location) && order.getOrderType().equals("pickup"))
					pickupQuantity = pickupQuantity + 1;
			}
			quantityOfOrdersPerMachine.add(pickupQuantity);
		}
		
		return quantityOfOrdersPerMachine;
	}
	
	/**
	 * 
	 * @return Quantities of local orders made in each machine
	 */
	public List<Integer> getQuantityOfLocalOrdersPerMachine(List<String> machineLocations){
		List<Integer> quantityOfOrdersPerMachine = new ArrayList<>();
		for (String location : machineLocations) {
			int localQuantity = 0;
			for (Order order : orders) {
				if (order.getVendingMachineLocation().equals(location) && order.getOrderType().equals("local"))
					localQuantity = localQuantity + 1;
			}
			quantityOfOrdersPerMachine.add(localQuantity);
		}
		
		return quantityOfOrdersPerMachine;
	}
	
	public XYChart.Series<String, Integer> getGraph(String type){
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		List<String> machineLocations = getLocationOfOrders();
		series.setName(type);
		if (type.equals("pickup"))
		{
			List<Integer> quantityOfPickupOrdersPerMachine = getQuantityOfPickupOrdersPerMachine(machineLocations);
			for (int i = 0 ; i < machineLocations.size() ; i++)
				series.getData().add(new XYChart.Data<String, Integer>(machineLocations.get(i), quantityOfPickupOrdersPerMachine.get(i)));
		}
		else 
		{
			List<Integer> quantityOfLocalpOrdersPerMachine = getQuantityOfLocalOrdersPerMachine(machineLocations);
			for (int i = 0 ; i < machineLocations.size() ; i++)
				series.getData().add(new XYChart.Data<String, Integer>(machineLocations.get(i), quantityOfLocalpOrdersPerMachine.get(i)));
		}
		return series;
	}
}
