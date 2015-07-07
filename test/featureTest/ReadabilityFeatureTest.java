package featureTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import feature.ReadabilityFeature;
import feature.SentenceCounterFeature;
import feature.SyllablesCounterFeature;
import feature.WordCounterFeature;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitterReader.TweetUtil;

public class ReadabilityFeatureTest {

	@Test
	public void testGetFeature() {
		String pathToTestTweets="/home/sebastiankopsel/Data/TestData/";
		try {
			Status tweet1=TweetUtil.getTweetFromJSON(pathToTestTweets+"test1.json");
			Status tweet2=TweetUtil.getTweetFromJSON(pathToTestTweets+"test2.json");
			Status tweet3=TweetUtil.getTweetFromJSON(pathToTestTweets+"test3.json");
			Status tweet4=TweetUtil.getTweetFromJSON(pathToTestTweets+"test4.json");
			Status tweet5=TweetUtil.getTweetFromJSON(pathToTestTweets+"test5.json");
			Status tweet6=TweetUtil.getTweetFromJSON(pathToTestTweets+"test6.json");
			
			ReadabilityFeature readability= new ReadabilityFeature();
			SyllablesCounterFeature syllables = new SyllablesCounterFeature();
			WordCounterFeature words= new WordCounterFeature();
			SentenceCounterFeature sentences = new SentenceCounterFeature();
			
			System.out.println(TweetUtil.cleanTweetText(tweet1.getText()));
			System.out.println("Readability: "+readability.getFeature(tweet1));
			System.out.println("Syllables: "+syllables.getFeature(tweet1));
			System.out.println("Words: "+ words.getFeature(tweet1));
			System.out.println("Sentences: "+ sentences.getFeature(tweet1));
			
			System.out.println(TweetUtil.cleanTweetText(tweet2.getText()));
			System.out.println("Readability: "+readability.getFeature(tweet2));
			System.out.println("Syllables: "+syllables.getFeature(tweet2));
			System.out.println("Words: "+ words.getFeature(tweet2));
			System.out.println("Sentences: "+ sentences.getFeature(tweet2));
			
			System.out.println(TweetUtil.cleanTweetText(tweet3.getText()));
			System.out.println("Readability: "+readability.getFeature(tweet3));
			System.out.println("Syllables: "+ syllables.getFeature(tweet3));
			System.out.println("Words: "+ words.getFeature(tweet3));
			System.out.println("Sentences: "+ sentences.getFeature(tweet3));
			
			System.out.println(TweetUtil.cleanTweetText(tweet4.getText()));
			System.out.println("Readability: "+readability.getFeature(tweet4));
			System.out.println("Syllables: "+syllables.getFeature(tweet4));
			System.out.println("Words: "+ words.getFeature(tweet4));
			System.out.println("Sentences: "+ sentences.getFeature(tweet4));
			
			System.out.println(TweetUtil.cleanTweetText(tweet5.getText()));
			System.out.println("Readability: "+readability.getFeature(tweet5));
			System.out.println("Syllables: " +syllables.getFeature(tweet5));
			System.out.println("Words: "+ words.getFeature(tweet5));
			System.out.println("Sentences: "+ sentences.getFeature(tweet5));
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
