package feature;
import message.Message;
import twitter4j.Status;
public class TweetFullLengthFeature extends Feature{
	
		public String getArffHeader()  {
			return "TweetFullLength numeric";
		}
		public String getCVSHeader(){
			return "TweetFullLength";
		}
		
		
		public String getFeature(Status tweet){
			return String.valueOf(tweet.getText().length());
			
		}
		@Override
		public String getFeature(Message message) {
			return String.valueOf(message.getText().length());
		}	

}
