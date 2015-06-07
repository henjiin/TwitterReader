package feature;

import twitter4j.Status;

public class IsPossiblySensisitveFeature extends Feature{
	public String getArffHeader() {
		return "isPossiblySensite {yes,no}";
	}
	public String getCVSHeader(){
		return "isPossiblySensite";
	}
	
	public String getFeature(String text){
		return "?";
	}
	public String getFeature(Status tweet){
		if(tweet.isPossiblySensitive())return "yes";
		else return "no";
		
	}
	
	public String getNeededType(){
		return "tweet";
	}
}
