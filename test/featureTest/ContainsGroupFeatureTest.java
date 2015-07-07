package featureTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitterReader.TweetUtil;
import feature.ContainsGroupFeature;

public class ContainsGroupFeatureTest {

	@Test
	public void test() throws IOException, TwitterException {
		ContainsGroupFeature containsGroup = new ContainsGroupFeature("test",
				new String[] {"hour", "what", "horrif", "inspired", "Japanese"});
		containsGroup.applyStringContainsMode();
		String pathToTestTweets = "/home/sebastiankopsel/Data/TestData/";
		List<Status> statuses= new LinkedList<Status>();
		List<String> list2 = TweetUtil.getJSONFileList(pathToTestTweets);
		for(String file:list2){
			Status status= TweetUtil.getTweetFromJSON(file);
			statuses.add(status);
		}
		for(Status status: statuses){
			String tweetText=status.getText().toLowerCase();
			System.out.println(tweetText+ ">>"+containsGroup.stringContainsItemFromList(tweetText));
			
		}
		

	}

}
