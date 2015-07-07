package annotator;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import message.Message;
import message.MessageFactory;
import SelfFileUtil.FileUtil;

public class AnnotationModel {
	private HashMap<String, String> annotation;
	private File corporaDir;
	private List<Message> messages;
	private int totalMessages;
	private int index;

	public AnnotationModel() throws IOException {
		corporaDir = new File("corpora");
		if (!corporaDir.exists() || !corporaDir.isDirectory()) {
			System.out.println("Error!");
			System.exit(0);
		}
		annotation = new HashMap<String, String>();
		messages = new LinkedList<Message>();
		loadCorpus();
		index = 0;
		totalMessages = messages.size();
		System.out.println("Initialized model with " + messages.size()
				+ " entries");
	}

	private void loadCorpus() throws IOException {
		String annotationFile = FileUtil.readFile("annotation.csv");
		StringTokenizer lineTokenizer = new StringTokenizer(annotationFile,
				"\n");
		while (lineTokenizer.hasMoreTokens()) {
			String line = lineTokenizer.nextToken();
			StringTokenizer valueTokenizer = new StringTokenizer(line, ",");
			String messageID = valueTokenizer.nextToken();
			String messageAnnotation = "missing";
			if (valueTokenizer.hasMoreTokens())
				messageAnnotation = valueTokenizer.nextToken();

			File messageFile = null;
			messageFile = getMessageFile(messageID);

			// File found everything okey
			if (messageFile != null) {
				Message message = MessageFactory.getMessage(messageFile);
				messages.add(message);
				annotation.put(messageID, messageAnnotation);
			}
		}

	}

	private File getMessageFile(String messageID) {

		File[] file = corporaDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.equals(messageID);
			}
		});
		if (file.length < 1) {
			return null;
		} else {
			return file[0];
		}
	}

	public Message getNextMessage() {
		Message message;
		if (hasMoreMessages()) {
			message = messages.get(index);
			index++;
			String messageAnnotation = annotation.get(message.getStatusID());
			if (messageAnnotation.equals("missing")) {
				return message;
			}			
			return getNextMessage();
		} else {
			return null;
		}
	}

	public boolean hasMoreMessages() {
		return index < totalMessages ;
	}

	public boolean hasPreviousMessage() {
		return index>0;
	}

	public Message getPreviousMessage() {		
		if (index > 0) {
			
			--index;
			System.out.println(index);
			return messages.get(index);
		}
		return messages.get(index);
	}

	public void saveAnnotation(String messageID, String annotation) {
		this.annotation.put(messageID, annotation);
	}

	public void saveAnnotation(Message message, String annotation) {
		this.annotation.put(message.getStatusID(), annotation);
	}

	public String getAnnotation(String messageID) {
		String annotated = annotation.get(messageID);
		if (annotated == null)
			annotated = "missing";
		return annotated;

	}

	public void saveFinalAnnotation() throws IOException {
		FileWriter writer = null;

		writer = new FileWriter(new File("annotation.csv"));

		for (Entry<String, String> entry : annotation.entrySet()) {
			writer.write(entry.getKey() + "," + entry.getValue() + "\n");
		}
		writer.close();

	}

	public int getNumTotal() {
		return annotation.size();
	}

	public int getNumAnnotated() {
		int sum = 0;
		for (Entry<String, String> entry : annotation.entrySet()) {
			if (!entry.getValue().equals("missing"))
				sum++;
		}
		return sum;
	}

}
