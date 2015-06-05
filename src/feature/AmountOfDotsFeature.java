package feature;

public class AmountOfDotsFeature extends Feature{

	public String getArffHeader() {		
		return "amountOfDots numeric";
	}
	public String getCVSHeader(){
		return "amountOfDots";
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

	

}
