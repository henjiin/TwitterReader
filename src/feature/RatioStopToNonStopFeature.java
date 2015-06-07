package feature;

import twitter4j.Status;

public class RatioStopToNonStopFeature extends Feature{
	public String getArffHeader(){
		return "StopWordRatio numeric";
		
	}
	public String getCVSHeader(){
		return "StopWordRatio";
	}
	public String getFeature(Status tweet){
		double ratio =1;
		double amountOfWords=(double)Integer.valueOf(new AmountOfWordsFeature().getFeature(tweet));
		double amountOfNonStopWords=(double)Integer.valueOf(new AmountOfNonStopWords().getFeature(tweet));
		
		ratio=amountOfNonStopWords/amountOfWords;
		return String.valueOf(ratio);
	}
}
