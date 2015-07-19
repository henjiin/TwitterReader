package feature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import message.Message;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

public class MentionedUsersFeature extends Feature{
	String twitterUserPatternString="@\\w{1,15}";
	Pattern twitterUserPattern;
	public MentionedUsersFeature(){
		twitterUserPattern=Pattern.compile(twitterUserPatternString);
		
	}
	
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
			String mentionedUserNames="";
			for(UserMentionEntity mentionedUser:tweet.getUserMentionEntities()){
				String name=mentionedUser.getName();
				//Remove spaces, so each user is its own class
				name=name.replaceAll(" ", "");
				
				
				mentionedUserNames+=name+" ";
			}
			mentionedUserNames=mentionedUserNames.replaceAll("\'","").trim();
			mentionedUserNames+="\'";
			return "\'"+mentionedUserNames;
		}
	}
	
	public String getNeededType(){
		return "tweet";
	}
	
	public String getFeature(Message message) {
		/*
		if(message.getUserMentionEntities().length<1)
			return "\'noMentionedUsers\'";
			else{
				String mentionedUserNames="";
				for(UserMentionEntity mentionedUser:tweet.getUserMentionEntities()){
					String name=mentionedUser.getName();
					//Remove spaces, so each user is its own class
					name=name.replaceAll(" ", "");
					
					
					mentionedUserNames+=name+" ";
				}
				mentionedUserNames=mentionedUserNames.replaceAll("\'","").trim();
				mentionedUserNames+="\'";
				return "\'"+mentionedUserNames;
			}*/
		
		Matcher matcher = twitterUserPattern.matcher(message.getText());
		
		
		if (matcher.find())
		{
		    return(matcher.group(0));
		}
		return "noMentionedUsers";
		}		
	}

