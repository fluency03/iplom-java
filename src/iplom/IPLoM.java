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
 * Created AT 2016-02-22
 *
 */

package iplom;

import java.io.*;
import static java.lang.System.out;

public class IPLoM {

	
  
  /*
   * Read the log file by lines
   */
  public static void readFileByLines(String fileName) {
    File file = new File(fileName);
    BufferedReader reader = null;
    
    try {
      out.println("Read the file by lines.");
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      int line = 1;
      
      while ((tempString = reader.readLine()) != null) {
        out.println("LINE " + line + ": " + tempString);
        line ++;
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
	
	
  /*
   * Read the log file by selected line
   */
  
  
  
  
  
  
  
  
  
  
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "messages";
		
		readFileByLines(fileName);

	}

}
