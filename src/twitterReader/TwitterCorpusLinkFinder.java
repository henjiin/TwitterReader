package twitterReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TwitterCorpusLinkFinder {
	 public static String expandShortURL(String address) throws IOException {
		 
		    HttpURLConnection con = (HttpURLConnection) new URL(address).openConnection();
		    con.setInstanceFollowRedirects(false);
		    con.connect();
		    con.getInputStream();
		    
		    if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
		        String redirectUrl = con.getHeaderField("Location");
		        return expandShortURL(redirectUrl);
		    }
		    
		    return address;
		    
	    }

	 public static <K, V extends Comparable<? super V>> Map<K, V> 
	    sortByValue( Map<K, V> map )
	{
	    List<Map.Entry<K, V>> list =
	        new LinkedList<>( map.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	    {
	        @Override
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	        {
	            return (o2.getValue()).compareTo( o1.getValue() );
	        }
	    } );

	    Map<K, V> result = new LinkedHashMap<>();
	    for (Map.Entry<K, V> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return result;
	}
	 /*
	  * This Programm will search a given Tweet Corpus for Links in the Message, 
	  * and will sort them and output it into specified Data 
	  * 
	  * 3.Argument optional, will  return only top X results;
	  * 
	  */

	public static void main(String[] args) {
		if(args.length<2){
			System.out.println("Not enough arguments");
			return;
		}
		int topX = -1;
		if(args.length>=3){
			try{
				topX=Integer.parseInt(args[2]);
				System.out.println(topX);
			}catch(NumberFormatException e){
				System.out.println("Can't understand " + args[3] +"as Argument. Ignoring.");
			}
		}
		String tweetSourcePath=args[0];
		String sortedLinkData=args[1];
		BufferedReader reader = null;
	
		
		
		Map<String, Integer> links= new HashMap<String, Integer>();
		
		try {
		    File file = new File(tweetSourcePath);
		    reader = new BufferedReader(new FileReader(file));
    		Pattern urlRegex = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  
    		long linkCount=0;
    		long tweetCount=0;
		    String line;
		    while ((line = reader.readLine()) != null) {
		    	//
		    	if(line.startsWith("W")){
		    		tweetCount++;
		    		String[] Parts=line.split(" ");
		    		for(String word:Parts){
		    			Matcher matcher = urlRegex.matcher(word);
		    			if(matcher.matches()){
		    				linkCount++;
		    				int count=links.containsKey(word) ? links.get(word) : 0;
		    				links.put(word, count + 1);	 
		    			}  				
		    		}
		    	}
		    }
		    Writer fileWriter=null;
		    try{
		    	fileWriter=new FileWriter(sortedLinkData);
		    }catch(FileNotFoundException e){
		    	System.out.println("File " + sortedLinkData + " not found. Making new File");
		    	fileWriter=new FileWriter(new File(sortedLinkData));
		    }
		    
		    Map sortedLinks=sortByValue(links);
		    
		    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		    Iterator it = sortedLinks.entrySet().iterator();
		    
		    while (it.hasNext()) {
		    	topX--;
		        Map.Entry pair = (Map.Entry)it.next();
		        bufferedWriter.write(pair.getKey() + "\t" + pair.getValue());
		        bufferedWriter.write("\n");
		        it.remove(); // avoids a ConcurrentModificationException
		        if(topX==0) break;
		    }
		    System.out.println("Finished");
		    System.out.println("Read " + tweetCount+ "Tweets");
		    System.out.println("Found "+ linkCount + "links with "+ sortedLinks.size() +"uniques");
		    
		    bufferedWriter.close();
		    
		    
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

		

	}

}
