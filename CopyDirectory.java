/*
 * For CSCI 112, Week 4 Programing Assignment 
 *                - Recursive Directory Duplication
 * Author: Huajing Lin
 * Last update:2/14/2017
 */
package fileioproject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * Copy directory utility Class
 * It can copy directory including subdirectory and file to destination.
 * it can also copy between drives.
*/
public class CopyDirectory {

    //default constructor function
    public void CopyDirectory() {

    }

    /* public copy mothod, copy directory from source to destination
     * parameter source is source Directory Absolute path
     * parameter destination is destination Directory Absolute path
     */
    public int Copy(String source, String destination) throws Exception {
        // if destination path contains source path, cannot copy recursively.
        if(destination.contains(source)){
            return 1;
        }
        
        // if source path is not root of disk driver.
        if (!source.contains(":")) {
            File fileSourceDir = new File(source);
            
            // if Source directory does not exist.
            if (!fileSourceDir.exists()) {
                System.out.printf("Source %s does not exist.\n", source);
                return 2;
            }
        }
        //get source directory name
        String sourceDir = source.substring(source.lastIndexOf("\\")); 
        
        // if source directory's name is "\", it is a root of disk driver.
        if((sourceDir.length() == 1)&& (sourceDir.contains("\\"))){
            // Use source disk driver's name as directory name.
            sourceDir += source.substring(0,1);
        }
        
        // if the source directory's path is same the destination.
        if(source.equalsIgnoreCase(destination + sourceDir))
            return 1;
        
        // create a file of destination directory
        // destination directory is destination path plus source directory.
        File fileDestDir = new File(destination + sourceDir);
        
        // if destination directory exists, cancel copy.
        if (fileDestDir.exists()) {
            System.out.printf("Destination %s has exist.\n", fileDestDir.getPath());
            return 3;
        }
        
        // creat destination directory
        if (!fileDestDir.mkdir()) {
            System.out.printf("Cannot create a directory: %s\n", fileDestDir.getPath());
            return 4;
        }
        System.out.printf("mkdir: %s\n", fileDestDir.getPath());
        
        // copy the Items of source directory to destination directory.
        CopyItems(source, fileDestDir.getPath());
        return 0;
    }

    /*
     * CopyItems method is a recursively copy function.
     */
    private void CopyItems(String source, String destination) throws Exception {

        // create a file list.
        File[] fileList;
        
        // Create a File class object as source of copying.
        File fileSource = new File(source);

        if (fileSource.isFile()) {
            // if source is a file, copy file to destination.
            copyFile(source, destination);
            
        } // end if ( fileSource.isFile() )        
        else {  // if the source is a directory or the root of disk driver

            // get this directory's files list. 
            fileList = fileSource.listFiles();
            
            // create a string variable that used to form destination string.
            String strDest ="";
            
            // Traverse each file in this directory.
            for (File entry : fileList) {

                // if it is a file, copy file to destination.
                if (entry.isFile()) {
                    strDest = destination + "\\" + entry.getName();
                    copyFile(entry.getAbsolutePath(), strDest);
                } else {
                    //if it is a directory, create the directory name in the 
                    //destination directory first.
                    strDest = destination + "\\" + entry.getName();
                    File fileDir = new File(strDest);
                    fileDir.mkdir();
                    System.out.printf("mkdir: %s\n", fileDir.getPath());
                    
                    //recursively call 
                    CopyItems(entry.getAbsolutePath(), strDest);
                } // end if (!entry.isFile())

            } // end for
            
        } // end if ( fileSource.isDirectory() )
        
    }// end CopyItems()
    
    // copy file method, copy file from source path to destination path.
    private void copyFile(String source, String destination) throws Exception
    {
        // Create two file variables for the source and destination files       
        File sourceFile = null;
        File destFile = null;

        // Create two stream variables
        FileInputStream sourceStream = null;
        FileOutputStream destStream = null;

        // Create two buffering variables
        BufferedInputStream bufferedSource = null;
        BufferedOutputStream bufferedDestination = null;

        try {

            // new two file objects for the source and destination files
            sourceFile = new File(source);
            destFile = new File(destination);

            // new two file streams for the source and destination files
            sourceStream = new FileInputStream(sourceFile);
            destStream = new FileOutputStream(destFile);

            // buffer the file streams -- set the buffer sizes to 4K
            bufferedSource = new BufferedInputStream(sourceStream, 4096);
            bufferedDestination = new BufferedOutputStream(destStream, 4096);

            // use an integer to transfer data between files
            int iByte = 0;

            // Output information of copying            
            System.out.println("=== copying " + source);
            System.out.println("=== tto      " + destination);

            // read a byte, checking for end of file (-1 is returned by read at EOF)
            while ((iByte = bufferedSource.read()) != -1) {

                // write a byte 
                bufferedDestination.write(iByte);

            } // end while

        } catch (IOException e) {        
            e.printStackTrace();
            System.out.println(" An unexpected I/O error occurred.");
        
        } finally {
            
            // close file streams 
           if (bufferedSource != null) 
               bufferedSource.close();
           
           if (bufferedDestination != null) 
            bufferedDestination.close();
           
           destStream.close();
           sourceStream.close();
        } // end finally       
        
    } // end copyFile
}// end class
