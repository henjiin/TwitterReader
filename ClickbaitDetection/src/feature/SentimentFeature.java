package feature;

import nlp.NLP;
import twitter4j.Status;
import util.TextUtil;
import learning.*;
import message.Message;
import corpora.*;
public class SentimentFeature extends Feature {
	
	private NLP nlp;
	public SentimentFeature() {
		
		nlp = new NLP();
		nlp.init();
	}
	
public String getArffHeader(){
	return "sentiment {verynegative,negative,neutral,positive,verypositve}";
}
public String getCVSHeader(){
	return "sentiment";
}

public String getFeature(Status tweet){
	String tweetText=TextUtil.cleanText(tweet.getText());
	int sentiment = nlp.findSentiment(tweetText);
	switch (sentiment) {
	case 0:
		return "verynegative";
	case 1:
		return "negative";
	case 2:
		return "neutral";
	case 3:
		return "positive";
	case 4:
		return "verypositve";
	default:
		return "neutral";
	}
}

@Override
public String getFeature(Message message) {
	String tweetText=TextUtil.cleanText(message.getText());
	int sentiment = nlp.findSentiment(tweetText);
	switch (sentiment) {
	case 0:
		return "verynegative";
	case 1:
		return "negative";
	case 2:
		return "neutral";
	case 3:
		return "positive";
	case 4:
		return "verypositve";
	default:
		return "neutral";
	}

}
}

