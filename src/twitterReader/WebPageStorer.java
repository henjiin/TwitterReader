package twitterReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class WebPageStorer {
	File _pageStorage;
	File _listOfPages;
	PrintWriter _storageWriter;	
	String pathToStorage;
	String pathToList;
	boolean appendToFiles=true;
	
	public WebPageStorer() {
		_pageStorage=new File("pageStorage");
		_listOfPages=new File("listOfPages");		
		initWriters("pageStorage", "listOfPages");

	}
	
	public WebPageStorer(String pageStorage, String listOfPages){
		_pageStorage=new File(pageStorage);
		_listOfPages=new File(listOfPages);		
		initWriters(pageStorage, listOfPages);
	}
	public WebPageStorer(String pageStorage, String listOfPages, boolean appendFile){
		_pageStorage=new File(pageStorage);
		_listOfPages=new File(listOfPages);	
		appendToFiles=appendFile;
		initWriters(pageStorage, listOfPages);
		
	}
	public void storePage(String URL) throws IOException{

		
        URL url = new URL(URL);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        _storageWriter.println("########################################################");
        _storageWriter.println(URL);
        _storageWriter.println();
        while ((inputLine = br.readLine()) != null) {
                _storageWriter.println(inputLine);
        }
        _storageWriter.flush();
        br.close();
        
       }
	 void initWriters(String pageStorage, String listOfPages){
		try{
			 _storageWriter = new PrintWriter(
				new BufferedWriter(
						new FileWriter(_pageStorage, appendToFiles))); 			 
		}
		    
		catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
}


