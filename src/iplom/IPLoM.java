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
   * Default: 0.05
   */
  private double partitionSupportThreshold = 0.05;
  
  /**
   * Define the cluster goodness threshold 0.3 ~ 0.6
   * Default: 0.4
   */
  private double clusterGoodnessThreshold = 0.4;
  
  /**
   * Define the upper bound (>0.5) and lower bound (<0.5)
   * Default: upperBound = 0.8 | lowerBound = 0.2
   */
  private double upperBound = 0.8;
  private double lowerBound = 0.2;
  
  /**
   * Define the source file name (path)
   */
  private File sourceFile = null;
  
  
  /** -----------------------------------------------
   * Constructors
   * ------------------------------------------------
   */
  public IPLoM () { }
  
  public IPLoM (String fileName) {
    this.sourceFile = new File(fileName);
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
   * Set the partition support threshold
   */
  public void setPartitionSupportThreshold(double support) {
    this.partitionSupportThreshold = support;
  }
  
  /**
   * Set the cluster goodness
   */
  public void setClusterGoodnessThreshold(double goodness) {
    this.clusterGoodnessThreshold = goodness;
  }
  
  /**
   * Set upper bound
   */
  public void setUpperBound(double upperBound) {
    this.upperBound = upperBound;
  }
  
  /**
   * Set lower bound
   */
  public void setLowerBound(double lowerBound) {
    this.lowerBound = lowerBound;
  }
  
  /**
   * Set both the lower and upper bounds
   */
  public void setBounds(double lowerBound, double upperBound) {
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
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
    out.println("#Tokens: " + tokenSizeOfString(str));
  }
  
	
  /**
   * Count the #tokens of a string
   * @param 
   * String str: input string
   */
	private int tokenSizeOfString(String str) {
	  StringTokenizer tokens = new StringTokenizer(str, this.delimiter);
	  return tokens.countTokens();
	}

  
	/**
	 * Partition the log messages based on the #tokens
	 * @param 
	 * 
	 */
  public Map<Integer, ArrayList<String>> partitionByTokenSize() {
    BufferedReader reader = null;
    Map<Integer, ArrayList<String>> partitionsBySize = new HashMap<>();
    
    try {
      out.println("Partition by token size.");
      reader = new BufferedReader(new FileReader(this.sourceFile));
      String tempString = null;
      //int currentLine = 1;
      int tokenSize = 0;
      while ((tempString = reader.readLine()) != null) {
        /* 
         * Remove the time stamp and server name here
         * TODO: other more intelligent way to get rid of time, server name, <number>, etc.
         */
        //tempString = tempString.substring(21, tempString.length());
        tokenSize = tokenSizeOfString(tempString);
        if (partitionsBySize.containsKey(tokenSize)) {
          partitionsBySize.get(tokenSize).add(tempString);
        } else {
          ArrayList<String> tempList = new ArrayList<>();
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
    
    return partitionsBySize;
  }
  
  
  /**
   * Print the sizePartitions
   * @param 
   */
  public void printSizePartition() {
    Map<Integer, ArrayList<String>> partitionsBySize = partitionByTokenSize();
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
  public Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByTokenPosition() {    
    
    out.println("Partition by token position.");
    
    Map<Integer, ArrayList<String>> partitionsBySize = partitionByTokenSize();
    Map<Integer, ArrayList<ArrayList<String>>> matirxBySize = new HashMap<>();
    Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition = new HashMap<>();
    
    /*
     * For each of the partition divided based on token size
     */
    for (Map.Entry<Integer, ArrayList<String>> partitionEntry: partitionsBySize.entrySet()) {
      
      out.println(partitionEntry.getKey() + " " + partitionEntry.getValue().size() + " " + partitionEntry.getValue());
      Integer tempSize = partitionEntry.getKey();
      matirxBySize.put(tempSize, new ArrayList<ArrayList<String>>());
      List<HashMap<String, Integer>> tokenCollection = new ArrayList<>(tempSize);
      
      while(tokenCollection.size() < tempSize) {
        tokenCollection.add(new HashMap<String, Integer>());
      }
      
      for (String oneLog: partitionEntry.getValue()) {
        StringTokenizer oneLogTokens = new StringTokenizer(oneLog, this.delimiter);
        ArrayList<String> logArray = new ArrayList<>(oneLogTokens.countTokens());
        
        for (int i = 0; i < tempSize; i++) {
          String oneToken = oneLogTokens.nextToken();
          logArray.add(oneToken); 
          
          HashMap<String, Integer> logEntry = tokenCollection.get(i);
          
          if (logEntry.containsKey(oneToken)) {
            // logEntry.get(oneToken) ++; // TODO: this causes error
            // TODO: I want to simplify this
            Integer tempValue = logEntry.get(oneToken);
            tempValue++;
            logEntry.remove(oneToken);
            logEntry.put(oneToken, tempValue);
          } else {
            logEntry.put(oneToken, 1);
          }

        }
        matirxBySize.get(tempSize).add(logArray);
      }
      /* -------------------- For debugging ---------------------- */
      // printTokenCollection(tokenCollection);
      /* -------------------- For debugging ---------------------- */
      
      /*
       * ----------- Calculate the partitioning position ----------- 
       * Reason for putting it here instead of merging it with the above for-loop:
       * Merging with above for-loop adding lots of computation, when loop is rolling
       */
      int choosenPosition = positionWithLowestCardinality(tokenCollection).getPosition();
      //out.println("Position with lowest cardinality: " + choosenPosition);
      
      for (ArrayList<String> logMatrix: matirxBySize.get(tempSize)) {
        String key = logMatrix.get(choosenPosition);
        ArrayList<Object> keyArray = new ArrayList<>();
        keyArray.add(tempSize);
        HashMap<String, Integer> pair = new HashMap<>();
        pair.put(key, choosenPosition);
        keyArray.add(pair);
        
        if (!partitionByPosition.containsKey(keyArray)){
          partitionByPosition.put(keyArray, new ArrayList<ArrayList<String>>());
        }
        partitionByPosition.get(keyArray).add(logMatrix);
      }

      /*
       * Check PST (Partition Support Threshold)
       */
      for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> subPartitionEntry: partitionByPosition.entrySet()) {
        if (subPartitionEntry.getKey().get(0) == partitionEntry.getKey()) {
          double partitionSupportRatio = (double)subPartitionEntry.getValue().size()/(double)partitionEntry.getValue().size();
          out.println(partitionSupportRatio);
        
          if (partitionSupportRatio < partitionSupportThreshold) {
            // TODO: Add lines from this partition into Outlier partition
          }
        }
      }
    }

    
    /* -------------------- For debugging ---------------------- */
    printPartitionsByPosition(partitionByPosition);
    //out.println(matirxBySize);
    /* -------------------- For debugging ---------------------- */

    return partitionByPosition;
    
  }
  
  
  /**
   * Print the token collections, mainly for debugging
   * @param 
   * List<HashMap<String, Integer>> tokenCollection
   */
  private void printTokenCollection(List<HashMap<String, Object>> tokenCollection) {
    for (HashMap<String, Object> logEntry: tokenCollection) {
      out.println(logEntry);
    }
  }
  
  /**
   * Print the partitions by position, mainly for debugging
   * @param 
   * Map<Integer, Map<String, ? extends Object>> partitionByPosition
   */
  private void printPartitionsByPosition(Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition) {
    for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> entry: partitionByPosition.entrySet()) {
      out.println(entry.getKey().toString() + entry.getValue());
    }
  }
  
  /**
   * Position chosen and cardinality list of a partition
   */
  private class positionCardinality {
    private Integer position;
    private List<Integer> cardinality;
    
    public positionCardinality() { }
    
    public positionCardinality(Integer position, List<Integer> cardinality) { 
      this.position = position;
      this.cardinality = cardinality;
    }
    
    public Integer getPosition() { return position; }
    public List<Integer> getCardinality() { return cardinality; }
    
    public void setPosition(Integer position) { this.position = position; }
    public void setCardinality(List<Integer> cardinality) { this.cardinality = cardinality; }
    
  }
  
  
  /**
   * Determine the token position with lowest cardinality with respect to set of unique tokens
   * @param 
   * List<HashMap<String, Integer>> tokenCollection
   */
  private positionCardinality positionWithLowestCardinality(List<HashMap<String, Integer>> tokenCollection) {
    int position = 0;
    int lowestCardinality = Integer.MAX_VALUE;
    int tempSize = tokenCollection.size();
    // Keep the cardinality at each position
    List<Integer> cardinality = new ArrayList<>();
    
    for (int j = 0; j < tempSize; j++) {
      int tempCardinality = tokenCollection.get(j).size();
      // Keep the cardinality at each position
      cardinality.add(tempCardinality);
      
      if (tempCardinality < lowestCardinality) {
        lowestCardinality = tempCardinality;
        position = j;
      } 
    }
    
    return (new positionCardinality(position, cardinality));
  }
  
  
  /**
   * Partition by search bijection
   */
  public void partitionByTokenBijection() {
    
    Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition = partitionByTokenPosition();
    
    for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> entry: partitionByPosition.entrySet()) {
      ArrayList<ArrayList<String>> partitionIn = entry.getValue();
      Integer tokenCount = (Integer) entry.getKey().get(0);
      positionPair tempPair = determineP1P2(partitionIn, tokenCount);
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
    }
    
    

    
    
  }
  
  
  /**
   * Pair of positions
   */
  private class positionPair {
    private Integer p1 = 0;
    private Integer p2 = 0;
    
    public positionPair() {}
    
    public positionPair(Integer p1, Integer p2){
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public Integer getP1(){ return p1; }
    public Integer getP2(){ return p2; }
    
    public void setP1(Integer p1){ this.p1 = p1; }
    public void setP2(Integer p2){ this.p2 = p2; }
  }
  
  
  
  /**
   * Determine positions P1 and P2
   */
  private positionPair determineP1P2(ArrayList<ArrayList<String>> partitionIn, Integer tokenCount) {
    
    if (tokenCount > 2) {
      
      Integer count = 1; //TODO
      
      double clusterGoodness = (double)count/(double)tokenCount;
      
      if (clusterGoodness < clusterGoodnessThreshold) {
        return getMappingPositions(partitionIn, tokenCount);
      } else {
        return (new positionPair()); 
      }
    } else if (tokenCount == 2) {
      return (new positionPair(0 ,1));
    } else {
      return (new positionPair());
    }
    
  }
  
  
  private positionPair getMappingPositions(ArrayList<ArrayList<String>> partitionIn, Integer tokenCount) {
    
    
    
    
    
    
    return (new positionPair());
    
  }
  
  
  
  
  
  
  
  
  
  


}
