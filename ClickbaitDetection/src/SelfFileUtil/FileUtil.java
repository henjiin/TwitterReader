package SelfFileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

public class FileUtil {
	public static String readFile(String filename) throws IOException {
		String content = null;
		File file = new File(filename);

		FileReader reader = new FileReader(file);
		char[] chars = new char[(int) file.length()];
		reader.read(chars);
		content = new String(chars);
		reader.close();

		return content;
	}

	public static String readFile(File file) throws IOException {
		String content = null;
		FileReader reader = new FileReader(file);
		char[] chars = new char[(int) file.length()];
		reader.read(chars);
		content = new String(chars);
		reader.close();

		return content;
	}
	public static void storeJSON(String rawJSON, String fileName)
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

	public static void storeTweet(Status tweet, String filename) {
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
}
