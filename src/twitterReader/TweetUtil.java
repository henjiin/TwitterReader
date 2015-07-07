package twitterReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

public class TweetUtil {
	static String urlPattern = "((https?|ftp|file|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";

	static String userPattern = "@([A-Za-z0-9_]+)";
	static String retweetPattern = "RT " + userPattern + ":";
	static Pattern URLpattern = Pattern.compile(urlPattern,
			Pattern.CASE_INSENSITIVE);
	static Pattern userPatter = Pattern.compile(userPattern);
	static Pattern retweetPatter = Pattern.compile(retweetPattern);
	static String stopwords="/home/sebastiankopsel/Data/Other_Sources/stop-words-collection-2014-02-24/stop-words/stop-words_english_6_en.txt";
	public static String readFirstLine(String fileName) throws IOException {
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

	public static String removeTwitterUser(String string) {
		try {
			Matcher m = userPatter.matcher(string);
			int i = 0;
			while (m.find()) {
				
				string = string.replaceAll(m.group(i), "").trim();
				i++;
			}
			string = string.replaceAll("@", "");
			return string;
		} catch (IndexOutOfBoundsException e) {
			return string;
		}
	}

	public static String removeUrl(String string) {
		
		Matcher m = URLpattern.matcher(string);
		int i = 0;
		while (m.find()) {
			string = string.replaceAll(m.group(i), "").trim();
			i++;
		}		
		return string;
	}

	public static Status getTweetFromJSON(String fileName) throws IOException,
			TwitterException {
		String rawJSON = TweetUtil.readFirstLine(fileName);
		Status status = TwitterObjectFactory.createStatus(rawJSON);

		return status;
	}

	public static List<String> getJSONFileList(String pathToFolder) {
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

	public static String cleanTweetText(String tweetText) {
		tweetText = tweetText.trim();
		tweetText = tweetText.replaceAll("\n", "").replaceAll("\r", "");
		tweetText = tweetText.replaceAll("’", " ");
		tweetText = tweetText.replaceAll("'s", " is");
		tweetText = tweetText.replaceAll("'", "");
		tweetText = tweetText.replace("\"", " ");
		tweetText = tweetText.replace("“", "");		
		tweetText = removeReTweet(tweetText);
		tweetText = removeTwitterUser(tweetText);
		tweetText = removeUrl(tweetText);
		tweetText= tweetText.replace(":", "");
		return tweetText;

	}

	public static String cleanStopWords(String tweetText) throws IOException {
		String text = cleanTweetText(tweetText);
		
		List<String> listToTestagainst = Files
				.readAllLines(Paths
						.get(stopwords));
		String cleanedText = "";
		StringTokenizer tokenizer = new StringTokenizer(text);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (!listToTestagainst.contains(token)) {
				cleanedText += token+" ";
			}
		}
		cleanedText=cleanedText.trim();
		return cleanedText;

	}

	public static String removeReTweet(String tweetText) {
		try {
			Matcher m = retweetPatter.matcher(tweetText);
			int i = 0;
			while (m.find()) {
				tweetText = tweetText.replaceAll(m.group(i), "").trim();
				i++;
			}
			return tweetText;
		} catch (IndexOutOfBoundsException e) {
			return tweetText;
		}
	}

}
