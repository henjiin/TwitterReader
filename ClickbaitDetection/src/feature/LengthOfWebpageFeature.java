package feature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import message.Message;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import twitter4j.Status;
import util.WebUtil;

public class LengthOfWebpageFeature extends Feature{
		
	
	public String getArffHeader() {
		return "LengthOfPage numeric";
	}
	public String getCVSHeader(){
		return "LengthOfPage";
	}

	private String getWebpageFromFile(File file) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String everything;
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        everything = sb.toString();
	    } finally {
	        br.close();
	    }
	    return everything;	    
	}

	@Override
	public String getFeature(Message message) {
		String mainContent;
		try {
			mainContent = WebUtil.getMainContent(message.getTargetLink());
			return String.valueOf(mainContent.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "?";
		
	}

}
