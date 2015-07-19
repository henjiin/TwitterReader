package annotation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import util.FileUtil;

/**
 * 
 * @author sebastiankopsel
 *
 * 
 */
public class Annotation {
	Set<String> classes;
	//Internal representation of the Annotation
	Map<String, Map<String, Integer>> annotations;
	String[] annotationFiles;
	public Annotation(){
		classes=new HashSet<String>();
		annotations = new HashMap<String,Map<String, Integer>>();
		
	}
	public Annotation(String[] annotationFiles) throws IOException{
		this();
		this.annotationFiles=annotationFiles;
		for(int i=0; i<annotationFiles.length; i++){
			BufferedReader reader = new BufferedReader(new FileReader(annotationFiles[i]));
			String line;
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				String ID = tokenizer.nextToken();
				String anntotation= tokenizer.nextToken();
				classes.add(anntotation);				
				//No annotation for this ID yet
				if(annotations.get(ID)==null){				
					Map map= new HashMap<String, Integer>();
					map.put(anntotation, 1);
					annotations.put(ID, map);					
				}
				else{
					if(annotations.get(ID).get(anntotation)==null){
						annotations.get(ID).put(anntotation, 1);
					}
					else{
						int count = annotations.get(ID).get(anntotation);
						annotations.get(ID).put(anntotation, count+1);
					}
				}
			}
			reader.close();
		}
	}
		
	public void feedAnnotation(String fileName){
		
	}
	public void feedAnnotation(File file){
		
	}
	public void put(String id, Map<String, Integer> annotation ){
		annotations.put(id, annotation);
		for(Entry<String, Integer> entry: annotation.entrySet()){
			classes.add(entry.getKey());
		}
	}
	
	public HashMap<String, Map<String, Integer>> getAnnotations(){
		return new HashMap<String, Map<String, Integer>>(annotations);
	}

	public int getNumEntries(){
		return annotations.size();
	}

	public Set<String> getClasses() {
		return classes;
	}
	public void printReducedForm(String fileName) throws IOException{
		
		
		
	}
	


}
