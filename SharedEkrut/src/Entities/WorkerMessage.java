package Entities;

import java.io.Serializable; 

public class WorkerMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String workerID;
	private String message;
	private String msgStatus;
	/**
	 * @param id
	 * @param workerID
	 * @param message
	 * @param msgStatus
	 */
	public WorkerMessage(String id, String workerID, String message, String msgStatus) {
		super();
		this.id = id;
		this.workerID = workerID;
		this.message = message;
		this.msgStatus = msgStatus;
	}
	public String getId() {
		return id;
	}
	public String getWorkerID() {
		return workerID;
	}
	public String getMessage() {
		return message;
	}
	public String getMsgStatus() {
		return msgStatus;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setWorkerID(String workerID) {
		this.workerID = workerID;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	

}
