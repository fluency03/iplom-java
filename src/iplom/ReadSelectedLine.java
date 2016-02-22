/**
 * Class: ReadSelectedLine
 * 
 * @author ERICSSON/edghklj (Chang Liu)
 * 
 * Initially Created: 2016-02-22
 * 
 */

package iplom;

import java.io.*;
import static java.lang.System.out;

public class ReadSelectedLine {

  /**
   * Read the selected line of a log file
   * @param 
   * File sourceFile: input file
   * int lineNumber: specified line number
   */
  static void readAppointedLineNumber(File sourceFile, int lineNumber) 
      throws IOException {
    FileReader input = new FileReader(sourceFile);
    LineNumberReader reader = new LineNumberReader(input);
    // reader.setLineNumber(lineNumber);
    String s = "";
    
    if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {
      out.println("The selected line number is our of range.");
      System.exit(0);
    }
    
//    s = reader.readLine();
//    out.println("LINE " + reader.getLineNumber() + ": " + s);
    
    int line = 0;
    while (s != null) {
      line ++;
      s = reader.readLine();
      if ((line - lineNumber) == 0) {
        out.println("LINE " + line + ": " + s);
        // System.exit(0);
      }
    }
    reader.close();
    input.close();
  }
  
  
  /**
   * Get the total number of lines within a log file
   * @param 
   * File sourceFile: input file
   */
  static int getTotalLines(File sourceFile) 
      throws IOException {
    FileReader input = new FileReader(sourceFile);
    LineNumberReader reader = new LineNumberReader(input);
    String s = reader.readLine();
    int line = 0;
    while (s != null) {
      line ++;
      s = reader.readLine();
    }
    reader.close();
    input.close();
    return line;
  }
  
  
  /**
   * @param args
   */
  public static void main(String[] args) 
      throws IOException {
    int lineNumber = 865;
    File sourceFile = new File("messages");
    
    long startTime=System.currentTimeMillis();
    readAppointedLineNumber(sourceFile, lineNumber);
    long endTime=System.currentTimeMillis();
    
    out.println("Running time: " + (endTime - startTime) + "ms");
    out.println("Total #line: " + getTotalLines(sourceFile));
  }

}
