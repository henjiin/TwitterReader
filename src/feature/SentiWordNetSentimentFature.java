package feature;

import java.io.IOException;

import wekaLearning.SWN3;

public class SentiWordNetSentimentFature extends Feature{
	SWN3 swn;
	public SentiWordNetSentimentFature(){
		
		try {
			swn = new SWN3("/home/sebastiankopsel/Data/swn.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getArffHeader(){
		return "sentiment {verynegative,negative,neutral,positive,verypositve}";

	}

	public String getFeature(String text){
		
		String[] words = text.split("\\s+");
		double totalScore = 0, averageScore = 0;
		for (String word : words) {
			word = word.replaceAll("([^a-zA-Z\\s])", "");
			if (swn.extract(word) == null)
				continue;
			totalScore += swn.extract(word);
		}
		averageScore = totalScore / words.length;

		if (averageScore >= 0.75)
			return "verypositve";
		else if (averageScore > 0.25 && averageScore < 0.5)
			return "positive";
		else if (averageScore >= 0.5)
			return "positive";
		else if (averageScore < 0 && averageScore >= -0.25)
			return "negative";
		else if (averageScore < -0.25 && averageScore >= -0.5)
			return "negative";
		else if (averageScore <= -0.75)
			return "verynegative";
		return "neutral";
	}
}
