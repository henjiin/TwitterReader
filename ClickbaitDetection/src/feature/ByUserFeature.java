package feature;

import message.Message;
import twitter4j.Status;

public class ByUserFeature extends Feature{
	public String getArffHeader()  {
		return "By_User String";
	}
	public String getCVSHeader(){
		return "By_User";
	}
	
	public String getFeature(String text){
		return "?";
	}
	public String getFeature(Status tweet){
		String name=tweet.getUser().getName();
		name=name.replaceAll(" ", "");
		return "\'"+name+"\'";
	}
	
	public String getFeature(Message message){
		String name=message.getCreator();
		name=name.replaceAll(" ", "");
		return "\'"+name+"\'";
	}
	
	
}
