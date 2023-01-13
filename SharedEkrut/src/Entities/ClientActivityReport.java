package Entities;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.chart.XYChart.Series;

public class ClientActivityReport extends Report{
	private static final long serialVersionUID = 4577899278807266896L;
	private ArrayList<HashMap<Integer,Integer>> amountOfClientsPerOrderSize; 

	public ClientActivityReport(String month, String year,String region,ArrayList<HashMap<Integer,Integer>> amountOfClientsPerOrderSize) {
		super(ReportType.Client_Activity, month, year, region);
		this.setAmountOfClientsPerOrderSize(amountOfClientsPerOrderSize);
	}

	public ArrayList<HashMap<Integer,Integer>> getAmountOfClientsPerOrderSize() {
		return amountOfClientsPerOrderSize;
	}

	public void setAmountOfClientsPerOrderSize(ArrayList<HashMap<Integer,Integer>> amountOfClientsPerOrderSize) {
		this.amountOfClientsPerOrderSize = amountOfClientsPerOrderSize;
	}

	public Series<String, Integer> getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

}
