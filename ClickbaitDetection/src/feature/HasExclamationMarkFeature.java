package feature;

import message.Message;
import twitter4j.Status;
import corpora.*;

public class HasExclamationMarkFeature extends Feature {
	public String getArffHeader() {
		return "hasExclamationMark {yes,no}";
	}
	public String getCVSHeader(){
		return "hasExclamationMark";
	}
	public String getFeature(Status tweet) {
		String tweetText=TweetUtil.cleanTweetText(tweet.getText());
		if (tweetText.contains("!"))
			return "yes";
		else
			return "no";
	}
	@Override
	public String getFeature(Message message) {
		String tweetText=TweetUtil.cleanTweetText(message.getText());
		if (tweetText.contains("!"))
			return "yes";
		else
			return "no";
	}
	
}
