package feature;

import twitter4j.Status;
import twitterReader.TweetUtil;

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
}
