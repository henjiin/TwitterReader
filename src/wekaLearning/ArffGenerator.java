package wekaLearning;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import feature.AmountOfDotsFeature;
import feature.Feature;
import feature.HasExclamationMarkFeature;
import feature.HasQuestionMarkFeature;
import feature.IsEmotionalFeature;
import feature.IsReaderReferingFeature;
import feature.IsSelfReferingFeature;
import feature.SentimentFeature;
import feature.StartsWithNumberFeature;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitterReader.TweetUtil;

public class ArffGenerator {

	static boolean removeLinks = true;
	static boolean removeRT = true;
	static boolean removeTwitterUsers = true;
	public static String arfFileName;
	static String arffFileName;
	LinkedList<Feature> usedFeatures;

	static String HEADER = "@RELATION clickbait_detection\n\n";

	public ArffGenerator() {
		usedFeatures = new LinkedList<Feature>();
		usedFeatures.add(new HasExclamationMarkFeature());
		usedFeatures.add(new HasQuestionMarkFeature());
		usedFeatures.add(new AmountOfDotsFeature());
		usedFeatures.add(new IsSelfReferingFeature());
		usedFeatures.add(new IsReaderReferingFeature());
		usedFeatures.add(new StartsWithNumberFeature());
		usedFeatures.add(new IsEmotionalFeature());
		usedFeatures.add(new SentimentFeature());
	}

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
	}

	private void writeHeader() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, false)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(HEADER);
		writer.write("@ATTRIBUTE clickbait_class {clickbait,serious}\n");
		writer.write("@ATTRIBUTE tweet_text String\n");
		for (Feature feature : usedFeatures) {
			writer.write("@ATTRIBUTE " + feature.getArffHeader() + "\n");
		}
		writer.write("@data\n\n");
		writer.flush();
		writer.close();
	}

	private String buildObject(Status tweet, String learningClass) {
		String tweetText = tweet.getText();
		tweetText = TweetUtil.cleanTweetText(tweetText);

		StringBuilder objectAttributes = new StringBuilder();

		objectAttributes.append("\'");
		objectAttributes.append(tweetText);
		objectAttributes.append("\',");
		Iterator<Feature> featureIterator = usedFeatures.iterator();

		while (featureIterator.hasNext()) {
			objectAttributes.append(featureIterator.next()
					.getFeature(tweetText));
			if (featureIterator.hasNext())
				objectAttributes.append(",");
		}

		return objectAttributes.toString();
	}

	public void writeArff(String filename, String learningClass) {
		List<String> list = TweetUtil.getJSONFileList(filename);

		PrintWriter writer = null;

		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					arffFileName, true)));

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		for (String tweetJSON : list) {
			try {
				Status tweet = TweetUtil.getTweetFromJSON(tweetJSON);
				String object = buildObject(tweet, learningClass);
				writer.write(object.toString());
			} catch (IOException | TwitterException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.flush();
		writer.close();

	}

	public static void main(String[] args) {
		arffFileName = "/home/sebastiankopsel/Data/Serious/clickbait.arff";
		ArffGenerator gen = new ArffGenerator();
		gen.writeHeader();
		gen.writeArff("/home/sebastiankopsel/Data/Serious/clickbait",
				"clickbait");
		gen.writeArff("/home/sebastiankopsel/Data/Serious/serious", "serious");

		System.out.println("Finished");
	}

}
