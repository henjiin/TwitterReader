package corpora;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CorporaMain {

	public static void main(String[] args) {
		
		GetCorpusOverview batman =new GetCorpusOverview("corpus", "corpus-overview.cvs");
		
		try {
			batman.generateOverview();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GetCorpusSelection inquisitor = new GetCorpusSelection("corpus-overview.cvs", "corpus-selection.cvs" );
		inquisitor.getCorpusSelection();
		
		try {
			Annotation.generateNewAnnotation("corpus-selection.cvs","annotation.cvs" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
