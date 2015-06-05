package feature;

public abstract class Feature {

	public String getArffHeader() {
		return "";
	}
	public String getCVSHeader(){
		return "";
	}
	
	public String getFeature(String text){
		return "";
	}
	
	public static boolean stringContainsItemFromList(String inputString,
			String[] items) {
		for (int i = 0; i < items.length; i++) {
			if (inputString.contains(items[i])) {
				return true;
			}
		}
		return false;
	}
	
	
}
