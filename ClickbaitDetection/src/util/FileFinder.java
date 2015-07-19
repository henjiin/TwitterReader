package util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileFinder {
	File dictionary;
	public FileFinder(String folderName){
		dictionary = new File(folderName);		
	}
	
	public File getFileById(String ID){
		// your directory
		
		File[] matchingFiles = dictionary.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.equals(ID);
		    }
		});
		if(matchingFiles.length<1)return null;
		else return matchingFiles[0];
	}
	

	public static List<File> getJsonFileList(String folderName) {
	
		File dir = new File(folderName);
		List<String> filo = Arrays.asList(new File(folderName).list());
		
		String[] extensions = new String[] {"json"};		
		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);		
		System.out.println("Finished getting files");
		return files;
	}
	
}
