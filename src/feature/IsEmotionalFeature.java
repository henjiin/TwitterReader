package feature;

public class IsEmotionalFeature extends Feature {
	static String[] emotionalWords = { "awesome", "unbelievable", "terrifying",
		"worst", "best", "weird", "awful", "perfect", "overwhelming",
		"magic", "cruel", "confuse", "happy", "delicious", "priceless",
		"clever", "faith", "hot", "desperat", "desperat" };
	
	public String getArffHeader() {		
		return "isEmotional {yes,no}";
	}
	
	public String getFeature(String text) {
		int counter = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '.') {
				counter++;
			}
			if (text.charAt(i) == '…') {
				counter += 3;
			}
		}
		return String.valueOf(counter);	
		
	}
	public static String isEmotional(String text) {
		text = text.toLowerCase();
		boolean isEmotional=stringContainsItemFromList(text, emotionalWords);
		if (isEmotional) return "yes";
		else return "no";
	}
	
	public static String[] getEmotionalWords() {
		return emotionalWords;
	}
	
	public static void setEmotionalWords(String[] emotionalWords) {
		IsEmotionalFeature.emotionalWords = emotionalWords;
	}

}
