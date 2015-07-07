package feature;

import message.Message;
import twitter4j.Status;
import corpora.*;

public class HasQuestionMarkFeature extends Feature {
	public String getArffHeader(){
		return "hasQuestionMark {yes,no}";
	}
	public String getCVSHeader(){
		return "hasQuestionMark";
	}
	public String getFeature(Status tweet) {
		String tweetText=TweetUtil.cleanTweetText(tweet.getText());
		if (tweetText.contains("?"))
			return "yes";
		else
			return "no";		
	}
	@Override
	public String getFeature(Message message) {
		// TODO Auto-generated method stub
		return null;
	}
}
