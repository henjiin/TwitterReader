package feature;

import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;
import util.TextUtil;
import corpora.*;

public class MaxWordLengthFeature extends Feature{
	public String getArffHeader() {
		return "Max_Word_Length numeric";
	}
	public String getCVSHeader(){
		return "Max_Word_Length";
	}
		
	public String getFeature(Status tweet){
		String tweetText=TextUtil.cleanText(tweet.getText());
		StringTokenizer tokenizer= new StringTokenizer(tweetText);
		String token;
		int maxWordLength=0;
		while(tokenizer.hasMoreTokens()){
			token=tokenizer.nextToken();
			if(token.length()>maxWordLength)
				maxWordLength=token.length();
		}
		return String.valueOf(maxWordLength);	
		
	}
	@Override
	public String getFeature(Message message) {
		String tweetText=TextUtil.cleanText(message.getText());
		StringTokenizer tokenizer= new StringTokenizer(tweetText);
		String token;
		int maxWordLength=0;
		while(tokenizer.hasMoreTokens()){
			token=tokenizer.nextToken();
			if(token.length()>maxWordLength)
				maxWordLength=token.length();
		}
		return String.valueOf(maxWordLength);
	}
}
