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

public class LengthOfWebpageFeature extends Feature{
	String pathToCorpus="/home/sebastiankopsel/Data/Korpus-HTMLs/";
	public void setPathToCorpus(String pathToCorpus) {
		this.pathToCorpus = pathToCorpus;
	}		
	
	public String getArffHeader() {
		return "LengthOfPage numeric";
	}
	public String getCVSHeader(){
		return "LengthOfPage";
	}
	
	public String getFeature(Status tweet){
		File file;
		String extractedMainContent;
		//check if page is in corpus
		if((file=new File(pathToCorpus+tweet.getId()+".html")).exists()){
			String html ="";
			try {
				html = getWebpageFromFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			extractedMainContent=getMainContent(html);
			int lengthOfMainContent=extractedMainContent.length();
			return String.valueOf(lengthOfMainContent);
		}
		else return "?";
	}
	
	private String getMainContent(String pageHtml) {		
		String cleanedText = "";
		try {
			cleanedText = ArticleExtractor.INSTANCE.getText(pageHtml);
		} catch (BoilerpipeProcessingException e) {
			//Noone cares what boilerpipe thinks			
		}	
		
		return cleanedText;
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
		// TODO Auto-generated method stub
		return null;
	}

}
