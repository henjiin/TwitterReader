package feature;

import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;
import util.TextUtil;
import corpora.*;

public class WordCounterFeature extends Feature {
	public String getArffHeader() {
		return "Amount_of_words numeric";

	}

	public String getCVSHeader() {
		return "Amount_of_words";
	}

	public String getFeature(Status tweet) {
		String tweetText = TextUtil.cleanText(tweet.getText());
		return getFeature(tweetText);
	}

	@Override
	public String getFeature(Message message) {
		String messageText = TextUtil.cleanText(message.getText());		
		return getFeature(messageText);
	}
	
	public String getFeature(String text){		
		StringTokenizer tokenizer = new StringTokenizer(text);
		int count = 0;
		while (tokenizer.hasMoreTokens()) {			
			tokenizer.nextToken();
			count++;
		}
		return String.valueOf(count);
	}
	
}
