package learning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.LibSVMLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Experimenter {

	Instances trainData;
	Instances filteredData;
	/**
	 * Object that stores the filter
	 */
	StringToWordVector filter;
	/**
	 * Object that stores the classifier
	 */
	FilteredClassifier classifier;
	static PrintWriter out;

	public void loadDataset(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			ArffReader arff = new ArffReader(reader);
			trainData = arff.getData();
			System.out.println("===== Loaded dataset: " + fileName + " =====");
			reader.close();
		} catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}

	public void iterateOverAttributes() throws Exception {

		int numAttributes = trainData.numAttributes();
		trainData.setClassIndex(0);
		String classF = trainData.attribute(0).name();
		System.out.println(classF);
		// Iterate over all Attributes but the first
		out.write("Feature,Naive, Random Forrest, Logi\n");
		
		for (int activeAttribute = 2; activeAttribute < numAttributes - 1; activeAttribute++) {
			// get attributename
			
			
			
			// Remove all but the active attribute and the clickbait class
			Remove remove = new Remove();
			remove.setAttributeIndices("1," + String.valueOf(activeAttribute));
			remove.setInvertSelection(true);
			remove.setInputFormat(trainData);
			filteredData = Filter.useFilter(trainData, remove);
			
			filter();
			filteredData.setClassIndex(0);
			
			String attributeName = filteredData.attribute(1).name();
			Classifier naive = (Classifier) new NaiveBayes();
			Classifier rand = (Classifier) new RandomForest();
			Classifier logi = (Classifier) new Logistic();
			
			naive.buildClassifier(filteredData);
			rand.buildClassifier(filteredData);
			logi.buildClassifier(filteredData);
			
			Evaluation naiveTest = new Evaluation(filteredData);
			Evaluation randTest = new Evaluation(filteredData);
			Evaluation logiTest = new Evaluation(filteredData);
			
			naiveTest.crossValidateModel(naive, filteredData, 10, new Random(1));			
			randTest.crossValidateModel(rand, filteredData, 10, new Random(1));			
			logiTest.crossValidateModel(logi, filteredData, 10, new Random(1));			

			double naivA=naiveTest.weightedAreaUnderROC();
			double randA=randTest.weightedAreaUnderROC();
			double logiA=logiTest.weightedAreaUnderROC();
			String attributeReport = attributeName+","+naivA+","+randA+","+logiA+"\n";
			System.out.println(attributeReport);
			out.write(attributeReport);
			out.flush();
		}
	}

	private void filter() {
		if (filteredData.checkForStringAttributes()) {
			filter = new StringToWordVector();
			filter.setAttributeIndices("last");
			filter.setLowerCaseTokens(true);
			try {
				filter.setInputFormat(filteredData);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				filteredData = Filter.useFilter(filteredData, filter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String baseData = "";
		if (args.length < 2) {
			baseData = "/home/sebastiankopsel/Data/Majority-Vote-Data/fair-clickbait.arff";
		}
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					"/home/sebastiankopsel/Data/Majority-Vote-Data/solo-feature-report.txt"
					+ "", true)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Experimenter learner = new Experimenter();
		learner.loadDataset(baseData);
		try {
			learner.iterateOverAttributes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.close();
		System.out.println("finished");
	}
	

}
