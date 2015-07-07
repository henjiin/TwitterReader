package corpora;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import twitter4j.Status;
import twitter4j.TwitterException;
import feature.ByUserFeature;
import feature.ContainsGroupFeature;
import feature.Feature;
import feature.MediaFeature;
import feature.StatusIDFeature;
import feature.TweetFullLengthFeature;
import feature.TweetShortenedLengthFeature;
import feature.TweetTextFeature;

public class ArffGenerator {

	public static String arfFileName;
	static String arffFileName;
	static LinkedList<Feature> usedFeatures;
	static final int CSV = 1;
	static final int ARFF = 2;
	static int dataFormat;
	static String RELATION_NAME = "@RELATION clickbait_detection\n\n";
	static final char CVS_TEXT_DELIMITER = '\"';
	static final char ARFF_TEXT_DELIMITER = '\'';
	String[] selfReferingWords = { "i", "me", "mine", "we", "our" };
	String[] readerReferingWords = { "you", "your", "yours", "yourselfes",
			"our" };

	public ArffGenerator() {
		dataFormat = ARFF;
		usedFeatures = new LinkedList<Feature>();
		
		TweetTextFeature tweetTextFeature = new TweetTextFeature();
		if (dataFormat == CSV)
			tweetTextFeature.setTextDelimiter(CVS_TEXT_DELIMITER);
		if (dataFormat == ARFF)
			tweetTextFeature.setTextDelimiter(ARFF_TEXT_DELIMITER);
		usedFeatures.add(new StatusIDFeature());
		usedFeatures.add(new ByUserFeature());
		
		usedFeatures.add(tweetTextFeature);
		usedFeatures.add(new TweetFullLengthFeature());
		usedFeatures.add(new TweetShortenedLengthFeature());
		
		usedFeatures.add(new MediaFeature());
		/*
		try {
			usedFeatures.addAll(GetDictFeatures("/home/sebastiankopsel/Data/Other_Sources/Dict"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		usedFeatures.add(new RatioStopToAllwordsFeature());		
		usedFeatures.add(new HasExclamationMarkFeature());
		usedFeatures.add(new HasQuestionMarkFeature());
		usedFeatures.add(new DotCounterFeature());
		usedFeatures.add(new StartsWithNumberFeature());
		
		
		ContainsGroupFeature isSelfReferring = new ContainsGroupFeature(
				"Is_Self_Referential", Arrays.asList(selfReferingWords));
		usedFeatures.add(isSelfReferring);

		ContainsGroupFeature IsReaderReferring = new ContainsGroupFeature(
				"Is_Reader_Reffering", Arrays.asList(readerReferingWords));		
		usedFeatures.add(IsReaderReferring);
		
		
		try {
			ContainsGroupFeature containsAbbreviation;
			containsAbbreviation = new ContainsGroupFeature(
					"containsAbbreviation", "/home/sebastiankopsel/Data/Other_Sources/Dict-abbrev-oxford.txt");
			containsAbbreviation.setReturnDelimiter(true);
			usedFeatures.add(containsAbbreviation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		usedFeatures.add(new ReadabilityFeature());
		usedFeatures.add(new MaxWordLengthFeature());		
		usedFeatures.add(new HashtagFeature());		
		usedFeatures.add(new IsRetweetedFeature());		
		usedFeatures.add(new MentionedUsersFeature());
		usedFeatures.add(new LengthOfWebpageFeature());
		// Disabled while testing because of long execution time
		usedFeatures.add(new SentimentFeature());
		
		//useless features
		//
		usedFeatures.add(new HasSymbolsFeature());
		usedFeatures.add(new IsPossiblySensisitveFeature());
		*/
	}

	/*
	 * TODO Change so one can give config with JSON file
	 */
/*
	public ArffGenerator(String filePathToJSON) {
		this();
		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader(
					"/home/sebastiankopsel/Documents/test.json"));

			JSONObject jsonObject = (JSONObject) obj;

			String name = (String) jsonObject.get("Name");
			String author = (String) jsonObject.get("Author");
			JSONArray companyList = (JSONArray) jsonObject.get("Company List");

			System.out.println("Name: " + name);
			System.out.println("Author: " + author);
			System.out.println("\nCompany List:");
			Iterator<String> iterator = companyList.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	private void writeArffHeader() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, false)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(RELATION_NAME);
		writer.write("@ATTRIBUTE clickbait_class {clickbait,serious}\n");
		for (Feature feature : usedFeatures) {
			writer.write("@ATTRIBUTE " + feature.getArffHeader() + "\n");
		}
		
		writer.write("@data\n");
		writer.flush();
		writer.close();
	}

	private void writeCVSHeader() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, false)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write("clickbait_class");
		for (Feature feature : usedFeatures) {

			writer.write(feature.getCVSHeader());
			writer.write(",");
		}
		
		writer.write("\n");
		writer.flush();
		writer.close();
	}

	private static String buildObject(Status tweet, String learningClass) {
		
		StringBuilder objectAttributes = new StringBuilder();
		objectAttributes.append(learningClass);
		Iterator<Feature> it= usedFeatures.iterator();
		objectAttributes.append(",");
		while(it.hasNext())
		{
			
			String featureValue = it.next().getFeature(tweet);			
			objectAttributes.append(featureValue);
			if(it.hasNext())
			objectAttributes.append(",");
		
		}
		
		objectAttributes.append("\n");

		return objectAttributes.toString();
	}

	public static LinkedList<String> getObjects(String folderName) {
		LinkedList<String> objects = new LinkedList<String>();
		List<String> list = TweetUtil.getJSONFileList(folderName);
		String objectClass = new File(folderName).getName();
		for (String tweetJSON : list) {
			try {
				Status tweet = TweetUtil.getTweetFromJSON(tweetJSON);
				String object = buildObject(tweet, objectClass);
				objects.add(object);

			} catch (IOException | TwitterException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return objects;
	}

	public static LinkedList<String> getAllObjects(String folderName) {
		File directory = new File(folderName);
		LinkedList<File> folders = new LinkedList<File>(Arrays.asList(directory
				.listFiles()));
		LinkedList<String> objects = new LinkedList<String>();
		for (File folder : folders) {
			objects.addAll(getObjects(folder.toString()));
		}
		return objects;

	}

	public static void writeListToFile(List<String> list, String fileName) {
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, true)));

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		for (String object : list) {
			writer.write(object);
		}
		writer.flush();
		writer.close();
	}
	
	public List<Feature> GetDictFeatures(String folderName) throws IOException{
		List<Feature> dictFeatures= new LinkedList <Feature>();		
		//get .txt files from folder
		File[] files = new File(folderName).listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		for (File file : files) {			
			String featureName = file.getName().replaceFirst("[.][^.]+$", "");			
			dictFeatures.add(new ContainsGroupFeature("DICT-"+featureName, file.getAbsolutePath()));
		}

		return dictFeatures;
		
	}

	public static void main(String[] args) {
		arffFileName = "/home/sebastiankopsel/Data/TestData/fair-clickbait.arff";
		ArffGenerator gen = new ArffGenerator();
		if (dataFormat == CSV)
			gen.writeCVSHeader();
		if (dataFormat == ARFF)
			gen.writeArffHeader();
		Random rnd= new Random(1234);
		LinkedList<String> objects = getAllObjects("//home/sebastiankopsel/Data/Majority-Vote-Data/Corpus");
		Collections.shuffle(objects, rnd);
		writeListToFile(objects, arfFileName);
		System.out.println("Finished");
	}

}
