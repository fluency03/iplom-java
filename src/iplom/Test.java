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
    
    
    
    int[] arr1 = new int[]{1, 2, 3};
    int[] arr2 = new int[]{4, 5, 6};
    int[] arr3 = new int[]{7, 8, 9};

    int[][] arrX = new int[3][3];


    for(int i = 0; i<3; i++){
        for (int j = 0; j < 3; j++){
            arrX[i][j] = arr1[i] + arr2[j]; //Dont how to resolve this       without using any "Java built-in methods"
        }

    }

    for(int x = 0; x < arrX.length; x++){
        for (int y = 0; y <arrX[x].length; y++){
           System.out.print(arrX[x][y] + " ");
        }
    }
    
    
    
    
    
    
  }

}
