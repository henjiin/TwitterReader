package SelfFileUtil;

import java.io.File;
import java.io.FilenameFilter;

public class FileFinder {
	File dictionary;
	public FileFinder(String folderName){
		dictionary = new File(folderName);		
	}
	
	public File[] getFileById(String ID){
		// your directory
		
		File[] matchingFiles = dictionary.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith(ID);
		    }
		});
		if(matchingFiles.length<1)return null;
		else return matchingFiles;
	}
	
}
