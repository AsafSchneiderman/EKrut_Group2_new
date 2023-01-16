package Entities;

import java.io.Serializable;

public class Worker extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String jobType;
	private String region;

	public Worker(String userID, String id, String firstName, String lastName, String userName, String password,
			String role, String email, String phoneNumber, int isLoggedIn, String jobType, String region) {
		super(userID, id, firstName, lastName, userName, password, role, email, phoneNumber, isLoggedIn, region);
		this.jobType = jobType;
		this.region = region;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
