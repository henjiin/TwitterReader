package feature;

import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;

public class SyllablesCounterFeature extends Feature{
	public String getArffHeader() {
		return "Amount_of_Syllabus numeric";
	}
	public String getCVSHeader(){
		return "Amount_of_Syllabus";
	}
		
	public String getFeature(Status tweet){
		String tweetText=tweet.getText();
		int amountSyllables=0;
		StringTokenizer tokenizer=new StringTokenizer(tweetText);
		String word;
		while(tokenizer.hasMoreTokens()){
			word=tokenizer.nextToken();
			amountSyllables+=countSyllables(word);
		}
		
		return String.valueOf(amountSyllables);
	}
	
	//Shameless stolen from stackoverflow
	public int countSyllables(String word) {
	    int count = 0;
	    word = word.toLowerCase();
	    for (int i = 0; i < word.length(); i++) {
	        if (word.charAt(i) == '\"' || word.charAt(i) == '\'' || word.charAt(i) == '-' || word.charAt(i) == ',' || word.charAt(i) == ')' || word.charAt(i) == '(') {
	            word = word.substring(0,i)+word.substring(i+1, word.length());
	        }
	    }
	    boolean isPrevVowel = false;
	    for (int j = 0; j < word.length(); j++) {
	        if (word.contains("a") || word.contains("e") || word.contains("i") || word.contains("o") || word.contains("u")) {
	            if (isVowel(word.charAt(j)) && !((word.charAt(j) == 'e') && (j == word.length()-1))) {
	                if (isPrevVowel == false) {
	                    count++;
	                    isPrevVowel = true;
	                }
	            } else {
	                isPrevVowel = false;
	            }
	        } else {
	            count++;
	            break;
	        }
	    }
	    return count;
	}
	public boolean isVowel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            return true;
        } else {
            return false;
        }
    }
	@Override
	public String getFeature(Message message) {
		String messageText=message.getText();
		int amountSyllables=0;
		StringTokenizer tokenizer=new StringTokenizer(messageText);
		String word;
		while(tokenizer.hasMoreTokens()){
			word=tokenizer.nextToken();
			amountSyllables+=countSyllables(word);
		}
		
		return String.valueOf(amountSyllables);
	}


}
