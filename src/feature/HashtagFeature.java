package feature;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

public class HashtagFeature  extends Feature{
	public String getArffHeader() {
		return "Hashtags String";
	}
	public String getCVSHeader(){
		return "Hashtags";
	}
	
	public String getFeature(String text){
		return "?";
	}
	public String getFeature(Status tweet){
		if(tweet.getHashtagEntities().length<1)
			return "\'noHashtags\'";
			else{
				String hashtags="\'";
				for(HashtagEntity hashtag:tweet.getHashtagEntities()){
					hashtags+=hashtag.getText()+" ";
				}
				hashtags=hashtags.trim();
				hashtags+="\'";
				return hashtags;
			}
	}
	
	public String getNeededType(){
		return "tweet";
	}
	
}
