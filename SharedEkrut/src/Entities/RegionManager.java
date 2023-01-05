package Entities;

import java.io.Serializable;

public class RegionManager extends User implements Serializable {
	private static final long serialVersionUID = -4559774050765854115L;

	private String region;

	public RegionManager(String id, String firstName, String lastName, String userName, String password, String role,
			String email, String phoneNumber, int isLoggedIn, String region ) {
		super(id, firstName, lastName, userName, password, role, email, phoneNumber, isLoggedIn);
		this.region=region;

	}
	
	public RegionManager(User user, String region)
	{
		super(user);
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
