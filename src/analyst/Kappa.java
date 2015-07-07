package analyst;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Kappa {

	public static void main(String[] args) throws IOException {
		Reader in = new FileReader("/home/sebastiankopsel/Data/Experiments/clickbait-annotation-combined.csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		int allRecords=0;
		int aggreeHagenSebastian=0;
		int aggreeHagenMartin=0;
		int aggreeMartinSebastian=0;
		
				
		for (CSVRecord record : records) {
			//System.out.println(record.get("Status_ID"));
			
		    boolean prof = record.get("Prof. Hagen").toLowerCase().startsWith("x");
		    boolean martin = record.get("Martin Potthast").toLowerCase().startsWith("x");
		    boolean sebastian = record.get("Sebastian").toLowerCase().startsWith("x");
		    allRecords++;
		    if(prof&&sebastian)aggreeHagenSebastian++;
		    if(prof&&martin)aggreeHagenMartin++;
		    if(martin&&sebastian)aggreeMartinSebastian++;	    
		    
		}
		System.out.println("allRecords: " + allRecords);
		System.out.println("aggreeHagenSebastian: " + aggreeHagenSebastian);
		System.out.println("aggreeHagenMartin: " + aggreeHagenMartin);
		System.out.println("aggreeMartinSebastian: " + aggreeMartinSebastian);
		
		

	}

}
