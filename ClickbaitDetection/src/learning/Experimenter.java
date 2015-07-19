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

	/*
	 * Reads a given .arff File
	 */

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

	/*
	 * Iterates over all attributes of the data set and learn only with them
	 * with naive bayes, random forest, and Logarithmic regression
	 */
	public void iterateOverAttributes() throws Exception {

		int numAttributes = trainData.numAttributes();
		trainData.setClassIndex(0);
		String classF = trainData.attribute(0).name();
		System.out.println(classF);
		// Iterate over all Attributes but the first
		out.write("Feature,Naive, Random Forrest, Logi\n");

		for (int activeAttribute = 2; activeAttribute < numAttributes - 1; activeAttribute++) {

			// Remove all but the active attribute and the clickbait class
			Remove remove = new Remove();
			remove.setAttributeIndices("1," + String.valueOf(activeAttribute));
			remove.setInvertSelection(true);
			remove.setInputFormat(trainData);
			filteredData = Filter.useFilter(trainData, remove);

			// Applies String to word vector if the current attribute is a
			// string
			filter();

			// selects the first attribute as training class
			filteredData.setClassIndex(0);

			String attributeName = filteredData.attribute(1).name();

			Classifier naiveBayesClassifier = (Classifier) new NaiveBayes();
			naiveBayesClassifier.buildClassifier(filteredData);
			Evaluation naiveBayesLearner = new Evaluation(filteredData);
			naiveBayesLearner.crossValidateModel(naiveBayesClassifier,
					filteredData, 10, new Random(1));
			double naiveBayesAUC = naiveBayesLearner.weightedAreaUnderROC();

			Classifier randomForestClassifier = (Classifier) new RandomForest();
			randomForestClassifier.buildClassifier(filteredData);
			Evaluation randomForestLearner = new Evaluation(filteredData);
			randomForestLearner.crossValidateModel(randomForestClassifier,
					filteredData, 10, new Random(1));
			double randomForestAUC = randomForestLearner.weightedAreaUnderROC();

			Classifier logisticRegressionClassifier = (Classifier) new Logistic();
			logisticRegressionClassifier.buildClassifier(filteredData);
			Evaluation logisticRegressionLearner = new Evaluation(filteredData);
			logisticRegressionLearner.crossValidateModel(
					logisticRegressionClassifier, filteredData, 10, new Random(
							1));
			double logisticRegressionAUC = logisticRegressionLearner
					.weightedAreaUnderROC();

			// print
			String attributeReport = attributeName + "," + naiveBayesAUC + ","
					+ randomForestAUC + "," + logisticRegressionAUC + "\n";
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
