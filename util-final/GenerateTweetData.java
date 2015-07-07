package corpora;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import message.TwitterUserAnalyst;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GenerateTweetData {

	/**
	 * This Script gets Tweet Timelines from given users and saves them in given
	 * place. Default behauviour is set for my pc only. You can give only
	 * Filename to get default users
	 */
	static // default accounts to grab
	String[] twitterAccounts = { "BBCNews", "nytimes", "mashable",
			"guardian", "CNN", "HuffingtonPost", "FoxNews", "nbc",
			"BuzzFeed", "BleacherReport", "ABC", "Forbes", "Independent",
			"Telegraph", "Yahoo", "businessinsider", "MailOnline",
			"indiatimes", "USATODAY", "espn", "ComplexMag", "verge",
			"washingtonpost", "billboard", "WSJ", "TIME", "MTV", "RT_com",
			"ndtv", "latimes", "Gizmodo", "TMZ", "BreitbartNews",
			"Entrepreneur", "CNET", "TechCrunch", "FIFAcom", "GOAL", "CBS" };
	public static void main(String[] args) {
		String filename = null;
		if(args.length<1){
			File dir = new File("corpus");
			dir.mkdir();			
			filename="corpus/";
		}	

		if (args.length > 0) {
			filename = args[0];
			if(!filename.endsWith("/")){
				filename+="/";
			}
			if (args.length > 1) {
				twitterAccounts = Arrays.copyOfRange(args, 1,
						args.length);
			}
		}
		System.out.println("Starting process");
		System.out.println("Saving in Folder: " + new File(filename).getAbsolutePath());
		System.out.println("Grabbing following accounts:");
		for(int i=0; i<twitterAccounts.length; i++){
			System.out.println("\t"+twitterAccounts[i]);
		}
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
		int pageCounter = 1;
		int tweetsPerPage = 25;
		Paging paging = new Paging(pageCounter, tweetsPerPage);

		List<Status> statuses = null;
		
		// Runs until stopped.

		while (true) {
			for (String user : twitterAccounts) {
				try {
					statuses = twitter.getUserTimeline(user, paging);
				} catch (TwitterException twitterException) {
					// Usually when connection limit reached
					return;
				}
				for (Status status : statuses) {
					// No Links-> No Clickbait
					if (status.getURLEntities().length > 0) {
						// stores tweet and the associated webpage
						TwitterUserAnalyst.storeTweet(status, filename);
					}
				}
			}
			pageCounter++;
			paging = new Paging(pageCounter, tweetsPerPage);
		}

	}
}

