package feature;

import message.Message;
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
	@Override
	public String getFeature(Message message) {
		// TODO Auto-generated method stub
		return "TODO";
	}
	
}
