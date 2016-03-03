/**
 * Class: IPLoM (Iterative Partitioning Log Mining)
 * 
 * Based on the log mining algorithms published on the following papers:
 * 
 * [1] Adetokunbo AO Makanju, A Nur Zincir-Heywood, and Evangelos E Milios. Clustering event logs using iterative partitioning. 
 * In Proceedings of the 15th ACM SIGKDD international conference on Knowledge discovery and data mining, pages 1255–1264. ACM, 2009.
 * 
 * [2] Adetokunbo Makanju, A Nur Zincir-Heywood, and Evangelos E Milios. A lightweight algorithm for message type extraction in 
 * system application logs. Knowledge and Data Engineering, IEEE Transactions on, 24(11):1921–1936, 2012.
 * 
 * @author ERICSSON/edghklj (Chang Liu)
 *
 * Initially Created: 2016-02-22
 *
 */

package iplom;

import static java.lang.System.out;
import java.io.*;
import java.util.*;



public class IPLoM {

  /* ------------------------------------------------------------------------------------ */
  /*                                    Elements                                          */
  /* ------------------------------------------------------------------------------------ */
  
  /**
   * Define the delimiter for separating a log message into tokens
   * Default: " []=:()/|\'\""
   */
  private String delimiter = " []=:()/|\'\"";
  
  /**
   * Define the partition support threshold
   * Default: 0.00
   */
  private double partitionSupportThreshold = 0.00;
  
  /**
   * Define the cluster goodness threshold
   * Default: 0.34
   */
  private double clusterGoodnessThreshold = 0.34;
  
  /**
   * Define the upper bound (>0.5) and lower bound (<0.5)
   * Default: upperBound = 0.9 | lowerBound = 0.1
   */
  private double upperBound = 0.9;
  private double lowerBound = 0.1;
  
  /**
   * Define the source file name (path)
   */
  private File sourceFile = null;
  
  
  /* ------------------------------------------------------------------------------------ */
  /*                                  Constructors                                        */
  /* ------------------------------------------------------------------------------------ */
  
  public IPLoM () { }
  
  public IPLoM (String fileName) {
    this.sourceFile = new File(fileName);
  }
  
  
  /* ------------------------------------------------------------------------------------ */
  /*                                    Methods                                           */
  /* ------------------------------------------------------------------------------------ */
  
  /**
   * Set the source log file name (path)
   */
  public void setFile(String fileName) {
    this.sourceFile = new File(fileName);
  }
  
  /**
   * Print the analyzed log file name (path)
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
   * Set the cluster goodness threshold
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
  
  /**
   * Check the token type
   */
  public Integer checkTokenType(String token) {
    Integer tokenType = 0;
    
    // TODO: 
    /* ------------------- Five types of tokens ------------------- */
    String regexOnlySymbols = "";
    String regexOnlyLetters = "^[A-Za-z]+$";
    String regexSymbolsLetters = "";
    String regexNumbersLetters = "";
    String regexNumbersSymbols = "";
    /* ------------------- Five types of tokens ------------------- */
    
    
    
    
    
    
    
    
    
    return tokenType;
  }
  
  
  /* ----------------------------------------------------------------------------------- */
  
  /**
   * Read the log file by lines
   * @param 
   * String fileName: input log file name(path)
   */
  public void readByLines() {
  	
    BufferedReader reader = null;
    
    try {
      out.println("\nRead the file by lines.");
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

	
	
	
	
	
	
	
  /* ------------------------------------------------------------------------------------ */
  /*                          Step 1 - Partition by token size                            */
  /* ------------------------------------------------------------------------------------ */
  
	/**
	 * Partition the log messages based on the #tokens
	 * @param 
	 * 
	 */
  public Map<Integer, ArrayList<String>> partitionByTokenSize() {
    
    out.println("\nPartition by token size.");
    
    BufferedReader reader = null;
    Map<Integer, ArrayList<String>> partitionsBySize = new HashMap<>();
    
    try {
      out.println("Partition by token size.");
      reader = new BufferedReader(new FileReader(this.sourceFile));
      String tempString = null;
      //int currentLine = 1;
      while ((tempString = reader.readLine()) != null) {
        /* 
         * Remove the time stamp and server name here
         * TODO: other more intelligent way to get rid of time, server name, <number>, etc.
         */
        tempString = tempString.substring(16, tempString.length());
        Integer tokenSize = tokenSizeOfString(tempString);
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
    /* -------------------- For debugging ---------------------- */
    //printSizePartition(partitionsBySize);
    /* -------------------- For debugging ---------------------- */
    
    return partitionsBySize;
    
  }
  
  
  /**
   * Print the partitions based on token size
   * Used for debugging
   */
  private void printSizePartition(Map<Integer, ArrayList<String>> partitionsBySize) {
    //Map<Integer, ArrayList<String>> partitionsBySize = partitionByTokenSize();
    for (Map.Entry<Integer, ArrayList<String>> entry: partitionsBySize.entrySet()) {
      // out.println(entry.getKey() + " " + entry.getValue().size() + " " + entry.getValue());
      out.println(entry.getKey() + " " + entry.getValue().size());
      for (String oneLog: entry.getValue()) {
        out.println(oneLog);
      }
    }
  }
  
  
  
  
  
  
  
  
  /* ------------------------------------------------------------------------------------ */
  /*                        Step 2 - Partition by token position                          */
  /* ------------------------------------------------------------------------------------ */
  
  /**
   * partitionByTokenPosition
   * @return 
   * Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition
   */
  public Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByTokenPosition() {    
    
    Map<Integer, ArrayList<String>> partitionsBySize = partitionByTokenSize();
    Map<Integer, ArrayList<ArrayList<String>>> matirxBySize = new HashMap<>();
    Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition = new HashMap<>();
    
    out.println("\nPartition by token position.");
    
    /*
     * For each of the partition divided based on token size
     */
    for (Map.Entry<Integer, ArrayList<String>> partitionEntry: partitionsBySize.entrySet()) {
      
      //out.println(partitionEntry.getKey() + " " + partitionEntry.getValue().size() + " " + partitionEntry.getValue());
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
          logEntry.put(oneToken, logEntry.containsKey(oneToken) ? (logEntry.get(oneToken) + 1) : 1);
        }
        matirxBySize.get(tempSize).add(logArray);
      }
      /* -------------------- For debugging ---------------------- */
      // printTokenCollection(tokenCollection);
      /* -------------------- For debugging ---------------------- */
      

      /*
       * Calculate the partitioning position:
       *    Reason for putting it here instead of merging it with the above for-loop:
       *    Merging with above for-loop adding lots of computation, when loop is rolling
       */
      int chosenPosition = positionCardinality(tokenCollection).getLeft();
      //out.println("Position with lowest cardinality: " + choosenPosition);
      
      //out.println(tempSize);
      for (ArrayList<String> logMatrix: matirxBySize.get(tempSize)) {
        String key = logMatrix.get(chosenPosition);
        ArrayList<Object> keyArray = new ArrayList<>();
        keyArray.add(tempSize);
        Pair<String, Integer> tokenPositionPair = new Pair<>(key, chosenPosition);
        keyArray.add(tokenPositionPair);
          
        if (!partitionByPosition.containsKey(keyArray)){
          //out.println(((Pair)keyArray.get(1)).getLeft());
          partitionByPosition.put(keyArray, new ArrayList<ArrayList<String>>());
        }
        partitionByPosition.get(keyArray).add(logMatrix);
      }
      

      /*
       * Check PST (Partition Support Threshold)
       */
      for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> subPartitionEntry: partitionByPosition.entrySet()) {
        if (subPartitionEntry.getKey().get(0) == tempSize) {
          //out.println("Size: " + tempSize);
          double partitionSupportRatio = (double)subPartitionEntry.getValue().size()/(double)partitionEntry.getValue().size();
          out.println("" + partitionSupportRatio);
        
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
   * Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition
   */
  @SuppressWarnings("rawtypes")
  private void printPartitionsByPosition(Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition) {
    //int i = 0; // int i, for debugging
    for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> entry: partitionByPosition.entrySet()) {
      ArrayList<Object> key = entry.getKey();
      /* if-statement for debugging */
      if ((Integer)key.get(0) == 8) {
        out.println(key.get(0) + " " + ((Pair)key.get(1)).getLeft() + " " + ((Pair)key.get(1)).getRight() + " " + entry.getValue());
        //i ++;
      }
    }
    //out.println(i);
  }
  

  /**
   * Determine the token position with lowest cardinality with respect to set of unique tokens
   * @param 
   * List<HashMap<String, Integer>> tokenCollection
   */
  private Pair<Integer, ArrayList<Integer>> positionCardinality(List<HashMap<String, Integer>> tokenCollection) {
  	
    int position = 0;
    int lowestCardinality = Integer.MAX_VALUE;
    int tempSize = tokenCollection.size();
    // Keep tack of the cardinality at each position
    ArrayList<Integer> cardinality = new ArrayList<>();
    
    for (int j = 0; j < tempSize; j++) {
      int tempCardinality = tokenCollection.get(j).size();
      cardinality.add(tempCardinality);
      
      if (tempCardinality < lowestCardinality && tempCardinality > 1) {
        lowestCardinality = tempCardinality;
        position = j;
      } 
    }
    
    return (new Pair<Integer, ArrayList<Integer>>(position, cardinality));
    
  }
  
  
  /**
   * Determine the token collection information of a partition
   */
  private List<HashMap<String, Integer>> tokenCollection(Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partition){
    
    Integer tempSize = (Integer)(partition.getKey()).get(0);
    List<HashMap<String, Integer>> tokenCollection = new ArrayList<>(tempSize);
    
    while(tokenCollection.size() < tempSize) {
      tokenCollection.add(new HashMap<String, Integer>());
    }
    
    for (ArrayList<String> logArray: partition.getValue()) {
      for (int i = 0; i < tempSize; i++) {
        String oneToken = logArray.get(i);
        HashMap<String, Integer> logEntry = tokenCollection.get(i);
        logEntry.put(oneToken, logEntry.containsKey(oneToken) ? (logEntry.get(oneToken) + 1) : 1);
      }
    }
    
    return tokenCollection;
    
  }
  

  
  
  
  
  
  
  /* ------------------------------------------------------------------------------------ */
  /*                         Step 3 - Partition by search bijection                       */
  /* ------------------------------------------------------------------------------------ */
  
  /**
   * partitionByTokenBijection
   * @return
   * Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByBijection
   */
  public Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByTokenBijection() {
    
    Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByPosition = partitionByTokenPosition();
    Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByBijection = new HashMap<>();
    
    out.println("\nPartition by token bijection.");
    
    for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionEntry: partitionByPosition.entrySet()) {
      List<HashMap<String, Integer>> tokenCollection = tokenCollection(partitionEntry);
      Pair<Integer, ArrayList<Integer>> positionCardinality = positionCardinality(tokenCollection);
      Pair<Integer, Integer> positionPair = determineP1P2(partitionEntry, tokenCollection, positionCardinality);
      Integer P1 = positionPair.getLeft();
      Integer P2 = positionPair.getRight();
      
      if (positionPair.equals(new Pair<Integer, Integer>(0, 0))) {
        /* 
         * Add this partition to output partition 
         * No need for further partitioning
         */
        ArrayList<Object> tempKey = partitionEntry.getKey();
        tempKey.add("No BI");
        partitionByBijection.put(tempKey, partitionEntry.getValue());
      } else {
        HashMap<String, Integer> tokensSet1 = tokenCollection.get(P1);
        HashMap<String, Integer> tokensSet2 = tokenCollection.get(P2);
        Integer splitPosition = 0;
        HashMap<String, Integer> removedTokenSet = new HashMap<>();
        
        Integer partitionCount = 1;
        for (Map.Entry<String, Integer> tokenEntry: tokensSet1.entrySet()) {
          /*
           * If this token is in the removedTokenSet
           * That means it has been passed
           */
          if (removedTokenSet.containsKey(tokenEntry.getKey())) {
            continue;
          }
          
          /*
           * Determine the mapping type and also return the tokens covered by this mapping.
           */
          Pair<Integer, Pair<HashMap<String, Integer>, HashMap<String, Integer>>> mappingPair = 
              determineMappingType(partitionEntry, tokenEntry, P1, P2, tokensSet1, tokensSet2);
          /*
           * The mapping type
           */
          Integer mappingType = mappingPair.getLeft();
          /*
           * The sub-sets for above type of mapping.
           * The tokens form these sub-sets will be removed from S1 and S2.
           */
          Pair<HashMap<String, Integer>, HashMap<String, Integer>> setPair = mappingPair.getRight();
          /*
           * Move all these sub-set tokens into removedTokenSet.
           */
          removedTokenSet.putAll(setPair.getLeft());

          
          /*
           * Determining the split position based on the mapping type
           */
          if (mappingType == 1) {
            /* ------------------- mapping: 1-1 ------------------- */
            splitPosition = P1;
          } else if (mappingType == 2) {
            /* ------------------- mapping: 1-M ------------------- */
            HashMap<String, Integer> tempTokenSet = setPair.getRight();
            splitPosition = (getRankPosition(partitionEntry, tempTokenSet, mappingType, P2) == 1) ? P1 : P2;
          } else if (mappingType == 3) {
            /* ------------------- mapping: M-1 ------------------- */
            HashMap<String, Integer> tempTokenSet = setPair.getLeft();
            splitPosition = (getRankPosition(partitionEntry, tempTokenSet, mappingType, P1) == 2) ? P2 : P1;
          } else if (mappingType == 4) {
            /* ------------------- mapping: M-M ------------------- */
            Boolean fromStep1 = false; // TODO: check the partitions from Step1 or Step2
            if (fromStep1) {
              splitPosition = (setPair.getLeft().size() < setPair.getRight().size())? P1 : P2;
            } else {
              ArrayList<ArrayList<String>> tempPartition = new ArrayList<>();
              for (ArrayList<String> logMatrix: partitionEntry.getValue()) {
                if (setPair.getLeft().containsKey(logMatrix.get(P1))) {
                  tempPartition.add(logMatrix);
                }
              }
              partitionEntry.getValue().removeAll(tempPartition);
              ArrayList<Object> tempKey = partitionEntry.getKey();
              tempKey.add("M-M" + " " + (partitionCount++).toString());
              partitionByBijection.put(tempKey, tempPartition);
              continue;
            }
          }
          
          HashMap<String, Integer> partitionTokenSet = (splitPosition == P1) ? setPair.getLeft() : setPair.getRight();
          
          /*
           * TODO: 
           * Split partition into new partitions based on splitPosition and setPair
           * Then add them into output
           */
          Map<ArrayList<Object>, ArrayList<ArrayList<String>>> tempPartitionByBijection = new HashMap<>();
          for (String tempToken: partitionTokenSet.keySet()) {
            ArrayList<ArrayList<String>> tempPartition = new ArrayList<>();
            for (ArrayList<String> logMatrix: partitionEntry.getValue()) {
              if (tempToken == logMatrix.get(splitPosition)) {
                tempPartition.add(logMatrix);
              }
            }
            partitionEntry.getValue().removeAll(tempPartition);
            ArrayList<Object> tempKey = partitionEntry.getKey();
            tempKey.add(tempToken + " " + splitPosition.toString());
            tempPartitionByBijection.put(tempKey, tempPartition);
          }
          partitionByBijection.putAll(tempPartitionByBijection);
          
          /*
           * TODO: if partition is empty, move to the next partition
           */
          if (partitionEntry.getValue().isEmpty()) {
            break;
          }

        }
        
        /*
         * TODO: if partition is not empty, create a new partition with reminder lines
         */
        if (!partitionEntry.getValue().isEmpty()) {
          partitionEntry.getKey().add("Outliers");
          partitionByBijection.put(partitionEntry.getKey(), partitionEntry.getValue());
        }

      }

    }
    
    /* -------------------- For debugging ---------------------- */
    printPartitionByBijection(partitionByBijection);
    /* -------------------- For debugging ---------------------- */
    
    return partitionByBijection;

  }
  
  
  /**
   * Print out the partitions based on token bijection relationships
   * Used for debugging
   */
  @SuppressWarnings("rawtypes")
  private void printPartitionByBijection(Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByBijection) {
  	
    for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> entry: partitionByBijection.entrySet()) {
      ArrayList<Object> key = entry.getKey();
      /* if-statement for debugging */
      if ((Integer)key.get(0) == 8) {
        out.println(key.get(0) + " " + 
                    ((Pair)key.get(1)).getLeft() + " " + ((Pair)key.get(1)).getRight() + " " + 
                    key.get(2) + " " + 
                    entry.getValue());
      }
    }
    
  }
  
  
  
  /**
   * Get rank position
   * @return Integer splitRank: either 1 or 2
   */
  private Integer getRankPosition(Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionEntry, 
  																	HashMap<String, Integer> tempTokenSet, Integer mappingType, Integer position) {
  	
    Integer splitRank = 0;
    Integer cardinalityOfSet = tempTokenSet.size();
    Integer linesMatchSet = 0;
    
    /*
     * Determine the number of lines that have these values (of tempTokenSet)
     * in the corresponding token position of this partition
     */
    for (ArrayList<String> logMatrix: partitionEntry.getValue()) {
      if (tempTokenSet.containsKey(logMatrix.get(position))){
        linesMatchSet ++;
      }
    }
    
    double distance = (double)cardinalityOfSet/(double)linesMatchSet;
    
    if (distance <= lowerBound) {
      splitRank = (mappingType == 2) ? 2 : 1;
    } else if (distance >- upperBound) {
      splitRank = (mappingType == 2) ? 1 : 2;
    } else {
      splitRank = (mappingType == 2) ? 1 : 2;
    }

    return splitRank;
    
  }
  
  
  
  /**
   * Determine the mapping type
   * @return Integer mappingType
   * Represented by an Integer: 1 (1-1), 2 (1-M), 3 (M-1), or 4 (M-M)
   */
  private Pair<Integer, Pair<HashMap<String, Integer>, HashMap<String, Integer>>> 
  				determineMappingType(Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionEntry,
  															Map.Entry<String, Integer> tokenEntry, Integer P1, Integer P2,
  															HashMap<String, Integer> tokensSet1, HashMap<String, Integer> tokensSet2) {
  	
    Integer mappingType = 0;
    String tempToken1 = tokenEntry.getKey();
    HashMap<String, Integer> tempSet1 = new HashMap<>();
    HashMap<String, Integer> tempSet2 = new HashMap<>();
    tempSet1.put(tempToken1, 1);
    
    Pair<HashMap<String, Integer>, HashMap<String, Integer>> setPair 
        = completeTokenSets(partitionEntry, P1, P2, tempSet1, tempSet2);
    
    Integer sizeOfSet1 = setPair.getLeft().size();
    Integer sizeOfSet2 = setPair.getRight().size();
    
    if (sizeOfSet1 == 1 && sizeOfSet2 == 1) {
      mappingType = 1;
    } else if (sizeOfSet1 == 1) {
      mappingType = 2;
    } else if (sizeOfSet2 == 1) {
      mappingType = 3;
    } else {
      mappingType = 4;
    }

    return new Pair<Integer, Pair<HashMap<String, Integer>, HashMap<String, Integer>>>(mappingType, setPair);
    
  }
  
  
  
  /**
   * Complete two token sets: tokenSet1, tokenSet2
   */
  private Pair<HashMap<String, Integer>, HashMap<String, Integer>> 
          completeTokenSets(Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionEntry,
          										Integer P1, Integer P2, HashMap<String, Integer> tokensSet1, HashMap<String, Integer> tokensSet2) {
  	
    Integer sizeOfSet1 = tokensSet1.size();
    Integer sizeOfSet2 = tokensSet2.size();
    HashMap<String, Integer> tempSet1 = tokensSet1;
    HashMap<String, Integer> tempSet2 = tokensSet1;
    Pair<HashMap<String, Integer>, HashMap<String, Integer>> setPair = new Pair<>(tempSet1, tempSet2);
    
    /*
     * Complement the token set with less tokens
     */
    if (sizeOfSet1 == sizeOfSet2) {
      /*
       * If the size of two token sets are equal,
       * directly return, not further recursive substitution
       */
      return setPair;
    } else if (sizeOfSet1 > sizeOfSet2) {
      /*
       * If sizeOfSet1 > sizeOfSet2, Complement tokenSet2.
       */
      for (String tempToken: tempSet1.keySet()) {
        for (ArrayList<String> logMatrix: partitionEntry.getValue()) {
          if (logMatrix.get(P1) == tempToken) {
            String tempToken2 = logMatrix.get(P2);
            tempSet2.put(tempToken2, tempSet2.containsKey(tempToken2) ? tempSet2.get(tempToken2) + 1 : 1);
          }
        }
      }

    } else {
      /*
       * If sizeOfSet1 < sizeOfSet2, Complement tokenSet1.
       */
      for (String tempToken: tempSet2.keySet()) {
        for (ArrayList<String> logMatrix: partitionEntry.getValue()) {
          if (logMatrix.get(P2) == tempToken) {
            String tempToken1 = logMatrix.get(P1);
            tempSet1.put(tempToken1, tempSet1.containsKey(tempToken1) ? tempSet1.get(tempToken1) + 1 : 1);
          }
        }
      }
      
    }

    /*
     * Do the complementing recursively
     */
    return this.completeTokenSets(partitionEntry, P1, P2, tempSet1, tempSet2);
    
  }
  

  /**
   * Determine positions P1 and P2
   * Assume Pa is before P2
   */
  private Pair<Integer, Integer> determineP1P2(Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionEntry,
  																							List<HashMap<String, Integer>> tokenCollection, 
                                                Pair<Integer, ArrayList<Integer>> positionCardinality) {
  	
    Integer tokenCount = positionCardinality.getRight().size(); // token length of a single line
    
    if (tokenCount > 2) {
      
      Integer uniqueTokenCount = 0; 
      for (int i = 0; i < tokenCollection.size(); i++) {
        uniqueTokenCount = (tokenCollection.get(i).keySet().size() == 1) ? (uniqueTokenCount + 1) : uniqueTokenCount;
      }
      double clusterGoodness = (double)uniqueTokenCount/(double)tokenCount;
      
      /*
       * If clusterGoodness <= Threshold, this partition is considered to be bad partition,
       * and further partitioning is needed;
       * If clusterGoodness > Threshold, this partition is considered to be good partition,
       * and further partitioning is not needed.
       */
      if (clusterGoodness < clusterGoodnessThreshold) {
        return getMappingPositions(partitionEntry, tokenCollection, positionCardinality);
      } else {
        /*
         * If not further partition is needed, return Pair<Integer, Integer>(0, 0),
         * which indicates that this partition does not need further split, and move to the next partition.
         */
        return (new Pair<Integer, Integer>(0, 0)); 
      }
      
    } else if (tokenCount == 2) {
      /*
       * If there are only two tokens in a length, then P1 and P2 are these two positions, respectively
       */
      return (new Pair<Integer, Integer>(0, 1));
    } else {
      return (new Pair<Integer, Integer>(0, 0)); 
    }
    
  }
  
  
  /**
   * Get the mapping positions; Assume Pa is before P2
   * @param partitionIn, tokenCollection, tokenCount
   * @return Pair<Integer, Integer>
   */
  private Pair<Integer, Integer> getMappingPositions(Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionEntry, 
  																											List<HashMap<String, Integer>> tokenCollection, 
                                                        Pair<Integer, ArrayList<Integer>> positionCardinality) {
  	
    Pair<Integer, Integer> tempPair = new Pair<>(0, 1);
    ArrayList<Integer> cardinality = positionCardinality.getRight();
    HashMap<Integer, Integer> cardinalityCollection = new HashMap<>();
    
    /*
     * Get the cardinality collection
     */
    for (int i = 0; i < cardinality.size(); i++) {
      Integer key = cardinality.get(i);
      cardinalityCollection.put(key, cardinalityCollection.containsKey(key) ? (cardinalityCollection.get(key) + 1) : 1);
    }
    /* -------------------- For debugging ---------------------- */
    //out.println(cardinality);
    //out.println(cardinalityCollection);
    /* -------------------- For debugging ---------------------- */
    cardinalityCollection.remove(1);
    
    /*
     * Get the frequentCardinality
     */
    Pair<Integer, Integer> freqCardPosition1 = getFrequentCardinality(cardinalityCollection);
    Integer frequentCardinality1 = freqCardPosition1.getLeft();
    Integer cardinalityFrequency1 = freqCardPosition1.getRight();
    /* -------------------- For debugging ---------------------- */
    //out.println(frequentCardinality1 + " + " + cardinalityFrequency1);
    /* -------------------- For debugging ---------------------- */
    
    /*
     * Set the P1 and P2
     */
    Integer p1 = cardinality.indexOf(frequentCardinality1);
    /* -------------------- For debugging ---------------------- */
    //out.println(p1);
    /* -------------------- For debugging ---------------------- */
    if (cardinalityFrequency1 > 1) {
      tempPair.setLeft(p1);
      /*
       * p2 is the second token position with frequentCardinality1
       */
      tempPair.setRight(cardinality.subList(p1 + 1, cardinality.size()).indexOf(frequentCardinality1) + p1 +1);
    } else if (cardinalityFrequency1 == 1) {
      tempPair.setLeft(p1);
      /*
       * p2 is the token position with the next most frequentCardinality2
       */
      cardinalityCollection.remove(frequentCardinality1);
      Pair<Integer, Integer> freqCardPosition2 = getFrequentCardinality(cardinalityCollection);
      Integer frequentCardinality2 = freqCardPosition2.getLeft();
      /* -------------------- For debugging ---------------------- */
      //Integer cardinalityFrequency2 = freqCardPosition2.getRight();
      //out.println(frequentCardinality2 + " + " + cardinalityFrequency2);
      /* -------------------- For debugging ---------------------- */
      tempPair.setRight(cardinality.indexOf(frequentCardinality2));
    }
    /* -------------------- For debugging ---------------------- */
    //out.println("P1+P2: " + tempPair.getLeft() + "+" + tempPair.getRight());
    /* -------------------- For debugging ---------------------- */
    
    return tempPair;
    
  }
  
  
  /**
   * Get the most frequent cardinality and its frequency
   */
  private Pair<Integer, Integer> getFrequentCardinality(HashMap<Integer, Integer> cardinalityCollection) {
  	
    Integer cardinalityFrequency = 0;
    Integer frequentCardinality = 0;
    for (Map.Entry<Integer, Integer> cardinalityEntry: cardinalityCollection.entrySet()) {
      if (cardinalityEntry.getValue() > cardinalityFrequency) {
        frequentCardinality = cardinalityEntry.getKey();
        cardinalityFrequency = cardinalityEntry.getValue();
      } else if (cardinalityEntry.getValue() == cardinalityFrequency) {
        /* if more than one frequent value, choose the one with lower token count*/
        frequentCardinality = (cardinalityEntry.getKey() < frequentCardinality) ? cardinalityEntry.getKey(): frequentCardinality;
      }
    }
    
    return new Pair<Integer, Integer>(frequentCardinality, cardinalityFrequency);
    
  }
  
  
  

  
  
 
  
  /* ------------------------------------------------------------------------------------ */
  /*                            Step 4 - Discover log templates                           */
  /* ------------------------------------------------------------------------------------ */
  /**
   * Summary the log templates from each partition 
   */
  public void dicoverLogTemplate(){

    Map<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionByBijection = partitionByTokenBijection();
    
    out.println("\nDiscover log templates.");
    Integer numOfTemplates = 0;
    
    for (Map.Entry<ArrayList<Object>, ArrayList<ArrayList<String>>> partitionEntry: partitionByBijection.entrySet()) {
      List<HashMap<String, Integer>> tokenCollection = tokenCollection(partitionEntry);  
      List<String> template = new ArrayList<>();
      ArrayList<String> logMatrix = partitionEntry.getValue().get(0);
      
      for (int i = 0; i < logMatrix.size(); i++) {
        String tempToken = logMatrix.get(i);
        if (tokenCollection.get(i).size() == 1) {
          template.add(tempToken);
        } else {
          template.add("*");
        }
      }
      
      out.println(template);
      numOfTemplates ++;
    }
    
    out.println("\nNumber of templates: " + numOfTemplates);
    
  }
  
  
  
  
  

  
  


}
