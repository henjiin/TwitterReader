package twitterReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import static java.nio.file.CopyOption.*;
public class TwitterUserAnalyst {
	static String twitterUser = "washingtonpost";
	static String documentPath = "/home/sebastiankopsel/Data/Serious/";
	static final int SERIOUS = -1;
	static final int CLICKBAIT = 1;
	static final int NEUTRAL = 0;

	/*
	 * Stores Tweet to a default location for convinience Tweet stored as JSON
	 * Entity
	 */
	/*
	 * Stores Tweet to a default location for convinience Tweet stored as JSON
	 * Entity
	 */
	public TwitterUserAnalyst() {
		// TODO Auto-generated constructor stub
		getTwitter();
		
	}
	private static void storeJSON(String rawJSON, String fileName)
			throws IOException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(fileName);
			osw = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(osw);
			bw.write(rawJSON);
			bw.flush();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ignore) {
				}
			}
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException ignore) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ignore) {
				}
			}
		}
	}

	public static void storePage(String URL, String fileName) throws IOException{
		URL url = new URL(URL);
		URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
		 PrintWriter writer = new PrintWriter(
			new BufferedWriter(
					new FileWriter(fileName, false))); 		 

        String inputLine;   
        writer.println("<!--"+URL+"-->");
        while ((inputLine = br.readLine()) != null) {
                writer.println(inputLine);
        }
        writer.flush();
        writer.close();
        br.close();

	}
	
	static void storeTweet(Status tweet, String filename) {
		String rawJSON = TwitterObjectFactory.getRawJSON(tweet);
		String fileName = filename;
		fileName += tweet.getId();
		try {
			storeJSON(rawJSON, fileName+ ".json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			storePage(tweet.getURLEntities()[0].getExpandedURL(), fileName+".html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}


	static void storeTweet(Status tweet, int status) {
		String rawJSON = TwitterObjectFactory.getRawJSON(tweet);
		String fileName = documentPath;
		switch (status) {
		case SERIOUS:
			fileName += "serious/";
			break;
		case CLICKBAIT:
			fileName += "clickbait/";
			break;
		case NEUTRAL:
			fileName += "etc/";
			break;
		default:
			break;
		}

		fileName += tweet.getId();
		try {			
			storeJSON(rawJSON, fileName+ ".json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			storePage(tweet.getURLEntities()[0].getExpandedURL(), fileName+".html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
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
		Runtime rt = Runtime.getRuntime();
		Process pr;
		

			// if (tweet.getURLEntities().length > 0)
			// url = tweet.getURLEntities()[0].getExpandedURL();
			// else
			String url = "https://twitter.com/" + tweet.getUser().getScreenName()
					+ "/status/" + tweet.getId();
			try {
				pr = rt.exec("chromium-browser " + url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
		// Opens new tab to view the tweet
		if (chose.equals("v")) {
			Runtime rt = Runtime.getRuntime();
			Process pr;
			try {
				String url = "";

				// if (tweet.getURLEntities().length > 0)
				// url = tweet.getURLEntities()[0].getExpandedURL();
				// else
				url = "https://twitter.com/" + tweet.getUser().getScreenName()
						+ "/status/" + tweet.getId();
				pr = rt.exec("chromium-browser " + url);

			} catch (Exception e) {

			}
			evaluateTweet(tweet);

		}
		return status;
	}
	public static Twitter getTwitter(){
		
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
	private static String readFirstLine(File fileName) throws IOException {
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

	public static void main(String[] args) {

		int tweetsPerPage = 50;
		if (args.length > 1) {
			twitterUser = args[1];
			documentPath = args[0];
		}

		Twitter twitter=getTwitter();
		// Opens Twitter API
		// Config Builder is necessary to enable JSON store enabled
				/*
		 * Twitter twitter = new TwitterFactory().getInstance();
		 * twitter.setOAuthConsumer("dWEbLPNrfb4j6ClbuV2zbb2MF",
		 * "ITjeI4jI5Xu7p6UmdLMwlQbmhA8hkwVpmebIqyN2qredCOah6A");
		 * twitter.setOAuthAccessToken(new AccessToken(
		 * "3280097199-I9Q4dEtsX2qr9BnT5n9mTPeYlYV3Fm1yBMV5231",
		 * "jk08WJwHeYyK7WWK1ljhuswIc8LFuivMvwB09vKedkPN4"));
		 */
		int pageCounter = 1;
		Paging paging = new Paging(pageCounter, tweetsPerPage);
		List<Status> statuses = null;
		//Go through potentially all tweets of a user
		//Twitter can and will react funny at times
		/*
		do {
			try{
				statuses = twitter.getUserTimeline(twitterUser, paging);
			} catch (TwitterException twitterException) {
				//Usually when connection limit reached
				System.out.println("Twitter sagt Feierabend");
				break;
			}
			for (Status status : statuses) {
				// No Links-> No Clickbait
				if (status.getURLEntities().length > 0) {
					int isClickbait = evaluateTweet(status);
					//stores tweet and the associated webpage
					
					storeTweet(status, isClickbait);
					

				}
			}
			
			pageCounter++;
			paging = new Paging(pageCounter, tweetsPerPage);
			//Feedback system for the sanity of the programs user
			System.out.println("Another " + tweetsPerPage
					+ "Tweets done.\nWant to continue?");
			Scanner sc = new Scanner(System.in);
			if (sc.nextLine().equals("q"))
				break;

		} while ((statuses != null) && (!statuses.isEmpty()));
		*/
		try {
			statuses = twitter.getUserTimeline("test", paging);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			File[] files = new File("/home/sebastiankopsel/Data/Unclassified")
					.listFiles(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.endsWith(".json");
						}
					});
			Arrays.sort(files, new Comparator<File>(){
			    public int compare(File f1, File f2)
			    {
			        return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			    } });

			for (File file : files) {				
				{
				String rawJSON = readFirstLine(file);
				Status status = TwitterObjectFactory.createStatus(rawJSON);
				if (status.getURLEntities().length > 0) {
					int isClickbait = evaluateTweet(status);
					//stores tweet and the associated webpage
					
					//storeTweet(status, isClickbait);
					moveTweet(status, isClickbait);					

				}
				}
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Failed to store tweets: " + ioe.getMessage());
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}

		System.out.println("End of Stream");

	}
	private static void moveTweet(Status status, int isClickbait) {
		// TODO Auto-generated method stub
		String fromJSON ="/home/sebastiankopsel/Data/Unclassified/"+status.getId()+".json";
		String fromHTML = "/home/sebastiankopsel/Data/Unclassified/"+status.getId()+".html";
		String toJSON = "/home/sebastiankopsel/Data/Serious/";
		String toHTML= "/home/sebastiankopsel/Data/Serious/";
		
		if(isClickbait==CLICKBAIT){
			toJSON+="clickbait/"+status.getId()+".json";
			toHTML+="clickbait/"+status.getId()+".html";
		}
		if(isClickbait==SERIOUS){
			toJSON+="serious/"+status.getId()+".json";
		    toHTML+="serious/"+status.getId()+".html";
		}
		if(isClickbait==NEUTRAL ){
			toJSON+="etc/"+status.getId()+".json";;
			toHTML+="etc/"+status.getId()+".html";
		}
		try {
			Files.move(Paths.get(fromJSON) ,Paths.get(toJSON));
			Files.move(Paths.get(fromHTML) ,Paths.get(toHTML));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
