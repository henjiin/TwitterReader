package annotator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import message.Message;
import message.MessageFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sebastiankopsel
 */
public class AnnotatorView extends javax.swing.JFrame {

	class FileChooseButton extends JPanel implements ActionListener {

		JFileChooser chooser;

		public void actionPerformed(ActionEvent e) {
			int result;
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Please select the Corpus Message Folder");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			chooser.setAcceptAllFileFilterUsed(false);
			//
			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				System.out.println("getCurrentDirectory(): "
						+ chooser.getCurrentDirectory());

				System.out.println("getSelectedFile() : "
						+ chooser.getSelectedFile());
				corpusFolderName = chooser.getSelectedFile();
				contentLoader = new ContentLoader(corpusFolderName.getAbsolutePath());
			} else {
				System.out.println("No Selection ");
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(200, 200);
		}

	}

	class AnnotationSaver implements ActionListener {
		FileWriter writer;
		Map<String, String> annotations;		
		public AnnotationSaver() {
			System.out.println("Init AnnotationSaver");
			annotations= new HashMap<String , String>();		
		}

		public void actionPerformed(ActionEvent e) {			
			
				if (e.getSource() == clickbaitButton) {			
					System.out.println(annotations.size());
					annotations.put(messageViewer.getCurrentMessage().getStatusID(), "click");
					
					System.out.println(messageViewer.getCurrentMessage().getStatusID()+ " clickbait");
				}
				if (e.getSource() == seriousButton) {
					System.out.println(annotations.size());
					annotations.put(messageViewer.getCurrentMessage().getStatusID(), "serious");
					System.out.println(messageViewer.getCurrentMessage().getStatusID()+ " serious");
				}				
			
			contentLoader.getNextMessage();
			contentLoader.displayCurrentMessage();
		}
		private void saveAnnotation(){
			Iterator<Entry<String, String>> iter = annotations.entrySet().iterator();
			File file= new File("user-"+user+".csv");
			System.out.println(annotations.size());
			try {
				FileWriter writer =new FileWriter(file);
				for (Map.Entry entry : annotations.entrySet()) {
				    System.out.println(entry.getKey() + ", " + entry.getValue());
				    writer.write(entry.getKey()+","+entry.getValue()+"\n");
				}

				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	class ContentLoader  {
		File folderWithMessages;
		
		Iterator<File> listIterator;
		
		public ContentLoader() {			
				new FileChooseButton().actionPerformed(null);
				
		}

		public ContentLoader(String pathToMessages) {
			System.out.println("1");
			System.out.println("Loading content from: " + pathToMessages);
			folderWithMessages = new File(pathToMessages);
			messageFiles = new LinkedList<File>(
					Arrays.asList(folderWithMessages.listFiles()));
			listIterator = messageFiles.iterator();
			System.out.println("Found "+ messageFiles.size()+ " files");
		}
		public ContentLoader (File messagesToAnnotate){
			
			
		}


		private void displayCurrentMessage() {
			messageViewer.display(messages.getLast());
			window.revalidate();
			System.out.println(AnnotatorView.this.getWidth()+"  "+ AnnotatorView.this.getHeight());
			window.repaint();
		}

		public boolean hasMoreMassages() {
			return listIterator.hasNext();
		}

		public Message getNextMessage() {	
			System.out.println("2");
			if(messageFiles==null)
				System.out.println("Message File Folder empty");
			if(listIterator==null)
				listIterator=messageFiles.iterator();
			if (listIterator.hasNext()){
				
				File nextFile=listIterator.next();
				if(nextFile.isDirectory()){
					System.out.println("hi");
					File messageFile=getJsonFromDir(nextFile);
					Message message=parseFile(messageFile);
					messages.add(message);
					return message;
				}				
			}			
			return null;
		}
		private Message parseFile(File JSON){
			return  MessageFactory.getMessage(JSON);
		}
		File getJsonFromDir(File folder){
			return folder.listFiles(new FilenameFilter() {						
				@Override
				public boolean accept(File dir, String name) {							
					return name.toLowerCase().endsWith(".json");
				}
			})[0];
		}

	}

	public AnnotatorView() {
		messages=new LinkedList<Message>();
		messageViewer=new MessageViewer();
		
		try {
			getUserName();
			contentLoader=new ContentLoader();
			display();
			
			/*
			 * contentLoader = new ContentLoader(
			 * "/home/sebastiankopsel/Azmodan-Corpus");
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getUserName() {
		user = (String) JOptionPane.showInputDialog(window, "Hi",
				JOptionPane.PLAIN_MESSAGE

		);
		System.out.println(user);

	}

	private void noMoreMessages() {
		JOptionPane.showMessageDialog(window, "No more Messages",
				"No more Messages", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 * 
	 * @throws IOException
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void display() throws IOException {
		
		window = new javax.swing.JFrame();
		panel = new javax.swing.JPanel();
		clickbaitButton = new javax.swing.JButton();
		seriousButton = new javax.swing.JButton();
		notSureButton = new javax.swing.JButton();
		htmlScrollbar = new javax.swing.JScrollPane();
		
		
		
		
		annotationSaver=new AnnotationSaver();
		javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(
				window.getContentPane());
		window.getContentPane().setLayout(jFrame1Layout);
		jFrame1Layout.setHorizontalGroup(jFrame1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400,
				Short.MAX_VALUE));

		jFrame1Layout.setVerticalGroup(jFrame1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300,
				Short.MAX_VALUE));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		clickbaitButton.setText("Clickbait");
		clickbaitButton.addActionListener(annotationSaver);
		
		seriousButton.setText("Serious");
		seriousButton.addActionListener(annotationSaver);
		
		
		notSureButton.setText("Not Sure");
		notSureButton.addActionListener(annotationSaver);
		
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				panel);
		panel.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addGap(70, 70, 70)
										.addComponent(clickbaitButton)
										.addGap(18, 18, 18)
										.addComponent(notSureButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												15, Short.MAX_VALUE)
										.addComponent(seriousButton)
										.addGap(90, 90, 90)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap(36, Short.MAX_VALUE)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																seriousButton)
														.addComponent(
																clickbaitButton)
														.addComponent(
																notSureButton))));

		
		contentLoader.getNextMessage();
		contentLoader.displayCurrentMessage();
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup().addContainerGap()
								.addComponent(messageViewer).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(panel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(messageViewer,
										javax.swing.GroupLayout.DEFAULT_SIZE,		
										216, Short.MAX_VALUE).addContainerGap()));
		this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Do you want to Quit?");
               
                if(i==0){
                	annotationSaver.saveAnnotation();
                    System.exit(0);
                }
            }
        });
		addMenu();
		validate();
		pack();
		setSize(640,734);
	}// </editor-fold>

	private void addMenu() {

		JMenuBar menubar = new JMenuBar();
		// ImageIcon icon = new ImageIcon("exit.png");

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit");
		JMenuItem chooseFileItem = new JMenuItem("Select Folder");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		chooseFileItem.addActionListener(new FileChooseButton());
		file.add(chooseFileItem);
		file.add(eMenuItem);

		menubar.add(file);

		setJMenuBar(menubar);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(AnnotatorView.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(AnnotatorView.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(AnnotatorView.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(AnnotatorView.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new AnnotatorView().setVisible(true);

			}
		});

	}

	// Variables declaration - do not modify
	private javax.swing.JButton clickbaitButton;
	private javax.swing.JButton seriousButton;
	private javax.swing.JButton notSureButton;
	private javax.swing.JFrame window;
	private javax.swing.JPanel panel;
	private javax.swing.JScrollPane htmlScrollbar;
	private MessageViewer messageViewer;
	private String user;
	private ContentLoader contentLoader;
	private AnnotationSaver annotationSaver;
	private String defaultLocation = "/home/sebastiankopsel/Azmodan-Corpus";
	private File corpusFolderName;    
    private LinkedList<Message> messages;
    LinkedList<File> messageFiles;
	// End of variables declaration
}
