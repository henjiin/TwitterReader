package feature;

import message.Message;
import twitter4j.Status;
import util.TextUtil;
import corpora.*;

public class DotCounterFeature extends Feature{

	public String getArffHeader() {		
		return "amountOfDots numeric";
	}
	public String getCVSHeader(){
		return "amountOfDots";
	}
	
	public String getFeature(Status tweet) {
		String tweetText=TextUtil.cleanText(tweet.getText());
		int counter = 0;
		for (int i = 0; i < tweetText.length(); i++) {
			if (tweetText.charAt(i) == '.') {
				counter++;
			}
			if (tweetText.charAt(i) == '…') {
				counter += 3;
			}
		}
		return String.valueOf(counter);			
	}
	
	public String getFeature(Message message) {
		String tweetText=TextUtil.cleanText(message.getText());
		int counter = 0;
		for (int i = 0; i < tweetText.length(); i++) {
			if (tweetText.charAt(i) == '.') {
				counter++;
			}
			if (tweetText.charAt(i) == '…') {
				counter += 3;
			}
		}
		return String.valueOf(counter);			
	}
}
