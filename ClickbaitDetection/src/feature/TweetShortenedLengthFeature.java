package feature;
import message.Message;
import corpora.*;
import twitter4j.Status;
import util.TextUtil;
public class TweetShortenedLengthFeature extends Feature{
	
		public String getArffHeader()  {
			return "TweetShortLength numeric";
		}
		public String getCVSHeader(){
			return "TweetShortLength";
		}
		
		
		public String getFeature(Status tweet){
			return String.valueOf(TextUtil.cleanText(tweet.getText()).length());
			
		}
		@Override
		public String getFeature(Message message) {
			return String.valueOf(TextUtil.cleanText(message.getText()).length());
		}	

}
