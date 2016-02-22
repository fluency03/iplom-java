/**
 * Class: IPLoM (Iterative Partitioning Log Mining)
 * 
 * Based on the log mining algorithms published on the following papers:
 * 
 * [1] Adetokunbo AO Makanju, A Nur Zincir-Heywood, and Evangelos E Milios. Clustering 
 * event logs using iterative partitioning. In Proceedings of the 15th ACM SIGKDD international 
 * conference on Knowledge discovery and data mining, pages 1255–1264. ACM, 2009.
 * 
 * [2] Adetokunbo Makanju, A Nur Zincir-Heywood, and Evangelos E Milios. A lightweight
 * algorithm for message type extraction in system application logs. Knowledge and Data
 * Engineering, IEEE Transactions on, 24(11):1921–1936, 2012.
 * 
 * @author ERICSSON/edghklj (Chang Liu)
 *
 * Initially Created: 2016-02-22
 *
 */

package iplom;

import java.io.*;
import java.util.StringTokenizer;
import static java.lang.System.out;


public class IPLoM {

  /**
   * Define the delimiter for separating strings into tokens
   */
  private static String delimiter = " []=:()";
  
  
  /**
   * Read the log file by lines
   * @param 
   * String fileName: input log file name(path)
   */
  public static void readFileByLines(String fileName) {
    File file = new File(fileName);
    BufferedReader reader = null;
    
    try {
      out.println("Read the file by lines.");
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      int currentLine = 1;
      
      while ((tempString = reader.readLine()) != null) {
        singleLineProcess(tempString, currentLine);
        currentLine ++;
      }
      
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception e1) {
        }
      }
    }
  }
  
  /**
   * Process a single line of the log
   * @param 
   * String str: input string
   * int currentLine: current line number
   */
  public static void singleLineProcess(String str, int currentLine) {
    out.println("LINE " + currentLine + ": " + str);
    out.println("#tokens: " + countTokenSize(str));
  }
  
	
  /**
   * Count the #tokens of a string
   * @param 
   * String str: input string
   */
	public static int countTokenSize(String str) {
	  StringTokenizer token = new StringTokenizer(str, delimiter);
	  return token.countTokens();
	}

  
  
  
  
  
  
  
  
  
  
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "messages";
		
		readFileByLines(fileName);

	}

}
