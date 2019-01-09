/*
 * For CSCI 112, Week 4 Programing Assignment 
 *                - Recursive Directory Duplication
 * Author: Huajing Lin
 * Last update:2/14/2017
 */

package fileioproject;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FileIOProject  extends JFrame implements ActionListener {

    private final JLabel LblInfo;         //used for display information
    private final JLabel LblSource;       //Used to display the source directory
    private final JLabel LblDestination;  //Used to display the Destination directory
    
    private final JButton btnCopy;        //Copy button
    private final JButton btnSource;      //used to choose Source path
    private final JButton btnDestination; //used to choose Destination path
    private final JTree treeSource;       //used to display file list of Source directory.
    private final JTree treeDestination;  //used to display file list of Destination directory.
        
    private final int iHGridAmount;        //HORIZONTAL Grid amount

    public FileIOProject() {
        this.iHGridAmount = 9;
        
        // set the title, size, location and exit behavior for the main frame
        this.setTitle("Week 4 Programing Assignment");
        this.setSize(800, 600);
        this.setLocation(100, 50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //GridBagLayout
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        pane.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        //creat the button to choose source path
        btnSource = new JButton("Source directory");
        btnSource.addActionListener(this);  //button add action listerner
        btnSource.setMnemonic(1);           //set action identification as 1
        c.insets = new Insets(5,5,0,0);     //top and left padding
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(btnSource, c);
        
        // create a label for display Source directory full path
        LblSource = new JLabel("", JLabel.LEFT);
        LblSource.setFont(new Font("Arial", Font.PLAIN, 16));
        LblSource.setForeground(Color.BLUE);
        c.weightx = 0.44;        
        c.gridwidth = iHGridAmount - 1;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(LblSource, c);
        
        // creat the button to choose Destination path
        btnDestination = new JButton("Destination directory");
        btnDestination.addActionListener(this); //button add action listerner
        btnDestination.setMnemonic(2);          //set action identification as 2
        c.weightx = 0.0;        
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(btnDestination, c);
        
        // create a label for dispalying Destination directory full path.
        LblDestination = new JLabel("", JLabel.LEFT);
        LblDestination.setFont(new Font("Arial", Font.PLAIN, 16));
        LblDestination.setForeground(Color.BLUE);
        c.weightx = 0.44;
        c.gridwidth = iHGridAmount - 1;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(LblDestination, c);
        
        // create a label for tiping information
        LblInfo = new JLabel("Click Source Directory button to choose a source directory first.", JLabel.CENTER);
        LblInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        LblInfo.setForeground(Color.RED);
        c.weightx = 0.5;
        c.gridwidth = iHGridAmount;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(LblInfo, c);
        
        // create a label for showing title
        JLabel lblSour = new JLabel("Source:", JLabel.LEFT);
        lblSour.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSour.setForeground(Color.ORANGE);
        c.weightx = 0.1;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(lblSour, c);
        
        // create a label for showing title
        JLabel lblDest = new JLabel("Destination:", JLabel.LEFT);
        lblDest.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDest.setForeground(Color.ORANGE);
        c.weightx = 0.1;
        c.gridwidth = 4;
        c.gridx = 5;
        c.gridy = 3;
        pane.add(lblDest, c);

        // create a tree for showing soure files list.
        treeSource = new JTree();
        treeSource.setEnabled(false);
        c.weightx = 0.45;
        c.weighty = 0.8;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 4;
        c.ipady = 1000;
        pane.add(treeSource, c);
        
        // create a label to separate two trees.
        JLabel lblDire = new JLabel(">>>", JLabel.CENTER);
        lblDire.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDire.setSize(new Dimension(30, 30));
        c.weightx = 0.1;
        c.gridwidth = 1;
        c.gridx = 4;
        c.gridy = 4;
        pane.add(lblDire, c);
        
        // create a tree for showing Destination files list.
        treeDestination = new JTree();
        treeDestination.setEnabled(false);
        c.weightx = 0.45;
        c.weighty = 0.8;
        c.gridwidth = 4;
        c.gridx = 5;
        c.gridy = 4;
        c.ipady = 1000;
        pane.add(treeDestination, c);
        
        //create copy button
        btnCopy = new JButton("Copy directory");
        btnCopy.addActionListener(this);   //button add action listerner
        btnCopy.setMnemonic(3);            //set action identification as 3
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.gridwidth = iHGridAmount;
        c.gridx = 0;
        c.gridy = 5;
        c.ipady = 0;
        pane.add(btnCopy, c);
         
    }// end default struction
    
    //respond buttons' events
    @Override
    public void actionPerformed(ActionEvent e) {
        //get event source and change to button
        JButton source = (JButton) e.getSource();
        
        //get action identification of button
        int btnIndex = source.getMnemonic();
        switch (btnIndex) {
            case 1:
                //From Source button
                ChooseSourceDirectory();
                break;
            case 2:
                //From Destination button
                ChooseDestDirectory();
                break;
            default: {
                try {
                    //From Copy button
                    CopyDirectory();
                } catch (Exception ex) {
                    Logger.getLogger(FileIOProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }// end switch
    }// end actionPerformed
    
    // button event handler for Choosing source path.
    private void ChooseSourceDirectory(){
        //Popping a dialog to choose directory
        LblSource.setText(ChooseDirectory());
        if(LblSource.getText().length()==0)
            return;
        System.out.printf("reading file list of Source Directory...\n");
        LblInfo.setText("reading file list of Source Directory...");
        
        //creat file of source
        File fileRoot = new File(LblSource.getText());
        String rootName = fileRoot.getName();
        
        //if rootName is empty, it is driver's root
        if(rootName.length() == 0){
            //use driver's name as directory name.
            rootName = LblSource.getText();
        }
        //create the tree of file list
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootName);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeCreateChildNodes(fileRoot, root);
        treeSource.setModel(treeModel);
        treeSource.setShowsRootHandles(true);
        treeSource.setEnabled(true);
        int n = treeSource.getRowCount();
        
        //expand all nodes of tree
        TreeExpandAllNodes(treeSource,0,n);
        
        //tips information
        LblInfo.setText("Next step, click Destination Directory button "
                + "to choose a Destination directory.");
    }
    
    // button event handler for Choosing Destination path.
    private void ChooseDestDirectory(){
        //Popping a dialog to choose directory
        LblDestination.setText(ChooseDirectory());
        if(LblDestination.getText().length()==0)
            return;
        System.out.printf("reading file list of Destination Directory...\n");
        LblInfo.setText("reading file list of Destination Directory...");
        
        //refresh Destination Tree
        refreshDestTree();
                
        //tips information
        LblInfo.setText("Last step, click Copy Directory button to copy directory.");
    }// end ChooseDestDirectory()
    
    // button event handler for copying directory.
    private void CopyDirectory() throws Exception{
    
        //create a instance of copy directory class
        CopyDirectory copyDirectory = new CopyDirectory();
        LblInfo.setText("Copying directory ...");
        
        //call copy method of copy directory class;
        //if it return value>0, copy fail.
        int n = copyDirectory.Copy(LblSource.getText(), LblDestination.getText());
        if(n == 1){
            JOptionPane.showMessageDialog(this,
                    "Destination directory cannot be same directory or a subdirectory of source directory.",
                    "Destination error",
                    JOptionPane.ERROR_MESSAGE);
            LblInfo.setText("Copy fail.");
            return;
        }
        else if(n == 2){
            String str = String.format("Source:%s does not exist.", LblSource.getText());
            JOptionPane.showMessageDialog(this,
                    str,
                    "Destination error",
                    JOptionPane.ERROR_MESSAGE);
            LblInfo.setText("Copy fail.");
            return;
        }
        else if(n == 3){//Destination has existed
            //get Destination directory name
            String sourceDir = LblSource.getText();
            sourceDir = sourceDir.substring(sourceDir.lastIndexOf("\\"));
            String str = String.format("Destination: %s%s has existed.", LblDestination.getText(),sourceDir);
            JOptionPane.showMessageDialog(this,
                    str,
                    "Destination error",
                    JOptionPane.ERROR_MESSAGE);
            LblInfo.setText("Copy fail.");
            return;
        }
        else if(n == 4){
            String str = String.format("Destination: %s cannot do a write operation.", LblDestination.getText());
            JOptionPane.showMessageDialog(this,
                    str,
                    "Destination error",
                    JOptionPane.ERROR_MESSAGE);
            LblInfo.setText("Copy fail.");
            return;
        }
        
        //after copying successfully, refreshed destination tree.
        refreshDestTree();
        LblInfo.setText("Copy done.");
    }// end CopyDirectory()
    
    //Popping a dialog to choose a directory and get a path.
    private String ChooseDirectory() {
        //Popping a dialog to choose directory
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select a source directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        String strDirectory = "";
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            strDirectory = chooser.getSelectedFile().toString();
        } else {
            System.out.println("Choose Directory: No Selection");
        }
        return strDirectory;
    }// end ChooseDirectory()
    
    //for JTree to expand all nodes
    private void TreeExpandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }
        // call itself recursively
        if (tree.getRowCount() != rowCount) {
            TreeExpandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }// end TreeExpandAllNodes()
    
    //for JTree to create all child nodes.
    private void treeCreateChildNodes(File fileRoot, DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) {
            return;
        }

        // Traverse each file in this directory to create all nodes recursively.
        for (File file : files) {
            DefaultMutableTreeNode childNode
                    = new DefaultMutableTreeNode(file.getName());
            node.add(childNode);
            if (file.isDirectory()) {
                // call itself recursively
                treeCreateChildNodes(file, childNode);
            }
        }// end for
    }// end treeCreateChildNodes()
    
    // for refreshing JTree's files list of estination
    private void refreshDestTree(){
        if(LblDestination.getText().length()==0)
            return;
        //create a file of destination
        File fileRoot = new File(LblDestination.getText());
        String rootName = fileRoot.getName();
        
        //if rootName is empty, it is driver's root
        if(rootName.length() == 0){
            //use driver's name as directory name.
            rootName = LblDestination.getText();
        }
        
        //create the tree of file list        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootName);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeCreateChildNodes(fileRoot, root);
        treeDestination.setModel(treeModel);
        treeDestination.setShowsRootHandles(true);                
        treeDestination.setEnabled(true);
        
        //expand all nodes of tree
        int n = treeSource.getRowCount();
        TreeExpandAllNodes(treeDestination,0,n);        
    }// end refreshDestTree()
    
    //main method
    public static void main(String[] args) {
        FileIOProject mainJFrame = new FileIOProject();
        mainJFrame.setVisible(true);
    }// end main()
    
}// end class
