package analyst;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitterReader.TweetUtil;

public class Corpus_Analyser {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> JSONFileList = TweetUtil
				.getJSONFileList("/home/sebastiankopsel/Data/Big-Tweet-Corpus");

		PrintWriter writer = null;
	
		
		try {
			writer = new PrintWriter(
					"/home/sebastiankopsel/Data/big_corpus_statistics.csv", "UTF-8");
	
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yy/MM/dd");
		
		for (String fileName : JSONFileList) {
			try {
				Status tweet = TweetUtil.getTweetFromJSON(fileName);
				Date createdAt = tweet.getCreatedAt();
				if(sdf.format(createdAt).toString().equals("2015-06"); 
				writer.write(tweet.getId() + "," + tweet.getUser().getName() + ","
					+ "\n");
				writer.flush();
				//Tweet from week 8-14
				
				

			} catch (IOException | TwitterException e) {
				e.printStackTrace();
			}

		}
		writer.close();

	}
}
