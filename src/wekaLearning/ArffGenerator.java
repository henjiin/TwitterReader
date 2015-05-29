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
	static boolean removeRT = false;
	static boolean removeTwitterUsers = true;
	static String arffFileName;
	static String HEADER = "@RELATION clickibait_detection\n\n@ATTRIBUTE tweet_text String\n@ATTRIBUTE hasExclamationMark numeric\n@ATTRIBUTE hasQuestionMark numeric\n@ATTRIBUTE amountOfDots numeric\n@ATTRIBUTE clickbait_class {clickbait,serious}\n\n@data\n";
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
		List fileList = new LinkedList<String>();

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
			return "1";
		else
			return "0";
	}

	public static String hasExclamationMark(String text) {
		if (text.contains("!"))
			return "1";
		else
			return "0";
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
	
	public static int isSelfRefering(String text){
		text=text.toLowerCase();
		if(text.contains("i")||text.contains("me")||text.contains("mine")||text.contains("we")||text.contains("our"))
		return 1;
		return 0;
	}
	
	public static int isReferingToReader(String text){
		text=text.toLowerCase();
		if(text.contains("you")||text.contains("your")||text.contains("yours")||text.contains("yourselfes")||text.contains("our"))
		return 1;
		return 0;
		
	}
	public static int startsWithNumber(String text){
		StringTokenizer tokenizer=new StringTokenizer(" ");
		String token=tokenizer.nextToken();
		try{
			Integer.parseInt(token);
			return 1;
		}catch(NumberFormatException e){
			return 0;
		}
	}

	public static void writeArff(String filename, String learningClass) {
		List<String> list = getJSONFileList(filename);

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, true)));
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
				objectAttributes.append(learningClass);
				objectAttributes.append("\n");
				writer.write(objectAttributes.toString());

			} catch (IOException | TwitterException e) {
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
		tweetText = tweetText.replaceAll("'", "");
		if (removeLinks)
			tweetText = removeUrl(tweetText);

		if (removeTwitterUsers)
			tweetText = removeTwitterUser(tweetText);
		return tweetText;

	}

}
