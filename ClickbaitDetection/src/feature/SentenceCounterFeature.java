package feature;

import java.util.List;
import java.util.Properties;

import message.Message;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import twitter4j.Status;


public class SentenceCounterFeature extends Feature{
	private StanfordCoreNLP pipeline;
	public SentenceCounterFeature() {		
		Properties props = new Properties();
   	 props.setProperty("annotators", "tokenize, ssplit");
   	 pipeline = new StanfordCoreNLP(props);   	    
	}
	public String getArffHeader(){
		return "sentence_count numeric";
	}
	public String getCVSHeader(){
		return "sentence_count";
	}

	public String getFeature(Status tweet){
		String tweetText=tweet.getText();
		Annotation document = pipeline.process(tweetText);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		return String.valueOf(sentences.size());
	}
	@Override
	public String getFeature(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

}
