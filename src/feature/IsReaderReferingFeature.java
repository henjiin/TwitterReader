package feature;

public class IsReaderReferingFeature extends Feature{

	static String[]	readerReferingWords={" you "," your "," yours "," yourselfes "," our "};
	public String getArffHeader(){
		return "refersToReader {yes,no}";
	}
	public String getFeature(String text){
		text = text.toLowerCase();
		if (stringContainsItemFromList(text, readerReferingWords))
			return "yes";
		return "no";
	}
	public static String[] getSelfReferingWords() {
		return readerReferingWords;
	}
	public static void setSelfReferingWords(String[] readerReferingWords) {
		IsReaderReferingFeature.readerReferingWords = readerReferingWords;
	}
		
}
