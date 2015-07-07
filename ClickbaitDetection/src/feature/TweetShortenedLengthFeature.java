package feature;
import message.Message;
import corpora.*;
import twitter4j.Status;
public class TweetShortenedLengthFeature extends Feature{
	
		public String getArffHeader()  {
			return "TweetShortLength numeric";
		}
		public String getCVSHeader(){
			return "TweetShortLength";
		}
		
		
		public String getFeature(Status tweet){
			return String.valueOf(TweetUtil.cleanTweetText(tweet.getText()).length());
			
		}
		@Override
		public String getFeature(Message message) {
			return String.valueOf(TweetUtil.cleanTweetText(message.getText()).length());
		}	

}
