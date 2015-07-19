package feature;

import message.Message;
import twitter4j.Status;

public class MessageReadabilityFeature extends Feature{
	WordCounterFeature wordCounter= new WordCounterFeature();
	SentenceCounterFeature sentenceCounter = new SentenceCounterFeature();
	SyllablesCounterFeature syllablesCounter = new SyllablesCounterFeature();	
	
	
	public String getArffHeader(){
		return "Readability numeric";
	}
	public String getCVSHeader(){
		return "Readability";
	}
	
	@Override
	public String getFeature(Message message) {
		double wordCount=(double)Integer.valueOf(wordCounter.getFeature(message));
		double sentenceCount=(double) Integer.valueOf(sentenceCounter.getFeature(message));
		double syllablesCount=(double) Integer.valueOf(syllablesCounter.getFeature(message));
		double readingEase=206.835-1.015*(wordCount/sentenceCount)-84.6*(syllablesCount/wordCount);
		
		return String.valueOf(readingEase);
	}
	
}
