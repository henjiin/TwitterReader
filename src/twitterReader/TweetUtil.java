package twitterReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

public class TweetUtil {
	static String urlPattern = "((https?|ftp|file|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";

	static String userPattern = "@([A-Za-z0-9_]+)";
	static Pattern URLpattern = Pattern.compile(urlPattern,
			Pattern.CASE_INSENSITIVE);
	static Pattern userPatter = Pattern.compile(userPattern);

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
		tweetText = tweetText.replaceAll("'", " ");
		return tweetText;

	}

	private static String removeReTweet(String tweetText) {
		tweetText = tweetText.replaceAll("RT :", "");
		tweetText = tweetText.replaceAll("RT:", "");
		tweetText = tweetText.replaceAll("RT", "");
		tweetText = tweetText.trim();
		return tweetText;
	}

}
