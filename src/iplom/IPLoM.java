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
//import org.apache.commons.lang3.tuple.Pair;

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
  private Integer partitionSupportThreshold = 8;
  
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
    this.partitionSupportThreshold = threshold;
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
	  StringTokenizer tokens = new StringTokenizer(str, this.delimiter);
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
      out.println("Partition by token size.");
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
    
    out.println("Partition by token position.");
    
    for (Map.Entry<Integer, ArrayList<String>> partitionEntry: partitionsBySize.entrySet()) {
      out.println(partitionEntry.getKey() + " " + partitionEntry.getValue().size() + " " + partitionEntry.getValue());
      Integer tempSize = partitionEntry.getKey();
      List<HashMap<String, Integer>> tokenCollection = new ArrayList<HashMap<String, Integer>>(tempSize);
      
      while(tokenCollection.size() < tempSize) {
        tokenCollection.add(new HashMap<String, Integer>());
      }
      
      for (String oneLog: partitionEntry.getValue()) {
        StringTokenizer oneLogTokens = new StringTokenizer(oneLog, this.delimiter);
        
        for (int i = 0; i < tempSize; i++) {
          String oneToken = oneLogTokens.nextToken();
          
          HashMap<String, Integer> logEntry = tokenCollection.get(i);
          
          if (logEntry.containsKey(oneToken)) {
            // logEntry.get(oneToken) ++; // TODO this causes error
            // TODO I want to simplify this
            Integer tempValue = logEntry.get(oneToken);
            tempValue++;
            logEntry.remove(oneToken);
            logEntry.put(oneToken, tempValue);
          } else {
            logEntry.put(oneToken, 1);
          }

        }
      }
      
      // For debugging
      printTokenCollection(tokenCollection);
      
      out.println("Position with lowest cardinality: " + positionWithLowestCardinality(tokenCollection));
      
    }
    

    

    
  }
  
  
  /**
   * Print the token collections, mainly for debugging
   * @param 
   * List<HashMap<String, Integer>> tokenCollection
   */
  private void printTokenCollection(List<HashMap<String, Integer>> tokenCollection) {
    for (HashMap<String, Integer> logEntry: tokenCollection) {
      out.println(logEntry);
    }
  }
  
  /**
   * Determine the token position with lowest cardinality with respect to set of unique tokens
   * @param 
   * List<HashMap<String, Integer>> tokenCollection
   */
  private int positionWithLowestCardinality(List<HashMap<String, Integer>> tokenCollection) {
    int lowestCardinality = Integer.MAX_VALUE;
    int position = 0;
    int tempSize = tokenCollection.size();
    for (int j = 0; j < tempSize; j++) {
      int tempCardinality = tokenCollection.get(j).size();
      if (tempCardinality < lowestCardinality) {
        lowestCardinality = tempCardinality;
        position = j;
      } 
    }
    return position;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  


}
