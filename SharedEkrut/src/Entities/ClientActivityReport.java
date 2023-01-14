package Entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class ClientActivityReport extends Report{
	private static final long serialVersionUID = 4577899278807266896L;
	private ArrayList<HashMap<Integer,Integer>> amountOfClientsPerOrderAmount; 

	public ClientActivityReport(String month, String year,String region,ArrayList<HashMap<Integer,Integer>> amountOfClientsPerOrderAmount) {
		super(ReportType.Client_Activity, month, year, region);
		this.setAmountOfClientsPerOrderSize(amountOfClientsPerOrderAmount);
	}

	public ArrayList<HashMap<Integer,Integer>> getAmountOfClientsPerOrderAmount() {
		return amountOfClientsPerOrderAmount;
	}

	public void setAmountOfClientsPerOrderSize(ArrayList<HashMap<Integer,Integer>> amountOfClientsPerOrderAmount) {
		this.amountOfClientsPerOrderAmount = amountOfClientsPerOrderAmount;
	}
	
	@Override
	public String toString() {
		String res = "[";
		for (HashMap<Integer, Integer> map : amountOfClientsPerOrderAmount) {
			Set<Integer> key = map.keySet();
			for (Integer ordersAmount : key)
				res += "{"+ordersAmount+", " + map.get(ordersAmount) + "} | ";
		}
		res += "]";
		return res;
	}
	
	public List<String> getRangeList(){
		List<String> rangeList = new ArrayList<>();
		rangeList.add("1-2");
		rangeList.add("3-4");
		rangeList.add("5-6");
		rangeList.add("7-8");
		rangeList.add("9-10");
		return rangeList;
	}


	private Integer[] getClientAmountPerRange() {
		Integer[] amountsPerRange = new Integer[5];
		Arrays.fill(amountsPerRange, 0);
		for (HashMap<Integer, Integer> map : amountOfClientsPerOrderAmount) {
			Set<Integer> key = map.keySet();
			for (Integer ordersAmount : key) {
				int amountOfClients = map.get(ordersAmount);
				amountsPerRange[(ordersAmount-1)/2] += amountOfClients;
			}
		}
		return amountsPerRange;
	}

	public XYChart.Series<String, Integer> getGraph() {
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		List<String> orderSizeRangeList = getRangeList();
		Integer[] clientAmountPerRange = getClientAmountPerRange();
		for (int i = 0 ; i < orderSizeRangeList.size() ; i++)
			series.getData().add(new XYChart.Data<String, Integer>(orderSizeRangeList.get(i),  clientAmountPerRange[i]));
		return series;
	}

}
