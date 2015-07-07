package corpora;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class Annotation {

	public static void generateNewAnnotation(String string, String string2) throws IOException {
		
		String file = SelfFileUtil.FileUtil.readFile(string);
		
		StringTokenizer lineTokenizer = new StringTokenizer(file, "\n");
		FileWriter writer= new FileWriter(string2);
		while(lineTokenizer.hasMoreTokens()){
			String line= lineTokenizer.nextToken();
			StringTokenizer tokenizer= new StringTokenizer(line, ",");
			writer.write(tokenizer.nextToken()+",missing\n");			
		}
		writer.close();
		
		
		
	}

}
