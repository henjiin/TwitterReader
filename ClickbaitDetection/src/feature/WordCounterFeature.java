package feature;

import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;
import corpora.*;
public class WordCounterFeature extends Feature {
	public String getArffHeader(){
		return "Amount_of_words numeric";
		
	}
	public String getCVSHeader(){
		return "Amount_of_words";
	}
	public String getFeature(Status tweet){
		String tweetText=TweetUtil.cleanTweetText(tweet.getText());
		StringTokenizer tokenizer = new StringTokenizer(tweetText);
		int count=0;
		while (tokenizer.hasMoreTokens()){
			tokenizer.nextToken();
			count++;
		}
		return String.valueOf(count);
	}
	@Override
	public String getFeature(Message message) {
		String messageText=TweetUtil.cleanTweetText(message.getText());
		StringTokenizer tokenizer = new StringTokenizer(messageText);
		int count=0;
		while (tokenizer.hasMoreTokens()){
			tokenizer.nextToken();
			count++;
		}
		return String.valueOf(count);
	}
}
