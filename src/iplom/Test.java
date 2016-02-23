/**
 * 
 */

package iplom;

//import java.io.*;
import static java.lang.System.out;


public class Test {

  /**
   * @param args
   */
  public static void main(String[] args) {
    String fileName = "/home/edghklj/workspace/github/messages";
    IPLoM logAnalyzer = new IPLoM(fileName);
    
    // logAnalyzer.readByLines();
    
    logAnalyzer.partitionByTokenSize();
    //logAnalyzer.printSizePartition();
    logAnalyzer.partitionByTokenPosition();
    
    out.println("The analyzed log file: " + logAnalyzer.returnFile());
    
  }

}
