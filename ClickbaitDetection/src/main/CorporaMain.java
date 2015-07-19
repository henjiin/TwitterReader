package main;

import java.io.IOException;

import corpora.GetCorpusOverview;
import annotation.Annotation;
import annotation.AnnotationStatistics;

public class CorporaMain {

	public static void main(String[] args) throws IOException {
		
		GetCorpusOverview overview= new GetCorpusOverview("/home/sebastiankopsel/annotation-sebastian/corpora", "/home/sebastiankopsel/annotation-sebastian/overview-with-texst.cvs");
		overview.generateOverview();
		//		Annotation annotation = new Annotation(
//				new String[] {
//						"/home/sebastiankopsel/annotation-sebastian/annotation-test-1.csv",
//						"/home/sebastiankopsel/annotation-sebastian/annotation-test-2.csv" });
//		AnnotationStatistics statistic = new AnnotationStatistics(annotation);
//		System.out.println(statistic.getFullAgreement("strong"));
		
	}

}
