package corpora;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class GetCorpusSelection {

	Set<String> USERS = new HashSet<String>(Arrays.asList(new String[] {
			"BBC News (UK)", "The New York Times", "Mashable", "ABC News",
			"CNN", "The Guardian ", "Huffington Post", "Forbes",
			"Bleacher Report", "Fox News", "BuzzFeed", "Yahoo",
			"Daily Mail Online", "ESPN", "Wall Street Journal",
			"Business Insider", "The Telegraph", "Washington Post",
			"The Independent", "NBC News" }));

	String fileFrom;
	String fileTo;

	String dateFrom="15/06/08";
	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	String dateTo="15/06/14";
	
	public GetCorpusSelection() {
		fileFrom = "corpus.cvs";
		fileTo = "corpus-selection.cvs";
	}

	public GetCorpusSelection(String sourceFile) {
		fileFrom = sourceFile;
		fileTo = "corpus-selection.cvs";
	}

	public GetCorpusSelection(String sourceFile, String targetFile) {
		fileFrom = sourceFile;
		fileTo = targetFile;
	}

	public GetCorpusSelection(String[] users) {
		USERS = new HashSet<String>(Arrays.asList(users));
	}

	public Set<String> getUSERS() {
		return USERS;
	}

	public void setUSERS(Set<String> uSERS) {
		USERS = uSERS;
	}

	public String getFileFrom() {
		return fileFrom;
	}

	public void setFileFrom(String fileFrom) {
		this.fileFrom = fileFrom;
	}

	public String getFileTo() {
		return fileTo;
	}

	public void setFileTo(String fileTo) {
		this.fileTo = fileTo;
	}

	public GetCorpusSelection(String sourceFile, String targetFile,
			String[] users) {
		fileFrom = sourceFile;
		fileTo = targetFile;
		USERS = new HashSet<String>(Arrays.asList(users));
	}

	public void getCorpusSelection(){
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(fileFrom));
	
			String line;
			Map<String, LinkedList<String>> set = new HashMap<String, LinkedList<String>>();
			
			
			//Saves for each user the entries in a list
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				String ID = tokenizer.nextToken();
				String user = tokenizer.nextToken();
				String date = tokenizer.nextToken();				
				if (date.compareTo(dateFrom) >= 0
						&& date.compareTo(dateTo) <= 0) {
					if (USERS.contains(user)) {
						if(set.get(user)==null){
							set.put(user, new LinkedList<String>());
						}						
						set.get(user).add(line);						
					}
				}

			}	
			//Shuffle the entries of each user
			
			for(Entry<String, LinkedList<String>> entry:set.entrySet()){
				List<String> userTweets=entry.getValue();				
				Collections.shuffle(userTweets);				
			}
			
			FileWriter writer=new FileWriter(fileTo);
			//Select and Print 100 of each user
			for(Entry<String, LinkedList<String>> entry:set.entrySet()){
				List<String> userTweets=entry.getValue();
				for(int i=0; i<100;i++){
					
					writer.write(userTweets.get(i)+"\n");
				}
				
			}
			writer.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
