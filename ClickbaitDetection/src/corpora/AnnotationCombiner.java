package corpora;

import java.io.File;
import java.util.Map;

/**
 * 
 * @author sebastiankopsel
 * This class is meant to combine a given Amount of Annotation Files, and returns a map  with the final annotation for each Message
 * 
 */
public class AnnotationCombiner {
	int numberOfClasses;
	Map<String, Map<String, Integer>> annotations;
	public AnnotationCombiner(int numberOfClasses){
		
	}
	public AnnotationCombiner(int numberOfClasses, String[] annotationFiles){
		
	}
	
	public int getNumberOfClasses(){
		return numberOfClasses;
	}
	
	public void feedAnnotation(String fileName){
		
	}
	public void feedAnnotation(File file){
		
	}
	
	public Map<String, String> getAnnotation(){
		
		return null;
	}
	
	public int getNumEntries(){
		return annotations.size();
	}
	
	 
}
