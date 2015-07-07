package feature;

import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;
import corpora.*;
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
	@Override
	public String getFeature(Message message) {
		String tweetText=TweetUtil.cleanTweetText(message.getText());
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