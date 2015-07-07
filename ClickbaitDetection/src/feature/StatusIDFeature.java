package feature;

import message.Message;
import twitter4j.Status;

public class StatusIDFeature extends Feature {
	public String getArffHeader()  {
		return "Status_ID numeric";
	}
	public String getCVSHeader(){
		return "Status_ID";
	}	
	
	public String getFeature(Status tweet){
		
		//String url= "=HYPERLINK(\"https://twitter.com/" + tweet.getUser().getScreenName() 
		//+ "/status/" + tweet.getId()+"\";\"link\")";
		
		return String.valueOf(tweet.getId());
	}
	@Override
	public String getFeature(Message message) {
		// TODO Auto-generated method stub
		return message.getStatusID();
	}
	
}
