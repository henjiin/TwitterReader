import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;

public class GetCorpusOverview {
	public static void main(String[] args) {
		List<String> JSONFileList = new LinkedList<String>();
		if (args.length < 1) {
			System.err
					.println("Not enough arguments. Please specify the folder with the messages.");
			System.exit(0);
		} else {
			JSONFileList = TweetUtil.getJSONFileList(args[0]);
		}
		PrintWriter writer = null;
		if(args.length>1){
			try {
				writer = new PrintWriter(args[2],
						"UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {

				e1.printStackTrace();
			}
		}
		else{
			writer= new PrintWriter(System.out);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");

		for (String fileName : JSONFileList) {
			try {
				Status tweet = TweetUtil.getTweetFromJSON(fileName);
				Date createdAt = tweet.getCreatedAt();
				String output = tweet.getId() + "," + tweet.getUser().getName()
						+ "," + sdf.format(createdAt) + "\n";
				writer.write(output);
			} catch (IOException | TwitterException e) {
				e.printStackTrace();

			}

		}
		writer.close();
	}

}

