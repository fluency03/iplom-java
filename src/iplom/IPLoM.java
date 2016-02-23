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
import java.util.*;
import static java.lang.System.out;


public class IPLoM {

  /**
   * Define the delimiter for separating a log message into tokens
   * Default: " []=:()/|\'\""
   */
  private String delimiter = " []=:()/|\'\"";
  
  /**
   * Define the partition support threshold
   * Default: 8
   */
  private Integer partitioSupportThreshold = 8;
  
  /**
   * Define the source file name (path)
   */
  private File sourceFile;
  
  /**
   * Partitions based on #tokens
   */
  private Map<Integer, ArrayList<String>> partitionsBySize;
  
  /**
   * Constructors
   */
  public IPLoM () { 
    this.sourceFile = null;
    partitionsBySize = new HashMap<Integer, ArrayList<String>>();
  }
  
  public IPLoM (String fileName) {
    this.sourceFile = new File(fileName);
    partitionsBySize = new HashMap<Integer, ArrayList<String>>();
  }
  
  /**
   * Set the log file
   */
  public void setFile(String fileName) {
    this.sourceFile = new File(fileName);
  }
  
  /**
   * Print the log file name(path)
   */
  public File returnFile () {
    return this.sourceFile;
  }
  
  /**
   * Set the delimiter
   */
  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }
  
  /**
   * Set the log file
   */
  public void setThreshold(Integer threshold) {
    this.partitioSupportThreshold = threshold;
  }
  
  /*
   * ------------------------------------------------------------------
   */
  
  /**
   * Read the log file by lines
   * @param 
   * String fileName: input log file name(path)
   */
  public void readByLines() {
    BufferedReader reader = null;
    
    try {
      out.println("Read the file by lines.");
      reader = new BufferedReader(new FileReader(this.sourceFile));
      String tempString = null;
      int currentLine = 1;
      
      while ((tempString = reader.readLine()) != null) {
        singleLinePrint(tempString, currentLine);
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
  private void singleLinePrint(String str, int currentLine) {
    out.println("LINE " + currentLine + ": " + str);
    out.println("#Tokens: " + countTokenSize(str));
  }
  
	
  /**
   * Count the #tokens of a string
   * @param 
   * String str: input string
   */
	private int countTokenSize(String str) {
	  StringTokenizer tokens = new StringTokenizer(str, delimiter);
	  return tokens.countTokens();
	}

  
	/**
	 * Partition the log messages based on the #tokens
	 * @param 
	 * 
	 */
  public void partitionByTokenSize() {
    BufferedReader reader = null;
    
    try {
      out.println("Read the file by lines.");
      reader = new BufferedReader(new FileReader(this.sourceFile));
      String tempString = null;
      //int currentLine = 1;
      int tokenSize = 0;
      
      while ((tempString = reader.readLine()) != null) {
        tokenSize = countTokenSize(tempString);
        if (partitionsBySize.containsKey(tokenSize)) {
          partitionsBySize.get(tokenSize).add(tempString);
        } else {
          ArrayList<String> tempList = new ArrayList<String>();
          tempList.add(tempString);
          partitionsBySize.put(tokenSize, tempList);
        }
        //currentLine ++;
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
   * Print the sizePartitions
   * @param 
   */
  public void printSizePartition() {
    for (Map.Entry<Integer, ArrayList<String>> entry: partitionsBySize.entrySet()) {
      // out.println(entry.getKey() + " " + entry.getValue().size() + " " + entry.getValue());
      out.println(entry.getKey() + " " + entry.getValue().size());
      for (String oneLog: entry.getValue()) {
        out.println(oneLog);
      }
    }
  }
  
  
  /**
   * Partition each of the partitions with same token sizes based on the token positions
   * @param 
   */
  public void partitionByTokenPosition() {
    
    for (Map.Entry<Integer, ArrayList<String>> entry: partitionsBySize.entrySet()) {
      out.println(entry.getKey() + " " + entry.getValue().size());
      for (String oneLog: entry.getValue()) {
        out.println(oneLog);
      }
    }
    
    
    
    
    
    
    
    
    
    
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  


}
