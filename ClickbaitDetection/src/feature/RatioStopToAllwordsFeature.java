package feature;

import message.Message;
import twitter4j.Status;

public class RatioStopToAllwordsFeature extends Feature{
	public String getArffHeader(){
		return "StopWordRatio numeric";
		
	}
	public String getCVSHeader(){
		return "StopWordRatio";
	}
	public String getFeature(Status tweet){
		double ratio =1;
		double amountOfWords=(double)Integer.valueOf(new WordCounterFeature().getFeature(tweet));
		double amountOfNonStopWords=(double)Integer.valueOf(new NonStopWordCounterFeature().getFeature(tweet));
		
		ratio=amountOfNonStopWords/amountOfWords;
		return String.valueOf(ratio);
	}
	@Override
	public String getFeature(Message message) {
		double ratio =1;
		double amountOfWords=(double)Integer.valueOf(new WordCounterFeature().getFeature(message));
		double amountOfNonStopWords=(double)Integer.valueOf(new NonStopWordCounterFeature().getFeature(message));
		
		ratio=amountOfNonStopWords/amountOfWords;
		return String.valueOf(ratio);
	}
}
