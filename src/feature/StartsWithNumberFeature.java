package feature;

import java.util.StringTokenizer;

public class StartsWithNumberFeature extends Feature {
	public String getArffHeader(){
		return "starts_with_number {yes,no}";
		
	}
	public String getFeature(String text){
		StringTokenizer tokenizer = new StringTokenizer(text, " ");
		String token = tokenizer.nextToken();
		try {
			Integer.parseInt(token);
			return "yes";
		} catch (NumberFormatException e) {
			return "no";
		}
	}
}
