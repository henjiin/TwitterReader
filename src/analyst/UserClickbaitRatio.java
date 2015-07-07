package analyst;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class UserClickbaitRatio {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Reader in = new FileReader("/home/sebastiankopsel/Data/clickbait.csv");
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
		Map<String, Integer> map= new HashMap<String, Integer>();
		for(CSVRecord record:records){
			String name=record.get("By_User");
			if(record.get("clickbait_class").equals("serious"))
			{
			int count = map.containsKey(name) ? map.get(name) : 0;
			map.put(name, count + 1);
			}else{
				int count = map.containsKey(name) ? map.get(name) : 0;
				map.put(name,count);
			}
			
		}
		StringBuilder sb=new StringBuilder();
		Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
		    Entry<String, Integer> entry = iter.next();
		    System.out.println(entry.getValue());
		    
		}
		
		
	}

}
