package feature;

import java.io.IOException;
import java.util.StringTokenizer;

import twitter4j.Status;
import twitterReader.TweetUtil;

public class AmountOfStopWords extends Feature {
	public String getArffHeader(){
		return "AmountOfStopWords numeric";
		
	}
	public String getCVSHeader(){
		return "AmountOfStopWords";
	}
	public String getFeature(Status tweet){
		int count =0;
		int amountOfWords=Integer.valueOf(new AmountOfWordsFeature().getFeature(tweet));
		int amountOfNonStopWords=Integer.valueOf(new AmountOfNonStopWords().getFeature(tweet));
		
		count=amountOfWords-amountOfNonStopWords;
		return String.valueOf(count);
	}
}
