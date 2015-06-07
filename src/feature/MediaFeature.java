package feature;

import twitter4j.Status;

public class MediaFeature extends Feature{
	public String getArffHeader() {
		return "media {video,photo,none}";
	}
	public String getCVSHeader(){
		return "media";
	}
		
	public String getFeature(Status tweet){
		
		if(tweet.getMediaEntities().length<1)		
		return "none";
		else 
			return tweet.getMediaEntities()[0].getType() ;
	}
	
}
