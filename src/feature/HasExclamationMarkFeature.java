package feature;

public class HasExclamationMarkFeature extends Feature {
	public String getArffHeader() {
		return "hasExclamationMark {yes,no}";
	}
	public String getCVSHeader(){
		return "hasExclamationMark";
	}
	public String getFeature(String text) {
		if (text.contains("!"))
			return "yes";
		else
			return "no";
	}
	
}
