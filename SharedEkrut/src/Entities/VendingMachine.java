package Entities;

import java.io.Serializable;

//import javafx.scene.control.ComboBox;

public class VendingMachine implements Serializable {

	private static final long serialVersionUID = 7782388398415318676L;
	private String region;
	private String location;
	private String thresholdLevel;
	private String restockStatus;
	//private ComboBox<String> cmbBoxStatus;

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

		//cmbBoxStatus = new ComboBox<String>();
		//cmbBoxStatus.getItems().addAll("WaitToRestock", "Done");
		//cmbBoxStatus.setPromptText(restockStatus);
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

	//public ComboBox<String> getCmbBoxStatus() {
	//	return cmbBoxStatus;
	//}

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

	//public void setCmbBoxStatus(ComboBox<String> cmbBoxStatus) {
	//	this.cmbBoxStatus = cmbBoxStatus;
	//}

}
