package feature;

public class IsSelfReferingFeature extends Feature {
static String[]	selfReferingWords={" i "," me "," mine "," we "," our "};
public String getArffHeader(){
	return "isSelfRefering {yes,no}";
}
public String getFeature(String text){
	text = text.toLowerCase();
	if (stringContainsItemFromList(text, selfReferingWords))
		return "yes";
	return "no";
}
public static String[] getSelfReferingWords() {
	return selfReferingWords;
}
public static void setSelfReferingWords(String[] selfReferingWords) {
	IsSelfReferingFeature.selfReferingWords = selfReferingWords;
}
	
}
