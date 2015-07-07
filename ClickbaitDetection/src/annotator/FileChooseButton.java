package annotator;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class FileChooseButton extends JPanel
   implements ActionListener {
	
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
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      }
    else {
      System.out.println("No Selection ");
      }
     }
   
  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }
    
 
}
