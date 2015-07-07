package learning;
//package wekaLearning;
//import weka.core.Instances;
//import weka.filters.Filter;
//import weka.filters.unsupervised.attribute.Remove;
//import weka.filters.unsupervised.attribute.StringToWordVector;
//import weka.classifiers.Evaluation;
//
//import java.util.Random;
//
//import weka.classifiers.bayes.NaiveBayes;
//import weka.classifiers.meta.FilteredClassifier;
//import weka.core.converters.ArffLoader.ArffReader;
//
//import java.io.*;
//
///**
// * This class implements a simple text learner in Java using WEKA.
// * It loads a text dataset written in ARFF format, evaluates a classifier on it,
// * and saves the learnt model for further use.
// * @author Jose Maria Gomez Hidalgo - http://www.esp.uem.es/jmgomez
// * @see MyFilteredClassifier
// */
//public class Learner {
//
//	/**
//	 * Object that stores training data.
//	 */
//	Instances trainData;
//	Instances filteredData;
//	/**
//	 * Object that stores the filter
//	 */
//	StringToWordVector filter;
//	/**
//	 * Object that stores the classifier
//	 */
//	FilteredClassifier classifier;
//		
//	/**
//	 * This method loads a dataset in ARFF format. If the file does not exist, or
//	 * it has a wrong format, the attribute trainData is null.
//	 * @param fileName The name of the file that stores the dataset.
//	 * @throws Exception 
//	 */
//	public void filterToOnlyAttribute(int featureInded) throws Exception{
//		Remove remove = new Remove();
//		remove.setAttributeIndices(String.valueOf(featureInded));
//	    remove.setInvertSelection(true);
//	    remove.setInputFormat(trainData);
//	    filteredData = Filter.useFilter(trainData, remove);
//	    System.out.println(filteredData);	
//	  
//	}
//	
//	public void loadDataset(String fileName) {
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(fileName));
//			ArffReader arff = new ArffReader(reader);
//			trainData = arff.getData();
//			System.out.println("===== Loaded dataset: " + fileName + " =====");
//			reader.close();
//		}
//		catch (IOException e) {
//			System.out.println("Problem found when reading: " + fileName);
//		}
//	}
//	
//	/**
//	 * This method evaluates the classifier. As recommended by WEKA documentation,
//	 * the classifier is defined but not trained yet. Evaluation of previously
//	 * trained classifiers can lead to unexpected results.
//	 */
//	
//	
//	public void evaluateAll() {
//		try {
//			
//			trainData.setClassIndex(0);
//			filter = new StringToWordVector();			
//			classifier = new FilteredClassifier();
//			classifier.setFilter(filter);
//			classifier.setClassifier(new NaiveBayes());
//			Evaluation eval = new Evaluation(trainData);
//			eval.crossValidateModel(classifier, trainData, 10, new Random(1));
//			System.out.println(eval.toSummaryString());
//			System.out.println(eval.toClassDetailsString());
//			System.out.println("===== Evaluating on filtered (training) dataset done =====");
//		}
//		catch (Exception e) {
//			System.out.println("Problem found when evaluating");
//		}
//	}
//	
//	private void applyStringtoWordVector() {
//		
//		filter = new StringToWordVector();
//		filter.setAttributeIndices("last");
//		
//	}
//
//	public void evaluateFiltered(){
//		filteredData.setClassIndex(0);
//		if(filteredData.checkForStringAttributes()) applyStringtoWordVector();
//		Evaluation eval = new Evaluation(filteredData);
//		classifier = new FilteredClassifier();
//		filter=new NulFilter();
//		classifier.setFilter(filter);
//		classifier.setClassifier(new NaiveBayes());
//
//	}
//	
//	/**
//	 * This method trains the classifier on the loaded dataset.
//	 */
//	public void learn() {
//		try {
//			
//			trainData.setClassIndex(0);
//			filter = new StringToWordVector();
//			filter.setAttributeIndices("last");
//			classifier = new FilteredClassifier();
//			classifier.setFilter(filter);
//			classifier.setClassifier(new NaiveBayes());
//			classifier.buildClassifier(trainData);
//			// Uncomment to see the classifier
//			// System.out.println(classifier);
//			System.out.println("===== Training on filtered (training) dataset done =====");
//		}
//		catch (Exception e) {
//			System.out.println("Problem found when training");
//		}
//	}
//	
//	public void rate(){
//		
//	}
//	
//	/**
//	 * This method saves the trained model into a file. This is done by
//	 * simple serialization of the classifier object.
//	 * @param fileName The name of the file that will store the trained model.
//	 */
//	public void saveModel(String fileName) {
//		try {
//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
//            out.writeObject(classifier);
//            out.close();
// 			System.out.println("===== Saved model: " + fileName + " =====");
//        } 
//		catch (IOException e) {
//			System.out.println("Problem found when writing: " + fileName);
//		}
//	}
//	
//	/**
//	 * Main method. It is an example of the usage of this class.
//	 * @param args Command-line arguments: fileData and fileModel.
//	 */
//	public static void main (String[] args) {
//	
//		Learner learner;
//		if (args.length < 2)
//			System.out.println("Usage: java MyLearner <fileData> <fileModel>");
//		else {
//			
//			learner = new Learner();
//			learner.loadDataset(args[0]);
//			// Evaluation mus be done before training
//			// More info in: http://weka.wikispaces.com/Use+WEKA+in+your+Java+code
//			learner.evaluate();
//			learner.learn();
//			learner.saveModel(args[1]);
//		}
//	}
//}

class Learner{
	
}