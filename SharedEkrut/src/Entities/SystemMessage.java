package Entities;

import java.io.Serializable;

public class SystemMessage implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String workerID;
	private String region;
	private String message;
	/**
	 * @param workerID
	 * @param message
	 */
	public SystemMessage(String workerID,String region, String message) {
		super();
		this.workerID = workerID;
		this.message = message;
	}
	
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}


	public String getWorkerID() {
		return workerID;
	}
	public String getMessage() {
		return message;
	}
	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
