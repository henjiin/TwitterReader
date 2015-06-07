package feature;

import java.io.IOException;
import java.util.StringTokenizer;

import twitter4j.Status;
import twitterReader.TweetUtil;

public class AmountOfNonStopWords extends Feature {
	public String getArffHeader(){
		return "AmountOfNonStopWords numeric";
		
	}
	public String getCVSHeader(){
		return "AmountOfNonStopWords";
	}
	public String getFeature(Status tweet){
		String tweetText = "";
		try {
			tweetText = TweetUtil.cleanStopWords(tweet.getText());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count=0;
		StringTokenizer tokenizer = new StringTokenizer(tweetText);
		while(tokenizer.hasMoreTokens()){
			tokenizer.nextToken();
			count++;
		}
		return String.valueOf(count);
	}
}
