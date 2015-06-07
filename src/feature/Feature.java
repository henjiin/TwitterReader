package feature;

import twitter4j.Status;

public abstract class Feature {

	public String getArffHeader() {
		return "optinal String";
	}
	public String getCVSHeader(){
		return "optonal";
	}
	
	public String getFeature(Status tweet){
		return "?";
	}

}
