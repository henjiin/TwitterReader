package feature;

import java.util.StringTokenizer;

import twitter4j.Status;
import twitterReader.TweetUtil;

public class AmountOfWordsFeature extends Feature {
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
}
