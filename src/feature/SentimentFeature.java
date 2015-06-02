package feature;

import wekaLearning.NLP;

public class SentimentFeature extends Feature {
	private NLP nlp;
	public SentimentFeature() {
		nlp = new NLP();
		nlp.init();
	}
	
public String getArffHeader(){
	return "sentiment {verynegative,negative,neutral,positive,verypositve}";
}

public String getFeature(String text){
	int sentiment = nlp.findSentiment(text);
	switch (sentiment) {
	case 0:
		return "verynegative";
	case 1:
		return "negative";
	case 2:
		return "neutral";
	case 3:
		return "positive";
	case 4:
		return "verypositve";
	default:
		return "neutral";
	}
}
}

