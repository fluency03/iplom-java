/**
 * 
 */

package iplom;

import java.io.*;
//import java.util.*;
import java.util.StringTokenizer;

import static java.lang.System.out;

public class Test {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    /* --------------- Start time of the program --------------- */
    long startTime = System.currentTimeMillis();
    /* --------------- Start time of the program --------------- */
    
    /* ----------------- Output the printed to a file  ----------------- */
    try {
      System.setOut(new PrintStream(new FileOutputStream("/home/cliu/Documents/github/iplom-java/output1.txt")));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
    /* ----------------- Output the printed to a file  ----------------- */
    
    
    
    
    /* ------------------------- Main program  ------------------------- */
    String fileName = "/home/cliu/Documents/SC-1/messages.1";
    IPLoM logAnalyzer = new IPLoM(fileName);
    
    //logAnalyzer.readByLines();
    //logAnalyzer.partitionByTokenSize();
    //logAnalyzer.partitionByTokenPosition();
    //logAnalyzer.partitionByTokenBijection();
    logAnalyzer.dicoverLogTemplate();
    /* ------------------------- Main program  ------------------------- */
    
    
    
    
    /* ------------------ Analyzed file name  ------------------ */
    out.println("\nThe analyzed log file: " + logAnalyzer.returnFile());
    /* ------------------ Analyzed file name  ------------------ */
    
    /* ---------------- End time of the program ---------------- */
    long endTime   = System.currentTimeMillis();
    /* ---------------- End time of the program ---------------- */
    
    /* -------------- Print out the running time --------------- */
    out.println("\nRunning Time: " + (endTime - startTime) + " ms");
    /* -------------- Print out the running time --------------- */
    
    
    
    /* --------------------- For testing ----------------------- */
    //String str = "asfe[[asdq] fwev==qw cq:we we'f'we  /asad/qwd/asdq asd|111";
    //String delimiter = " []=:()/|\'\"";
    
    //StringTokenizer tokens = new StringTokenizer(str, delimiter, true);
    //while (tokens.hasMoreElements()) {
    //  out.println(tokens.nextToken());
    //}
    /* --------------------- For testing ----------------------- */
    //String timeRegex = "^((Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec) (0?[0-9]|[12][0-9]|3[01]) (([0-1][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]))"; 
    //String inputTest = "Feb 17 05:18:17 ";
    
    //out.print(inputTest.substring(0, 15).matches(timeRegex));
    /* --------------------- For testing ----------------------- */
    

  }

}
