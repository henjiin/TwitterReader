package feature;

import message.Message;
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

	public String getFeature(Status tweet){
		if(tweet.getHashtagEntities().length<1)
			return "\'noHashtags\'";
			else{
				String hashtags="";
				for(HashtagEntity hashtag:tweet.getHashtagEntities()){
					hashtags+=hashtag.getText()+" ";
				}
				hashtags=hashtags.replaceAll("\'", "").trim();
				hashtags+="\'";
				return "\'"+hashtags;
			}
	}	
	
	@Override
	public String getFeature(Message message) {
//		if (message.getHashtagEntities().length < 1)
//			return "\'noHashtags\'";
//		else {
//			String hashtags = "";
//			for (HashtagEntity hashtag : tweet.getHashtagEntities()) {
//				hashtags += hashtag.getText() + " ";
//			}
//			hashtags = hashtags.replaceAll("\'", "").trim();
//			hashtags += "\'";
//			return "\'" + hashtags;
//		}
		return "TODO";
	}
	
}
