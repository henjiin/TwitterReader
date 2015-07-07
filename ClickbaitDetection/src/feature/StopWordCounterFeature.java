package feature;


import message.Message;
import twitter4j.Status;

public class StopWordCounterFeature extends Feature {
	public String getArffHeader(){
		return "AmountOfStopWords numeric";
		
	}
	public String getCVSHeader(){
		return "AmountOfStopWords";
	}
	public String getFeature(Status tweet){
		int count =0;
		int amountOfWords=Integer.valueOf(new WordCounterFeature().getFeature(tweet));
		int amountOfNonStopWords=Integer.valueOf(new NonStopWordCounterFeature().getFeature(tweet));
		
		count=amountOfWords-amountOfNonStopWords;
		return String.valueOf(count);
	}
	@Override
	public String getFeature(Message message) {
		int count =0;
		int amountOfWords=Integer.valueOf(new WordCounterFeature().getFeature(message));
		int amountOfNonStopWords=Integer.valueOf(new NonStopWordCounterFeature().getFeature(message));
		
		count=amountOfWords-amountOfNonStopWords;
		return String.valueOf(count);
	}
}
