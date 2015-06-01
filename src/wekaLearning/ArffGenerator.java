package wekaLearning;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitterReader.TweetUtil;

public class ArffGenerator {

	static boolean removeLinks = true;
	static boolean removeRT = true;
	static boolean removeTwitterUsers = true;
	public static String arfFileName;
	static String arffFileName;
	static NLP nlp = null;
	static SWN3 swn = null;
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
			+ "\n" + "@data\n";
	static String[] emotionalWords = { "awesome", "unbelievable", "terrifying",
			"worst", "best", "weird", "awful", "perfect", "overwhelming",
			"magic", "cruel", "confuse", "happy", "delicious", "priceless",
			"clever", "faith", "hot", "desperat", "desperat" };
	static String[] selfReferingWords={" i "," me "," mine "," we "," our "};
	static String[] readerReferingWords={" you "," your "," yours "," yourselfes "," our "};
	public static boolean stringContainsItemFromList(String inputString,
			String[] items) {
		for (int i = 0; i < items.length; i++) {
			if (inputString.contains(items[i])) {
				return true;
			}
		}
		return false;
	}
	
	
	public static void init() {
		
		nlp = new NLP();
		nlp.init();
		try {
			swn = new SWN3("/home/sebastiankopsel/Data/swn.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static String isSelfRefering(String text) {
		text = text.toLowerCase();
		if (stringContainsItemFromList(text, selfReferingWords))
			return "yes";
		return "no";
	}

	public static String isReferingToReader(String text) {
		text = text.toLowerCase();
		if (stringContainsItemFromList(text, readerReferingWords))
			return "yes";
		return "no";
	}

	public static String startsWithNumber(String text) {
		StringTokenizer tokenizer = new StringTokenizer(text, " ");
		String token = tokenizer.nextToken();
		try {
			Integer.parseInt(token);
			return "yes";
		} catch (NumberFormatException e) {
			return "no";
		}
	}

	public static String getSentiment(String text) {

		String[] words = text.split("\\s+");
		double totalScore = 0, averageScore = 0;
		for (String word : words) {
			word = word.replaceAll("([^a-zA-Z\\s])", "");
			if (swn.extract(word) == null)
				continue;
			totalScore += swn.extract(word);
		}
		averageScore = totalScore / words.length;

		if (averageScore >= 0.75)
			return "verypositve";
		else if (averageScore > 0.25 && averageScore < 0.5)
			return "positive";
		else if (averageScore >= 0.5)
			return "positive";
		else if (averageScore < 0 && averageScore >= -0.25)
			return "negative";
		else if (averageScore < -0.25 && averageScore >= -0.5)
			return "negative";
		else if (averageScore <= -0.75)
			return "verynegative";
		return "neutral";
	}

	public static String getStanfordSentiment(NLP nlp, String text) {
		int sentiment = nlp.findSentiment(text);
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

	public static String isEmotional(String text) {
		text = text.toLowerCase();
		boolean isEmotional=stringContainsItemFromList(text, emotionalWords);
		if (isEmotional) return "yes";
		else return "no";
	}

	private static String buildObject(Status tweet, String learningClass) {
		String tweetText = tweet.getText();
		tweetText = TweetUtil.cleanTweetText(tweetText);
		StringBuilder objectAttributes = new StringBuilder();
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
		return objectAttributes.toString();
	}

	public static void writeArff(String filename, String learningClass) {
		List<String> list = TweetUtil.getJSONFileList(filename);

		PrintWriter writer = null;
		
		
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, true)));

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		for (String tweetJSON : list) {
			try {
				Status tweet = TweetUtil.getTweetFromJSON(tweetJSON);
				String object = buildObject(tweet, learningClass);
				writer.write(object.toString());
			} catch (IOException | TwitterException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.flush();
		writer.close();

	}

	public static void main(String[] args) {
		init();
		arffFileName = "/home/sebastiankopsel/Data/Serious/clickbait.arff";
		writeHeader();
		writeArff("/home/sebastiankopsel/Data/Serious/clickbait", "clickbait");
		writeArff("/home/sebastiankopsel/Data/Serious/serious", "serious");

		System.out.println("Finished");
	}

}
