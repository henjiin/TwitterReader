package analyst;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class statistics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Reader in = new FileReader(
				"/home/sebastiankopsel/clickbait-korpus.csv");
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		for (CSVRecord record : records) {
		    int prof = record.get("Prof. Hagen").equals("x")? 1:0;
		    int martin = record.get("Martin Potthast").equals("x")? 1:0;
		    int sebastian= record.get("clickbait_class").equals("x")? 1:0;
		}
	}

}
