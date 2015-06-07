package feature;

import java.util.StringTokenizer;

public class HasNumberFeature {
	
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
	
	
}
