package feature;

import twitter4j.Status;
import twitterReader.TweetUtil;

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
	
}
