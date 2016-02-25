/**
 * 
 */

package iplom;

import java.io.*;
import static java.lang.System.out;

public class Test {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    /* ------------- Start time of the program ------------- */
    long startTime = System.currentTimeMillis();
    /* ------------- Start time of the program ------------- */
    
    /* ---------------- Output the printed to a file  ---------------- */
    try {
      System.setOut(new PrintStream(new FileOutputStream("/home/edghklj/workspace/github/iplom-java/output.txt")));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
    /* ---------------- Output the printed to a file  ---------------- */
    
    
    
    String fileName = "/home/edghklj/workspace/github/messages.1";
    IPLoM logAnalyzer = new IPLoM(fileName);
    
    // logAnalyzer.readByLines();
    
    //logAnalyzer.partitionByTokenSize();
    //logAnalyzer.printSizePartition();
    logAnalyzer.partitionByTokenPosition();
    
    
    
    /* ---------------- Analyzed file name  ---------------- */
    out.println("The analyzed log file: " + logAnalyzer.returnFile());
    /* ---------------- Analyzed file name  ---------------- */
    
    /* -------------- End time of the program -------------- */
    long endTime   = System.currentTimeMillis();
    /* -------------- End time of the program -------------- */
    
    /* ------------- Print out the running time ------------- */
    out.println("Running Time: " + (endTime - startTime) + " ms");
    /* ------------- Print out the running time ------------- */
  }

}
