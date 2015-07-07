package message;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.omg.CosNaming.IstringHelper;

import SelfFileUtil.FileUtil;

public class MessageFactory {

	public static Message getMessage(File messageFile) {		
		if(messageFile.isDirectory()){
			
			messageFile=messageFile.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".json");
			    }
			})[0];
			
		}
		
		String fileContent = null;
		try {
			fileContent=FileUtil.readFile(messageFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isTweet(messageFile)){
			Message message=new TwitterMessage(fileContent) ;
			
			return message;
		}
		return null;	
		
	}
	
	private static boolean isTweet(File messageFile) {
		// TODO Auto-generated method stub
		return true;
	}

	
	

}