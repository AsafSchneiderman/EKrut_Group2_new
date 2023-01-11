package entity;

public class ConnectionStatus {
	
	private String host;
	private int IP;
	private Boolean statusType; // true - connected, false - not connected

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getIP() {
		return IP;
	}
	public void setIP(int iP) {
		IP = iP;
	}
	public Boolean getStatusType() {
		return statusType;
	}
	public void setStatusType(Boolean statusType) {
		this.statusType = statusType;
	}
}
