package feature;

import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;

public class HasNumberFeature extends Feature{
	
	public String getArffHeader() {
		return "HasNumber {yes,no}";
	}
	public String getCVSHeader(){
		return "HasNumber";
	}
	
	public String getFeature(String text){
		StringTokenizer tokenizer = new StringTokenizer(text, " ");
		String token="";
		while(tokenizer.hasMoreTokens())
		try {
			token= tokenizer.nextToken();
			Integer.parseInt(token);
			return "yes";
		} catch (NumberFormatException e) {}
		return "no";
	}
	@Override
	public String getFeature(Status tweet) {
		String text=tweet.getText();
		StringTokenizer tokenizer = new StringTokenizer(text, " ");
		String token="";
		while(tokenizer.hasMoreTokens())
		try {
			token= tokenizer.nextToken();
			Integer.parseInt(token);
			return "yes";
		} catch (NumberFormatException e) {}
		return "no";
	}
	@Override
	public String getFeature(Message message) {
		String text=message.getText();
		StringTokenizer tokenizer = new StringTokenizer(text, " ");
		String token="";
		while(tokenizer.hasMoreTokens())
		try {
			token= tokenizer.nextToken();
			Integer.parseInt(token);
			return "yes";
		} catch (NumberFormatException e) {}
		return "no";
	}
	
	
}
