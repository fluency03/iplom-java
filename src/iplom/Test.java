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
    try {
      System.setOut(new PrintStream(new FileOutputStream("/home/edghklj/workspace/github/iplom-java/output.txt")));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
    
    String fileName = "/home/edghklj/workspace/github/messages.1";
    IPLoM logAnalyzer = new IPLoM(fileName);
    
    // logAnalyzer.readByLines();
    
    //logAnalyzer.partitionByTokenSize();
    //logAnalyzer.printSizePartition();
    logAnalyzer.partitionByTokenPosition();
    
    out.println("The analyzed log file: " + logAnalyzer.returnFile());
    
  }

}
