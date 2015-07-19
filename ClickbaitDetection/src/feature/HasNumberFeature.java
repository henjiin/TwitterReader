package feature;

import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;

public class HasNumberFeature extends Feature{
	
	public String getArffHeader() {
		return "HasNumber {yes,no}";
	}
	public String getCVSHeader(){
		return "HasNumber";
	}
	@Override
	public String getFeature(Message message) {
		String text=message.getText();
		return getFeature(text);
	}
	
	public String getFeature(String text){
		//Matches against an number regex
		if(text.matches(".*\\d.*")){
			return "yes";
		}
		else return "no";
	}
	
}
