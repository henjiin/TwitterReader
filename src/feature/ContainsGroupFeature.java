package feature;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

import twitter4j.Status;

public class ContainsGroupFeature extends Feature {
	String name;
	List<String> list; 
	String pathToList;

	public ContainsGroupFeature(String name, String pathToList) throws IOException {
		this.name=name;
		this.pathToList=pathToList;
		fillList();
	}
	public ContainsGroupFeature(String name, List<String> list){
		this.name=name;
		this.list=list;
		
	}
	private void fillList() throws IOException{
		list = Files.readAllLines(Paths.get(pathToList),
                Charset.defaultCharset());
	}
	
	public String getArffHeader() {
		return name+" {yes,no}";
	}
	public String getCVSHeader(){
		return name;
	}
	
	public String getFeature(Status tweet){
		String tweetText=tweet.getText();
		if(stringContainsItemFromList(tweetText))
		return "yes";
		else return "no";
	}
	
	public String getNeededType(){
		return "tweet";
	}
	
	boolean stringContainsItemFromList(String text){
		StringTokenizer tokenizer= new StringTokenizer(text.toLowerCase());
		String token;
		while(tokenizer.hasMoreTokens()){
			token=tokenizer.nextToken();
			if(list.contains(token))
				return true;
		}
		return false;
	}

}
