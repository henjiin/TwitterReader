package twitterReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class LinkRater {
	
	public static String expandShortURL(String address) throws IOException {
		HttpURLConnection con=null;
		try{
			con = (HttpURLConnection) new URL(address)
				.openConnection();
		}catch(MalformedURLException e){
			
		}
		con.setInstanceFollowRedirects(false);
		con.connect();
		con.getInputStream();

		if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
				|| con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			String redirectUrl = con.getHeaderField("Location");
			return expandShortURL(redirectUrl);
		}

		return address;

	}
	
	public static String expandURL(String url) throws IOException{
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setReadTimeout(5000);
		conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		conn.addRequestProperty("User-Agent", "Mozilla");
		conn.addRequestProperty("Referer", "google.com");
		boolean redirect = false;
		int status = conn.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP
				|| status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_SEE_OTHER)
			redirect = true;
		}
		System.out.println("Response Code ... " + status);
		if (redirect) {
			 
			// get redirect url from "location" header field
			String newUrl = conn.getHeaderField("Location");
	 
			// get the cookie if need, for login
			String cookies = conn.getHeaderField("Set-Cookie");
	 
			// open the new connnection again
			conn = (HttpURLConnection) new URL(newUrl).openConnection();

	 
			System.out.println("Redirect to URL : " + newUrl);
			return newUrl;
	 
		}
		return url;
		


	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Not enough arguments");
			return;
		}
		String linkSourcePath = args[0];
		String taggedLinkPath = args[1];
		Runtime rt = Runtime.getRuntime();
		Process pr;
		try {
			pr = rt.exec("chromium-browser --new-window www.google.de");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader reader = null;
		try {
			File file = new File(linkSourcePath);
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String link = line.substring(0, line.indexOf("\t") );
				try {
					link = expandShortURL(link);
					if(link.startsWith("https://twitter.com/"))
						continue;
				} catch (Exception e) {
					System.out.println("Page not Found");
				}
				System.out.println(link);
				
				pr = rt.exec("chromium-browser " + link);
				System.in.read();

			}

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
