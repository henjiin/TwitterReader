package analyst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;

public class InquirerSplitter {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String root = "/home/sebastiankopsel/Data/Inquirer/";
		Reader in = new FileReader(root + "inquirerbasic_processed.csv");
		BufferedReader reader = new BufferedReader(in);

		String headers = reader.readLine();
		// Split to headers
		StringTokenizer tokenizer = new StringTokenizer(headers, ",");
		// throw away first entry
		tokenizer.nextToken();
		String token = null;

		// Make Files for all Categories
		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(root + token + ".txt", false)));
		}

		String line;

		while ((line = reader.readLine()) != null) {
			tokenizer = new StringTokenizer(line, ",");
			String word = tokenizer.nextToken();
			while (tokenizer.hasMoreTokens()) {
				token=tokenizer.nextToken();
				if (token.length()>1) {
					
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new FileWriter(root + token + ".txt", true)));
					
					word=word.toLowerCase();
					
					out.write(word.split("#")[0]);
					out.write("\n");
					System.out.println("Saved word: "+ word +" into: "+ root + token + ".txt");
					out.close();
				}
			}
		}

		reader.close();

	}

}
