package util;

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
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TextUtil {

	static String urlPatternString = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";

	static String userPatternString = "@([A-Za-z0-9_]+)";
	static String retweetPatternString = "RT " + userPatternString + ":";
	static Pattern urlPattern = Pattern.compile(urlPatternString,
			Pattern.CASE_INSENSITIVE);
	static Pattern userPattern = Pattern.compile(userPatternString);
	static Pattern retweetPattern = Pattern.compile(retweetPatternString);
	static String stopwords = "/home/sebastiankopsel/data-wstud-thesis-koepsel2/thirdparty-data/stop-words-collection-2014-02-24-raw/stop-words_english_6_en.txt";

	

	public static String removeTwitterUser(String string) {
		try {
			Matcher m = userPattern.matcher(string);
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

	static public String removeUrl(String commentstr) {
		Matcher m = urlPattern.matcher(commentstr);
		int i = 0;
		while (m.find()) {
			commentstr = commentstr.replace(m.group(i), "").trim();
			i++;
		}
		return commentstr;
	}

	/*
	 * Cleans text from unesseccary stuff,: linebreaks, "'s, urls
	 */

	public static String cleanText(String tweetText) {
		tweetText = tweetText.trim();
		tweetText = tweetText.replaceAll("\n", "").replaceAll("\r", "");
		// tweetText = tweetText.replaceAll("’", " ");
		// tweetText = tweetText.replaceAll("'", "");
		tweetText = tweetText.replace("\"", " ");
		tweetText = tweetText.replace("“", "");
		// tweetText = removeReTweet(tweetText);
		// tweetText = removeTwitterUser(tweetText);
		tweetText = removeUrl(tweetText);
		return tweetText;

	}

	public static String cleanStopWords(String tweetText) throws IOException {
		String text = cleanText(tweetText);

		List<String> listToTestagainst = Files.readAllLines(Paths
				.get(stopwords));
		String cleanedText = "";
		StringTokenizer tokenizer = new StringTokenizer(text);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (!listToTestagainst.contains(token)) {
				cleanedText += token + " ";
			}
		}
		cleanedText = cleanedText.trim();
		return cleanedText;
	}

	public static String removeReTweet(String tweetText) {
		try {
			Matcher m = retweetPattern.matcher(tweetText);
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

	public static Twitter getTwitter() {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("dWEbLPNrfb4j6ClbuV2zbb2MF")
				.setOAuthConsumerSecret(
						"ITjeI4jI5Xu7p6UmdLMwlQbmhA8hkwVpmebIqyN2qredCOah6A")
				.setOAuthAccessToken(
						"3280097199-I9Q4dEtsX2qr9BnT5n9mTPeYlYV3Fm1yBMV5231")
				.setOAuthAccessTokenSecret(
						"jk08WJwHeYyK7WWK1ljhuswIc8LFuivMvwB09vKedkPN4")
				.setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}

}
