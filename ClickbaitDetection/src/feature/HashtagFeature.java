package feature;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import message.Message;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

public class HashtagFeature  extends Feature{
	public static String hashtagRegex = "#\\w+|\\s#\\w+";
	public static Pattern hashtagPattern = Pattern.compile(hashtagRegex);


	public String getArffHeader() {
		return "Hashtags String";
	}
	public String getCVSHeader(){
		return "Hashtags";
	}

	
	
	@Override
	public String getFeature(Message message) {
		String hashtags="";
		StringTokenizer tokenizer = new StringTokenizer(message.getText());
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.startsWith("#")) {
                hashtags+=token.substring(1)+" ";
            }
        }       
        hashtags=hashtags.trim();
        if(hashtags.equals("")) return "'none'";
        return "'"+hashtags+"'";
	}
	
	

}
