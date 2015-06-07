package feature;

import twitter4j.Status;

public class HasSymbolsFeature extends Feature{
	public String getArffHeader() {
		return "Has_Symbols {yes,no}";
	}
	public String getCVSHeader(){
		return "Has_Symbols";
	}
	
	public String getFeature(String text){
		return "?";
	}
	public String getFeature(Status tweet){
		if(tweet.getSymbolEntities().length<1)
		return "no";
		else return "yes";
	}
	
	public String getNeededType(){
		return "tweet";
	}
}
