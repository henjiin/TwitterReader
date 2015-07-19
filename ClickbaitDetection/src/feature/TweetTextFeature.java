package feature;

import message.Message;
import twitter4j.Status;
import util.TextUtil;
import corpora.*;
public class TweetTextFeature extends Feature{
	char textDelimiter;
	public TweetTextFeature() {
		textDelimiter='\"';
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
	
	public String getFeature(Status tweet) {
		String tweetText=TextUtil.cleanText(tweet.getText());
		return textDelimiter+tweetText+textDelimiter;		
	}
	@Override
	public String getFeature(Message message) {
		String tweetText=TextUtil.cleanText(message.getText());
		return textDelimiter+tweetText+textDelimiter;		
	}
	
}
