package Entities;
import java.io.Serializable;
public class Worker extends User{
	private String jobType;
	private String region;

	public Worker(String firstName, String lastName, String id, String phoneNumber, String email, String userName, String password,
			String role,int isLoggedIn, String jobType, String region) {
		super(id,firstName,lastName,userName,password,role,email,phoneNumber,isLoggedIn);
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
