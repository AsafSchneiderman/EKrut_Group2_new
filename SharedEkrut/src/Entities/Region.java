package Entities;

public enum Region {
	North, South, UAE;
	public static Region toRegion(String type) {
		switch(type) {
		case "North":
			return North;
		case "South":
			return South;
		case "UAE":
			return UAE;
		default:
			return null;
		}
	}
	public static String regionToString(Region rg) {
		String retVal=null;
		switch(rg) {
		case North:
			retVal="North";
			return retVal;
		case South:
			retVal="South";
			return retVal;
		case UAE:
			retVal="UAE";
			return retVal;
		default:
			return null;
		}
	}
	
}
