package annotation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import message.Message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sebastiankopsel
 */
public class AnnotationView extends javax.swing.JFrame {

    /**
     * Creates new form AnnotationView
     */
	private AnnotationModel model;
	
    public AnnotationView() {
    	try {
    		System.out.println("Loading model - please be patient this can take a second");
			model=new AnnotationModel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
    	Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
               try {
				model.saveFinalAnnotation();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               }
    	});
    	annotationButtonListener=new AnnoationButtonListener();
        initComponents();
        nextMessage();
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Do you want to Quit?");
               
                if(i==0){
                	try {
						model.saveFinalAnnotation();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    System.exit(0);
                }
            }
        });
        
    }
    
    //Grabs new Messages, and displays it    
    public void nextMessage(){
    	
    	if(model.hasMoreMessages()){
    		
    		activeMessage=model.getNextMessage();
    		displayActiveMessage(); 
    		updateProgressBar();
    	}else{
    		JOptionPane.showMessageDialog(this, "Finished!.");
    		try {
				model.saveFinalAnnotation();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.exit(0);
    	}
    	
    }
    
    private void updateProgressBar() {
		progress.setText(model.getNumAnnotated()+"/"+model.getNumTotal());
		
	}
	private void displayActiveMessage() {
		messageViewer.display(activeMessage);		
	}

	class AnnoationButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent buttonPress) {
			if(buttonPress.getSource().equals(strong)){
				System.out.println(activeMessage.getID()+",strong");
				model.saveAnnotation(activeMessage, "strong");
			}
			if(buttonPress.getSource().equals(some)){			
				System.out.println(activeMessage.getID()+",medium");
				model.saveAnnotation(activeMessage, "medium");
			}
			if(buttonPress.getSource().equals(weak)){	
				System.out.println(activeMessage.getID()+",weak");
				model.saveAnnotation(activeMessage, "weak");
			}
			if(buttonPress.getSource().equals(none)){
				System.out.println(activeMessage.getID()+",none");
				model.saveAnnotation(activeMessage, "none");
			}
			if(buttonPress.getSource().equals(notsure)){
				System.out.println(activeMessage.getID()+",unsure");
				model.saveAnnotation(activeMessage, "unsure");
			}
			nextMessage();
		}
    	
    }
	
	//Shows the message before
	
	class GoBack implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.hasPreviousMessage()){
				activeMessage=model.getPreviousMessage();
				displayActiveMessage();
			}
			
		}	
	}
    
	//Opens the messages target in default browser
	class ShowTarget implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Runtime rt = Runtime.getRuntime();
			try {
				String targetLink=activeMessage.getTargetLink();
				System.out.println(targetLink);
				Process pr = rt.exec("xdg-open "+ targetLink);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
		}
		
	}
 
	@SuppressWarnings("unchecked")
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        
        
        strong = new javax.swing.JButton();
        strong.addActionListener(annotationButtonListener);
        some = new javax.swing.JButton();
        some.addActionListener(annotationButtonListener);
        weak = new javax.swing.JButton();
        weak.addActionListener(annotationButtonListener);
        none = new javax.swing.JButton();
        none.addActionListener(annotationButtonListener);
        notsure = new javax.swing.JButton();
        notsure.addActionListener(annotationButtonListener);
        
        jLabel1 = new javax.swing.JLabel();
        
        viewTarget = new javax.swing.JButton();
        viewTarget.addActionListener(new ShowTarget());
        goBackButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageViewer = new MessageViewer();
        jScrollPane2 = new javax.swing.JScrollPane();
        progress = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        strong.setText("Strong");
        
        some.setText("Medium");

        weak.setText("Weak");

        none.setText("None");

        jLabel1.setText("Allurement");

        notsure.setText("Not Sure");

        viewTarget.setText("View Target");

        goBackButton.setText("Last Message");
        goBackButton.addActionListener(new GoBack());
        
        jScrollPane1.setViewportView(messageViewer);

        jScrollPane2.setViewportView(progress);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(goBackButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addComponent(viewTarget)
                                .addGap(34, 34, 34)
                                .addComponent(notsure)
                                .addGap(55, 55, 55))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(none)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weak)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(some)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(strong))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(strong)
                    .addComponent(some)
                    .addComponent(weak)
                    .addComponent(none)
                    .addComponent(notsure)
                    .addComponent(viewTarget)
                    .addComponent(goBackButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AnnotationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnnotationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnnotationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnnotationView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnnotationView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    private MessageViewer messageViewer;
    private javax.swing.JTextPane progress;
    private javax.swing.JButton goBackButton;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton none;
    private javax.swing.JButton notsure;
    private javax.swing.JButton some;
    private javax.swing.JButton strong;
    private javax.swing.JButton viewTarget;
    private javax.swing.JButton weak;
    private Message activeMessage;
    private AnnoationButtonListener annotationButtonListener;
    // End of variables declaration                   
                
}
