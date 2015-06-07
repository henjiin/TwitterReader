package feature;

import twitter4j.Status;
import twitterReader.TweetUtil;

public class AmountOfDotsFeature extends Feature{

	public String getArffHeader() {		
		return "amountOfDots numeric";
	}
	public String getCVSHeader(){
		return "amountOfDots";
	}
	
	public String getFeature(Status tweet) {
		String tweetText=TweetUtil.cleanTweetText(tweet.getText());
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
