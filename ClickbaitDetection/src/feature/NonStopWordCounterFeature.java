package feature;

import java.io.IOException;
import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;
import util.TextUtil;
import corpora.*;

public class NonStopWordCounterFeature extends Feature {
	public String getArffHeader() {
		return "AmountOfNonStopWords numeric";

	}

	public String getCVSHeader() {
		return "AmountOfNonStopWords";
	}

	public String getFeature(Status tweet) {
		String tweetText = "";
		try {
			tweetText = TextUtil.cleanStopWords(tweet.getText());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count = 0;
		StringTokenizer tokenizer = new StringTokenizer(tweetText);
		while (tokenizer.hasMoreTokens()) {
			tokenizer.nextToken();
			count++;
		}
		return String.valueOf(count);
	}

	@Override
	public String getFeature(Message message) {
		String messageText = "0";

		try {
			messageText = TextUtil.cleanStopWords(message.getText());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getFeature(messageText);
		
	}

	public String getFeature(String text) {
		int count = 0;
		StringTokenizer tokenizer = new StringTokenizer(text);
		while (tokenizer.hasMoreTokens()) {
			tokenizer.nextToken();
			count++;
		}
		return String.valueOf(count);
	}
}
