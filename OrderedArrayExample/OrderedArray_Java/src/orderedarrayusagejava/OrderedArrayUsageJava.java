package orderedarrayusagejava;

/**
 *
 * @author magro
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import orderedarray.OrderedArray;
import orderedarray.OrderedArrayException;

/**
 * Java application that loads two times a file of records in an ordered array: The first
 * time specifying a non decreasing order on the int field of the records and the second time 
 * specifying a non decreasing lexicographical order on the String field of the records. It prints 
 * the obtained ordered arrays both times.
 * @author magro
 */
public class OrderedArrayUsageJava {
  
  private static final Charset ENCODING = StandardCharsets.UTF_8;

  
  private static void printArray(OrderedArray<Record> orderedArray) throws OrderedArrayException{
    Record currRec = null;
    int sizeArr;
    
    System.out.println("\nORDERED ARRAY OF RECORDS\n");
    sizeArr = orderedArray.size();
    for(int i=0;i<sizeArr;i++){
      currRec = orderedArray.get(i);
      System.out.println("<"+currRec.getStringField()+","+currRec.getIntegerField()+">\n");
    }
  }
  
  private static void loadArray(String filepath, OrderedArray<Record> orderedArray) 
	  throws IOException, OrderedArrayException{
    System.out.println("\nLoading data from file...\n");
    
    Path inputFilePath = Paths.get(filepath);
    try(BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)){
      String line = null;
      while((line = fileInputReader.readLine()) != null){      
        String[] lineElements = line.split(",");       
        Record record1 = new Record(lineElements[0],Integer.parseInt(lineElements[1]));
        orderedArray.add(record1);
      }
    } 
    System.out.println("\nData loaded\n");
  }
  
  private static void testWithComparisonFunction(String filepath, Comparator<Record> comparator) 
	  throws IOException, OrderedArrayException{
    OrderedArray<Record> orderedArray = new OrderedArray<>(comparator);
    loadArray(filepath,orderedArray);
    printArray(orderedArray);
  }
  
  /**
   * @param args the command line arguments. It should contain only one argument
   * specifying the filepath of the data file
   */
  public static void main(String[] args) throws IOException, OrderedArrayException, Exception {
    if(args.length < 1)
      throw new Exception("Usage: OrderedArrayUsageJava <file_name>");
    
    testWithComparisonFunction(args[0],new RecordComparatorIntField());
    testWithComparisonFunction(args[0],new RecordComparatorStringField());
  }  
    
}

