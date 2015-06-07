package feature;

import twitter4j.Status;
import twitterReader.TweetUtil;

public class TweetTextFeature extends Feature{
	char textDelimiter;
	public TweetTextFeature() {
		textDelimiter='\"';
		// TODO Auto-generated constructor stub
	}
	public void setTextDelimiter(char textDelimiter) {
		this.textDelimiter = textDelimiter;
	}
	@Override
	public String getArffHeader() {
		String header="tweet_text String";
		return header;
	}
	@Override
	public String getCVSHeader() {
		// TODO Auto-generated method stub
		return "tweet_text";
	}
	@Override
	public String getFeature(Status tweet) {
		String tweetText=TweetUtil.cleanTweetText(tweet.getText());
		return textDelimiter+tweetText+textDelimiter;		
	}
	
}
