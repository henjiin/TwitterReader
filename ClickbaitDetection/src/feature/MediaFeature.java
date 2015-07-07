package feature;

import message.Message;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class MediaFeature extends Feature{
	public String getArffHeader() {
		return "attachedMedia String";
	}
	public String getCVSHeader(){
		return "attachedMedia";
	}
		
	public String getFeature(Status tweet){
		
		if(tweet.getMediaEntities().length<1)		
		return "none";
		else return tweet.getMediaEntities()[0].getType();
				
		
		}	
	public String getFeature(Message message) {
		if(message.hasMedia())
			return message.getMediaType();
		else return "?";
	}
	
}
