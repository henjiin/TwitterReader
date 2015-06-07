package feature;

import twitter4j.Status;

public class StatusIDFeature extends Feature {
	public String getArffHeader()  {
		return "Status_ID numeric";
	}
	public String getCVSHeader(){
		return "Status_ID";
	}	
	
	public String getFeature(Status tweet){
		return String.valueOf(tweet.getId());
	}
	
}
