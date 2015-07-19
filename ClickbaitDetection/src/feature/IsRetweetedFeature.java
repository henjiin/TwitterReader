package feature;

import message.Message;
import twitter4j.Status;

public class IsRetweetedFeature extends Feature{
	public String getArffHeader() {
		return "IsRetweeted {yes, no}";
	}
	public String getCVSHeader(){
		return "is_retweetet";
	}
	
	public String getFeature(String text){
		return "?";
	}
	public String getFeature(Status tweet){
		
		if(tweet.isRetweet())return "yes";
		else return "no";
	}
	
	public String getNeededType(){
		return "tweet";
	}
	@Override
	public String getFeature(Message message) {
		return "?";
	}
	
}
