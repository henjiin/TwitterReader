package twitterReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.URLEntity;
import twitter4j.auth.AccessToken;

public class TwitterSteamSaver {
	 /*
	  * 
	  * This functions expands links - 
	  * Usually from t.co -the twitter link redirector- to normal, 
	  * but if this is some redirector like bit.ly, it will further expand
	  */
	

    /*
     * This function will read the Twitter Streaming API Stream and will safe all Tweets with links in the following Format
     * T <Time>
     * U <User>
     * W <Message>     
     */
    
	 
	public static void main(String[] args) {		
		if(args.length<2){
			System.out.println("Not enough arguments, please give the Filepath and 0- append to file or 1 new file");
			return;
		}
		File file = new File(args[0]);
	    Writer fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

		
		
		StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
            	try {
					bufferedWriter.write("T\t"+ new Date().toString()+"\n");
					bufferedWriter.write("U\t"+status.getUser().getName()+"\n");
					bufferedWriter.write("W\t"+status.getText()+"\n");
					bufferedWriter.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                //System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };	    
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthConsumer("dWEbLPNrfb4j6ClbuV2zbb2MF", "ITjeI4jI5Xu7p6UmdLMwlQbmhA8hkwVpmebIqyN2qredCOah6A");
        twitterStream.setOAuthAccessToken(new AccessToken("3280097199-I9Q4dEtsX2qr9BnT5n9mTPeYlYV3Fm1yBMV5231", "jk08WJwHeYyK7WWK1ljhuswIc8LFuivMvwB09vKedkPN4"));
	    twitterStream.addListener(listener);
	    // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
	    twitterStream.sample();

	}

}
