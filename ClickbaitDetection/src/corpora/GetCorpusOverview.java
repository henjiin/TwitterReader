package corpora;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import message.Message;
import message.MessageFactory;
import SelfFileUtil.FileFinder;
import twitter4j.Status;
import twitter4j.TwitterException;

public class GetCorpusOverview {
	SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
	private String folderName;

	private String targetFile;

	public GetCorpusOverview(String folderName, String tagetFile) {
		super();
		this.folderName = folderName;
		this.targetFile = tagetFile;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getTagetFile() {
		return targetFile;
	}

	public void setTagetFile(String tagetFile) {
		this.targetFile = tagetFile;
	}

	

	public void generateOverview() throws FileNotFoundException {
		List<File> JSONFileList = new LinkedList<File>();

		JSONFileList = FileFinder.getJsonFileList(folderName);
		System.out.println("Found " + JSONFileList.size()+ " Messages");
		PrintWriter writer =new PrintWriter(targetFile) ;	
		

		for (File file : JSONFileList) {
			try{
			Message message = MessageFactory.getMessage(file);
			Date createdAt = message.getCreatedAt();
			String output = message.getStatusID() + "," + message.getCreator()
					+ "," + sdf.format(createdAt) + "\n";
			writer.write(output);
			}catch(Exception e){
				System.out.println("Error occured in Message " + file.getName());
			}
		}
		writer.close();
	}

}
