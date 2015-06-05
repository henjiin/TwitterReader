package feature;

public class HasQuestionMarkFeature extends Feature {
	public String getArffHeader(){
		return "hasQuestionMark {yes,no}";
	}
	public String getCVSHeader(){
		return "hasQuestionMark";
	}
	public String getFeature(String text) {
		if (text.contains("?"))
			return "yes";
		else
			return "no";		
	}
}
