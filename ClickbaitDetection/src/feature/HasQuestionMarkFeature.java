package feature;

import message.Message;
import twitter4j.Status;
import util.TextUtil;
import corpora.*;

public class HasQuestionMarkFeature extends Feature {
	public String getArffHeader(){
		return "hasQuestionMark {yes,no}";
	}
	public String getCVSHeader(){
		return "hasQuestionMark";
	}
	public String getFeature(Status tweet) {
		String tweetText=TextUtil.cleanText(tweet.getText());
		if (tweetText.contains("?"))
			return "yes";
		else
			return "no";		
	}
	@Override
	public String getFeature(Message message) {
		String tweetText=TextUtil.cleanText(message.getText());
		if (tweetText.contains("?"))
			return "yes";
		else
			return "no";
	}
}
