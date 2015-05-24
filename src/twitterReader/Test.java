package twitterReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("dWEbLPNrfb4j6ClbuV2zbb2MF",
				"ITjeI4jI5Xu7p6UmdLMwlQbmhA8hkwVpmebIqyN2qredCOah6A");
		twitter.setOAuthAccessToken(new AccessToken(
				"3280097199-I9Q4dEtsX2qr9BnT5n9mTPeYlYV3Fm1yBMV5231",
				"jk08WJwHeYyK7WWK1ljhuswIc8LFuivMvwB09vKedkPN4"));
		int pageCounter = 1;
		Paging paging = new Paging(pageCounter, 200);
		List<Status> statuses = null;

		do {

			try {
				statuses = twitter.getUserTimeline("HuffingtonPost", paging);
				for (Status status : statuses) {
					// System.out.println(status.getText());
				}
			} catch (TwitterException e) {
				System.out.println("Twitter sagt Feierabend");
			}

			System.out.println(pageCounter);
			pageCounter++;
			paging = new Paging(pageCounter, 1);
		} while (statuses!=null&&(!statuses.isEmpty()));
		System.out.println("End of Stream");

	}

}
