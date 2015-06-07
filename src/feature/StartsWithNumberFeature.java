package feature;

import java.util.StringTokenizer;

import twitter4j.Status;
import twitterReader.TweetUtil;

public class StartsWithNumberFeature extends Feature {
	public String getArffHeader(){
		return "starts_with_number {yes,no}";
		
	}
	public String getCVSHeader(){
		return "starts with number";
	}
	public String getFeature(Status tweet){
		String tweetText=TweetUtil.cleanTweetText(tweet.getText());
		StringTokenizer tokenizer = new StringTokenizer(tweetText, " ");
		String token = tokenizer.nextToken();
		try {
			Integer.parseInt(token);
			return "yes";
		} catch (NumberFormatException e) {
			return "no";
		}
	}
}
