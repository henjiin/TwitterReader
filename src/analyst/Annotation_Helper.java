package analyst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitterReader.TweetUtil;
import static java.nio.file.StandardCopyOption.*;

public class Annotation_Helper {


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Reader in = new FileReader(
				"/home/sebastiankopsel/clickbait-korpus.csv");

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		for (CSVRecord record : records) {
		    int prof = record.get("Prof. Hagen").equals("x")? 1:0;
		    int martin = record.get("Martin Potthast").equals("x")? 1:0;
		    int Sebastian = record.get("clickbait_class").equals("x")? 1:0;
		    String id=record.get("Status_ID");		   
		    id= (parseID(id));
		    if(prof+martin+Sebastian<2){
		    	System.out.println("hi");
		    	Files.copy(Paths.get("/home/sebastiankopsel/Data/all/"+id+".json"), Paths.get("/home/sebastiankopsel/Data/all/s/"+id+".json"),REPLACE_EXISTING ); 
		    }
		    else{
		    	Files.copy(Paths.get("/home/sebastiankopsel/Data/all/"+id+".json"), Paths.get("/home/sebastiankopsel/Data/all/c/"+id+".json"),REPLACE_EXISTING ); 

		    }
		    
		}

		

	}

	private static String parseID(String string) {
		final Pattern pattern = Pattern.compile("(\\d)+");
		final Matcher matcher = pattern.matcher(string);
		if(matcher.find())
		return matcher.group();
		else return"";
	}

}
