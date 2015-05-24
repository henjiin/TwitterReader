package twitterReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Scanner;

import twitter4j.*;
import twitter4j.auth.AccessToken;

public class TwitterUserAnalyst {
	static String twitterUser = "voxdotcom";
	static String documentPath = "/home/sebastiankopsel/Data/Serious/";

	/*
	 * Stores Tweet to a default location for convinience Tweet stored as JSON
	 * Entity
	 */
	static void storeTweet(Status tweet, int status) {

		PrintWriter clickbaitStore = null;
		PrintWriter seriousStore = null;
		try {
			clickbaitStore = new PrintWriter(new BufferedWriter(new FileWriter(
					documentPath + "clickbaitTweets.txt", true)));
			seriousStore = new PrintWriter(new BufferedWriter(new FileWriter(
					documentPath + "seriousTweets.txt", true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (status == 1)
			clickbaitStore.write(tweet.toString());
		if (status == -1)
			seriousStore.write(tweet.toString());
		seriousStore.close();
		clickbaitStore.close();

	}

	static void presentTweet(Status tweet) {

		if (tweet.getInReplyToScreenName() != null)
			System.out.println("Reply:\t" + tweet.getInReplyToScreenName());
		System.out.println("Tweet:\t" + tweet.getText());

		if (tweet.getMediaEntities().length > 0)
			System.out.println("Media:\t"
					+ tweet.getMediaEntities()[0].getExpandedURL());
		if (tweet.getURLEntities().length > 0)
			System.out.println("URL:\t"
					+ tweet.getURLEntities()[0].getExpandedURL());
		System.out.println("------------------");

	}

	/*
	 * Returns the ClickBait Status of the Tweet -1= Serious news 0=Not Defined/
	 * unrelevant 1=Clickbait
	 */
	static int evaluateTweet(Status tweet) {
		int status = 0;
		Scanner sc = new Scanner(System.in);
		presentTweet(tweet);

		String chose = sc.next();

		if (chose.equals("j")) {
			System.out.println("Stored as Clickbait");
			status = 1;
		}
		if (chose.equals("h")) {
			System.out.println("Stored as Serious");
			status = -1;
		}
		//Opens  new tab to view the tweet
		if (chose.equals("v")) {
			Runtime rt = Runtime.getRuntime();
			Process pr;
			try {
				String url = "";

				//if (tweet.getURLEntities().length > 0)
					//url = tweet.getURLEntities()[0].getExpandedURL();
				//else
					url = "https://twitter.com/"
							+ tweet.getUser().getScreenName() + "/status/"
							+ tweet.getId();
				pr = rt.exec("chromium-browser " + url);
				
			} catch (Exception e) {

			}
			evaluateTweet(tweet);

		}
		return status;
	}

	public static void main(String[] args) {
		WebPageStorer clickBaiteStorer = null;
		WebPageStorer seriousStorer = null;
		WebPageStorer etcStorer = null;
		int tweetsPerPage=50;
		if (args.length > 1) {
			twitterUser = args[1];
			documentPath = args[0];
		}
		//Initialises the storage objects
		clickBaiteStorer = new WebPageStorer(documentPath + twitterUser
				+ "-clickbaitHTML.txt", "/home/sebastiankopsel/Data/pagesList");
		seriousStorer = new WebPageStorer(documentPath + twitterUser
				+ "-seriousHTML.txt", "/home/sebastiankopsel/Data/seriousList");
		etcStorer = new WebPageStorer(documentPath + twitterUser
				+ "-etcHTML.txt", "/home/sebastiankopsel/Data/etcList");

		// Opens Twitter API
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("dWEbLPNrfb4j6ClbuV2zbb2MF",
				"ITjeI4jI5Xu7p6UmdLMwlQbmhA8hkwVpmebIqyN2qredCOah6A");
		twitter.setOAuthAccessToken(new AccessToken(
				"3280097199-I9Q4dEtsX2qr9BnT5n9mTPeYlYV3Fm1yBMV5231",
				"jk08WJwHeYyK7WWK1ljhuswIc8LFuivMvwB09vKedkPN4"));

		int pageCounter = 2;
		Paging paging = new Paging(pageCounter, tweetsPerPage);
		List<Status> statuses = null;

		do {
			try {
				statuses = twitter.getUserTimeline(twitterUser, paging);
			} catch (TwitterException twitterException) {
				System.out.println("Twitter sagt Feierabend");
				break;
			}
			for (Status status : statuses) {
				// No Links-> No Clickbait
				if (status.getURLEntities().length > 0) {
					int isClickbait = evaluateTweet(status);
					storeTweet(status, isClickbait);
					try {
						if (isClickbait < 0)
							seriousStorer.storePage(status.getURLEntities()[0]
									.getExpandedURL());
						if (isClickbait == 0)
							etcStorer.storePage(status.getURLEntities()[0]
									.getExpandedURL());
						if (isClickbait > 0)
							clickBaiteStorer
									.storePage(status.getURLEntities()[0]
											.getExpandedURL());
					} catch (IOException e) {
						System.out.println("Error Occured. Page Not Stored");
					}
				}
			}
			pageCounter++;
			paging = new Paging(pageCounter, tweetsPerPage);
			System.out.println("Another " + tweetsPerPage + "Tweets done.\nWant to continue?");
			Scanner sc=new Scanner(System.in);
			if(sc.nextLine().equals("q"))break;
			
		} while ((statuses != null) && (!statuses.isEmpty()));

		System.out.println("End of Stream");

	}

}
