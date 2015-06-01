package wekaLearning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

public class ArffGenerator {

	static boolean removeLinks = true;
	static boolean removeRT = true;
	static boolean removeTwitterUsers = true;
	
	static String arffFileName;
	static String HEADER = "@RELATION clickibait_detection\n\n"
			+ "@ATTRIBUTE clickbait_class {clickbait,serious}\n"
			+ "@ATTRIBUTE tweet_text String\n"
			+ "@ATTRIBUTE hasExclamationMark {yes,no}\n"
			+ "@ATTRIBUTE hasQuestionMark {yes,no}\n"
			+ "@ATTRIBUTE amountOfDots numeric\n"
			+ "@ATTRIBUTE isSelfRefering {yes,no}\n"
			+ "@ATTRIBUTE refersToReader {yes,no}\n"
			+ "@ATTRIBUTE starts_with_number {yes,no}\n"
			+ "@ATTRIBUTE isEmotional {yes,no}\n"
			+ "@ATTRIBUTE sentiment {verynegative,negative,neutral,positive,verypositve}\n"
			+ "\n"
			+ "@data\n";
	static String urlPattern = "((https?|ftp|file|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	// TODO: To be refined
	static String userPattern = "@([A-Za-z0-9_]+)";
	static Pattern URLpattern = Pattern.compile(urlPattern,
			Pattern.CASE_INSENSITIVE);
	static Pattern userPatter = Pattern.compile(userPattern);
	
	private static String removeUrl(String string) {

		Matcher m = URLpattern.matcher(string);
		int i = 0;
		while (m.find()) {
			string = string.replaceAll(m.group(i), "").trim();
			i++;
		}
		string.replaceAll("@", "");
		return string;
	}

	private static String removeTwitterUser(String string) {
		try {
			Matcher m = userPatter.matcher(string);
			int i = 0;
			while (m.find()) {
				string = string.replaceAll(m.group(i), "").trim();
				i++;
			}
			string=string.replaceAll("@", "");
			return string;			
		} catch (IndexOutOfBoundsException e) {
			return string;
		}

	}

	private static String readFirstLine(String fileName) throws IOException {		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(fileName);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			return br.readLine();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ignore) {
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException ignore) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	static Status getTweetFromJSON(String fileName) throws IOException,
			TwitterException {
		String rawJSON = readFirstLine(fileName);
		Status status = TwitterObjectFactory.createStatus(rawJSON);
		
		
		return status;
	}

	static List<String> getJSONFileList(String pathToFolder) {
		List<String> fileList = new LinkedList<String>();

		File[] files = new File(pathToFolder).listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".json");
			}
		});
		for (File file : files) {
			fileList.add(file.toString());
		}

		return fileList;
	}

	public static String hasQuestionMark(String text) {
		if (text.contains("?"))
			return "yes";
		else
			return "no";
	}

	public static String hasExclamationMark(String text) {
		if (text.contains("!"))
			return "yes";
		else
			return "no";
	}

	public static int amountOfDots(String text) {
		int counter = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '.') {
				counter++;
			}
			if (text.charAt(i) == '…') {
				counter += 3;
			}
		}
		return counter;
	}
	
	public static String isSelfRefering(String text){
		text=text.toLowerCase();
		if(text.contains(" i ")||
			text.contains(" me ")||
			text.contains(" mine ")||
			text.contains(" we ")||
			text.contains(" our "))
		return "yes";
		return "no";
	}
	
	public static String isReferingToReader(String text){
		text=text.toLowerCase();
		if(text.contains(" you ")||
				text.contains(" your ")||
				text.contains(" yours ")||
				text.contains(" yourselfes ")||
				text.contains(" our "))
		return "yes";
		return "no";
		
	}
	public static String startsWithNumber(String text){
		StringTokenizer tokenizer=new StringTokenizer(text," ");
		String token=tokenizer.nextToken();
		try{
			Integer.parseInt(token);
			return "yes";
		}catch(NumberFormatException e){
			return "no";
		}
	}
	
	public static String getSentiment(SWN3 swn, String text){
	    
	        String[] words = text.split("\\s+"); 
	        double totalScore = 0, averageScore = 0;
	        for(String word : words) {
	            word = word.replaceAll("([^a-zA-Z\\s])", "");
	            if (swn.extract(word) == null)
	                continue;
	            totalScore += swn.extract(word);
	        }
	        averageScore = totalScore/words.length;

	        if(averageScore>=0.75)
	            return "very_positve";
	        else if(averageScore > 0.25 && averageScore<0.5)
	            return  "positve";
	        else if(averageScore>=0.5)
	            return  "positve";
	        else if(averageScore < 0 && averageScore>=-0.25)
	            return "negative";
	        else if(averageScore < -0.25 && averageScore>=-0.5)
	            return "negative";
	        else if(averageScore<=-0.75)
	            return "very_negative";
	        return "neutral";
	    }
	public static String getStanfordSentiment(NLP nlp, String text){		
		int sentiment=nlp.findSentiment(text);				
		switch (sentiment) {
		case 0:
			return "verynegative";			
		case 1:
			return "negative";
		case 2:
			return "neutral";
		case 3:
			return "positive";
		case 4:
			return "verypositve";
		default:
			return "neutral";
		}
	}
	
	public static String isEmotional(String text){
		text=text.toLowerCase();
		if(		text.contains("awesome")||
				text.contains("unbelievable")||
				text.contains("terrifying")||
				text.contains("worst")||
				text.contains("best")||
				text.contains("weird")||
				text.contains("awful")||
				text.contains("perfect")||
				text.contains("overwhelming")||
				text.contains("magic")||
				text.contains("cruel")||
				text.contains("confuse")||
				text.contains("happpy")||
				text.contains("delicious")||
				text.contains("priceless")||
				text.contains("clever")||
				text.contains("faith")||
				text.contains("hot")||
				text.contains("desperat")||
				text.contains("desperat")				
				)return "yes";
		return "no";		
	}
	

	public static void writeArff(String filename, String learningClass) {
		List<String> list = getJSONFileList(filename);
		SWN3 swn=null;
		PrintWriter writer = null;
		NLP nlp = new NLP();
		nlp.init();
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, true)));
			swn=new SWN3("/home/sebastiankopsel/Data/swn.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String tweetJSON : list) {
			try {
				Status tweet = getTweetFromJSON(tweetJSON);
				StringBuilder objectAttributes = new StringBuilder();
				String tweetText = tweet.getText();
				tweetText = cleanTweetText(tweetText);
				objectAttributes.append("\'");
				objectAttributes.append(tweetText);
				objectAttributes.append("\',");
				objectAttributes.append(hasExclamationMark(tweetText));
				objectAttributes.append(",");
				objectAttributes.append(hasQuestionMark(tweetText));
				objectAttributes.append(",");
				objectAttributes.append(amountOfDots(tweetText));
				objectAttributes.append(",");
				objectAttributes.append(isSelfRefering(tweetText));
				objectAttributes.append(",");
				objectAttributes.append(isReferingToReader(tweetText));
				objectAttributes.append(",");
				objectAttributes.append(startsWithNumber(tweetText));
				objectAttributes.append(",");
				objectAttributes.append(isEmotional(tweetText));				
				objectAttributes.append(",");
				objectAttributes.append(getStanfordSentiment(nlp, tweetText));
				objectAttributes.append(",");

				
				objectAttributes.append(learningClass);
				objectAttributes.append("\n");
				
				writer.write(objectAttributes.toString());				
			} catch (IOException | TwitterException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.flush();
		writer.close();

	}

	public static void main(String[] args) {
		arffFileName = "/home/sebastiankopsel/Data/Serious/clickbait.arff";
		writeHeader();
		writeArff("/home/sebastiankopsel/Data/Serious/clickbait", "clickbait");
		writeArff("/home/sebastiankopsel/Data/Serious/serious", "serious");
		
		
		System.out.println("Finished");
	}

	private static void writeHeader() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, false)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(HEADER);
		writer.flush();
		writer.close();
	}

	private static String cleanTweetText(String tweetText) {
		tweetText = tweetText.trim();
		tweetText = tweetText.replaceAll("\n", "").replaceAll("\r", "");
		tweetText = tweetText.replaceAll("'", " ");
		
		if (removeLinks)
			tweetText = removeUrl(tweetText);

		if (removeTwitterUsers)
			tweetText = removeTwitterUser(tweetText);
		
		if (removeRT)
			tweetText = removeReTweet(tweetText);
		return tweetText;

	}

	private static String removeReTweet(String tweetText) {
		tweetText = tweetText.replaceAll("RT :", "");
		tweetText = tweetText.replaceAll("RT:", "");
		
		tweetText = tweetText.replaceAll("RT", "");
		
		tweetText=tweetText.trim();
		return tweetText;
	}

}
