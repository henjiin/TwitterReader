package feature;

import twitter4j.Status;
import twitter4j.UserMentionEntity;

public class MentionedUsersFeature extends Feature{
	public String getArffHeader() {
		return "mentioned_users String";
	}
	public String getCVSHeader(){
		return "mentioned_users";
	}
	
	public String getFeature(String text){
		return "?";
	}
	public String getFeature(Status tweet){
		if(tweet.getUserMentionEntities().length<1)
		return "\'noMentionedUsers\'";
		else{
			String mentionedUserNames="\'";
			for(UserMentionEntity mentionedUser:tweet.getUserMentionEntities()){
				String name=mentionedUser.getName();
				//Remove spaces, so each user is its own class
				name=name.replaceAll(" ", "");
				
				
				mentionedUserNames+=name+" ";
			}
			mentionedUserNames=mentionedUserNames.trim();
			mentionedUserNames+="\'";
			return mentionedUserNames;
		}
	}
	
	public String getNeededType(){
		return "tweet";
	}
}
