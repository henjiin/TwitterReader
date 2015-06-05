package twitterReaderTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import twitterReader.TweetUtil;

public class TweetUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRemoveTwitterUser() {
		String tweetText;
		String expected;
		String actual;		
		
		tweetText= "I Thought This Cat "
				+ "Was Going To Be Owl Food"
				+ " - But Watch What Happens Next";
		expected= "I Thought This Cat Was Going To Be Owl Food - But Watch What Happens Next";
		actual=TweetUtil.removeTwitterUser(tweetText);					
		assertEquals(expected, actual);
		
		tweetText="RT @JessicaKRoy: people are the worst http";		
		expected="RT : people are the worst http";		
		actual=TweetUtil.removeTwitterUser(tweetText);
		assertEquals(expected, actual);
		
		tweetText="Sponsored: See how @SakaraLife s co-founders celebrated their first sucess, from @Piaget.";		
		expected="Sponsored: See how  s co-founders celebrated their first sucess, from .";		
		actual=TweetUtil.removeTwitterUser(tweetText);
		assertEquals(expected, actual);
		
		tweetText="This week on @nymag s Sex Lives podcast: Which museum should I have sex in?";		
		expected="This week on  s Sex Lives podcast: Which museum should I have sex in?";		
		actual=TweetUtil.removeTwitterUser(tweetText);
		assertEquals(expected, actual);
		
		tweetText="RT @BBCFilms: #ICYMI BBC Films & @bbcwritersroom have released some great scripts including #InTheLoop #MadeInDagenham & loads more http://…";		
		expected="RT : #ICYMI BBC Films &  have released some great scripts including #InTheLoop #MadeInDagenham & loads more http://…";		
		actual=TweetUtil.removeTwitterUser(tweetText);
		assertEquals(expected, actual);
		
		tweetText=".@grubstreet s new @Instagram is reason enough to throw a";		
		expected=". s new  is reason enough to throw a";		
		actual=TweetUtil.removeTwitterUser(tweetText);
		assertEquals(expected, actual);		
	}

	@Test
	public void testCleanTweetText() {		
		String tweetText;
		String expected;
		String actual;		
		
		tweetText= "I Thought This Cat Was Going To Be Owl Food - But Watch What Happens Next";
		expected= "I Thought This Cat Was Going To Be Owl Food - But Watch What Happens Next";
		actual=TweetUtil.cleanTweetText(tweetText);					
		assertEquals(expected, actual);
		
		tweetText= "Ten things we’ve learned from the SNP’s first full week in Parliament ";
		expected= "Ten things we ve learned from the SNP s first full week in Parliament";
		actual=TweetUtil.cleanTweetText(tweetText);					
		assertEquals(expected, actual);
		
		tweetText= "RT @ReutersPolitics: “I’d rather go to Iraq than work for Carly Fiorina again.\" Former staffers speak out ";
		expected= "RT @ReutersPolitics: I d rather go to Iraq than work for Carly Fiorina again. Former staffers speak out";
		actual=TweetUtil.cleanTweetText(tweetText);					
		assertEquals(expected, actual);
		
		tweetText= "Why one veteran hates \"Happy Memorial Day\"";
		expected= "Why one veteran hates Happy Memorial Day";
		actual=TweetUtil.cleanTweetText(tweetText);					
		assertEquals(expected, actual);
		
		tweetText= "Learn about the history and \ntraditions of the #MemorialDay holiday.";
		expected= "Learn about the history and traditions of the #MemorialDay holiday.";
		actual=TweetUtil.cleanTweetText(tweetText);					
		assertEquals(expected, actual);
	}
	@Test
	public void testRemoveReTweet() {
		String tweetText;
		String expected;
		String actual;		
		
		tweetText= "'RT @BBCiWonder: Should we reintroduce lynx to the UK today, 1,300 years after its disappearance?";
		expected= "' Should we reintroduce lynx to the UK today, 1,300 years after its disappearance?";
		actual=TweetUtil.removeReTweet(tweetText);					
		assertEquals(expected, actual);

		tweetText= "RT @leighgallagher: Tequila leaves the \"shots ghetto\" and goes way upscale (which means no hangovers!) @risenc in @FortuneMagazine";
		expected= "Tequila leaves the \"shots ghetto\" and goes way upscale (which means no hangovers!) @risenc in @FortuneMagazine";
		actual=TweetUtil.removeReTweet(tweetText);					
		assertEquals(expected, actual);
		
		tweetText= "RT @BBCSport: Our live text commentator sums up the start to the second half... Follow it here:";
		expected= "Our live text commentator sums up the start to the second half... Follow it here:";
		actual=TweetUtil.removeReTweet(tweetText);					
		assertEquals(expected, actual);
		
		tweetText= "RT @BBCSport: Our live text commentator sums up the start to the second half... Follow it here:";
		expected= "Our live text commentator sums up the start to the second half... Follow it here:";
		actual=TweetUtil.removeReTweet(tweetText);					
		assertEquals(expected, actual);
		
		tweetText= "RT @bbcweather: Wondering what the weather has in s";
		expected= "Wondering what the weather has in s";
		actual=TweetUtil.removeReTweet(tweetText);					
		assertEquals(expected, actual);
	}
	

}
