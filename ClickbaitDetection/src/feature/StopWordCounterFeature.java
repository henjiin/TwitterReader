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
	
	@Override
	public String getFeature(Message message) {
		int count =0;
		int amountOfWords=Integer.valueOf(new WordCounterFeature().getFeature(message));
		int amountOfNonStopWords=Integer.valueOf(new NonStopWordCounterFeature().getFeature(message));
		
		count=amountOfWords-amountOfNonStopWords;
		return String.valueOf(count);
	}
	
	public String getFeature(String text){
		int count =0;
		int amountOfWords=Integer.valueOf(new WordCounterFeature().getFeature(text));
		int amountOfNonStopWords=Integer.valueOf(new NonStopWordCounterFeature().getFeature(text));
		
		count=amountOfWords-amountOfNonStopWords;
		return String.valueOf(count);
	}
	
	
}
