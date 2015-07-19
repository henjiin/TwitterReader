package feature;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import message.Message;
import twitter4j.Status;

public class ContainsGroupFeature extends Feature {
	String name;
	List<String> list;
	String pathToList;
	boolean stringContainsMode = false;
	boolean returnDelimiter=false;
	public ContainsGroupFeature(String name, String pathToList)
			throws IOException {
		this.name = name;
		this.pathToList = pathToList;
		fillList();
	}

	public ContainsGroupFeature(String name, List<String> list) {
		this.name = name;
		this.list = list;

	}

	public ContainsGroupFeature(String name, String[] array) {
		this.name = name;
		this.list = Arrays.asList(array);

	}

	public void applyStringContainsMode() {
		stringContainsMode = true;
	}
	
	public void setReturnDelimiter(boolean returnDelimiter){
		this.returnDelimiter=returnDelimiter;
	}
	

	private void fillList() throws IOException {
		list = Files.readAllLines(Paths.get(pathToList),
				Charset.defaultCharset());	
		for(String entry: list){
			entry=entry.toLowerCase();			
		}
	}

	public String getArffHeader() {
		return name + " {yes,no}";
	}

	public String getCVSHeader() {
		return name;
	}

	public String getFeature(Status tweet) {
		String tweetText = tweet.getText();
		if (stringContainsItemFromList(tweetText))
			return "yes";
		else
			return "no";
	}
	public String getFeature(Message message) {
		String messageText = message.getText();
		if (stringContainsItemFromList(messageText))
			return "yes";
		else
			return "no";
	}
	

	public boolean stringContainsItemFromList(String text) {
		StringTokenizer tokenizer = new StringTokenizer(text.toLowerCase(),
				" -_.,?!'’", returnDelimiter);
		String token;
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			System.out.println(token);
			if (!stringContainsMode) {
				for(String listItem:list){
				if (listItem.equalsIgnoreCase(token))
					return true;
				}
			}
			else{
				for(String listItem:list){
					if(listItem.toLowerCase().contains(token))
						return true;
				}
			}
		}
		return false;
	}

}
