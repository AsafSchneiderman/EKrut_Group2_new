package Entities;

import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

//import javafx.scene.control.ComboBox;

public class VendingMachine implements Serializable {

	private static final long serialVersionUID = 1L;
	private String region;
	private String location;
	private String thresholdLevel;
	private String restockStatus;
	private ComboBox<String> cmbBoxStatus;

	/**
	 * @param region         - the region of the vending machine
	 * @param location       - the location of the vending machine
	 * @param thresholdLevel - the threshold Level of the vending machine
	 * @param restockStatus  - 'WaitToRestock' or 'Done', comboBox of('WaitToRestock', 'Done')
	 */
	public VendingMachine(String region, String location, String thresholdLevel, String restockStatus) {
		super();
		this.region = region;
		this.location = location;
		this.thresholdLevel = thresholdLevel;
		this.restockStatus = restockStatus;

		//ObservableList<String> statusData=FXCollections.observableArrayList("WaitToRestock", "Done");
		//cmbBoxStatus = new ComboBox<String>(statusData); 
		//cmbBoxStatus.getItems().setAll("WaitToRestock", "Done");
		//cmbBoxStatus.setValue(restockStatus);
	}

	public String getRegion() {
		return region;
	}

	public String getLocation() {
		return location;
	}

	public String getThresholdLevel() {
		return thresholdLevel;
	}

	public String getRestockStatus() {
		return restockStatus;
	}

	public ComboBox<String> getCmbBoxStatus() {
		return cmbBoxStatus;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setThresholdLevel(String thresholdLevel) {
		this.thresholdLevel = thresholdLevel;
	}

	public void setRestockStatus(String restockStatus) {
		this.restockStatus = restockStatus;
	}

	public void setCmbBoxStatus(ComboBox<String> cmbBoxStatus) {
		this.cmbBoxStatus = cmbBoxStatus;
	}
	
	/**
	 * Initialize the comboBox with the statusData list
	 * @param statusData - ("LowStock","WaitToRestock", "Done")
	 */
	public void comboBoxInitialize(ObservableList<String> statusData)
	{
		cmbBoxStatus = new ComboBox<String>(statusData); 
		cmbBoxStatus.setValue(restockStatus);
	}

}
