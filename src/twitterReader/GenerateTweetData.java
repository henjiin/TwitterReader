package twitterReader;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class GenerateTweetData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// default place to save data
		String filename = "/home/sebastiankopsel/Data/Unclassified/";
		// default accounts to grab
		String[] twitterAccounts = { "Slate", "washingtonpost","FortuneMagazine", "bbc", "TheCut",
				"nytimes", "HuffingtonPost", "ViralNova", "Reuters",
				"voxdotcom", "BuzzFeed", "WashTimes","YahooNews","RT_com","business"};
		if (args.length > 1) {
			filename = args[0];
			if (args.length > 1) {
				twitterAccounts = Arrays.copyOfRange(twitterAccounts, 1,
						args.length - 1);
			}
		}

		Twitter twitter = TwitterUserAnalyst.getTwitter();
		int pageCounter = 1;
		int tweetsPerPage = 50;
		Paging paging = new Paging(pageCounter, tweetsPerPage);
		List<Status> statuses = null;
		while (true) {
			for (String user : twitterAccounts) {
				System.out.println("getting " + user);

				try {
					statuses = twitter.getUserTimeline(user, paging);
				} catch (TwitterException twitterException) {
					// Usually when connection limit reached
					System.out.println("Twitter sagt Feierabend");
					return;
				}
				for (Status status : statuses) {
					System.out.print("|");
					// No Links-> No Clickbait
					if (status.getURLEntities().length > 0) {
						// stores tweet and the associated webpage

						TwitterUserAnalyst.storeTweet(status, filename);

					}
				}
				System.out.println();

			}
			pageCounter++;
			paging = new Paging(pageCounter, tweetsPerPage);

		}

	}
}
