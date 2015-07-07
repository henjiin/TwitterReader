package feature;

import message.Message;
import twitter4j.EntitySupport;
import twitter4j.Status;

public class HasSymbolsFeature extends Feature{
	public String getArffHeader() {
		return "Has_Symbols {yes,no}";
	}
	public String getCVSHeader(){
		return "Has_Symbols";
	}
	
	public String getFeature(Status tweet){
		if(tweet.getSymbolEntities().length<1)
		return "no";
		else return "yes";
	}
	
	@Override
	public String getFeature(Message message) {
		if(message.isTweet()){
			if(((Status) message).getSymbolEntities().length<1)
			return "no";
			else return "yes";
			
		}
		return "?";
	}
}
