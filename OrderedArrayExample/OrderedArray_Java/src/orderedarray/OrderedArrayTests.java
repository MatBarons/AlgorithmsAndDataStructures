package orderedarray;

import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * It specifies a test suite for the Ordered Array library
 * To improve readability, Java methods' naming conventions are not fully
 * respected...
 * @author magro
 */
public class OrderedArrayTests {
  
  class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i1.compareTo(i2);
    }
  }
  
  
  private Integer i1,i2,i3;
  private OrderedArray<Integer> orderedArray;
  
  @Before
  public void createOrderedArray() throws OrderedArrayException{
    i1 = -12;
    i2 = 0;
    i3 = 4;
    orderedArray = new OrderedArray<>(new IntegerComparator());
  }
  
  @Test
  public void testIsEmpty_zeroEl(){
    assertTrue(orderedArray.isEmpty());
  }
  
  @Test
  public void testIsEmpty_oneEl() throws Exception{
    orderedArray.add(i1);
    assertFalse(orderedArray.isEmpty());
  }
  
  
  @Test
  public void testSize_zeroEl() throws Exception{
    assertEquals(0,orderedArray.size());
  }
  
  @Test
  public void testSize_oneEl() throws Exception{
    orderedArray.add(i1);
    assertEquals(1,orderedArray.size());
  }
  
  @Test
  public void testSize_twoEl() throws Exception{
    orderedArray.add(i1);
    orderedArray.add(i2);
    assertEquals(2,orderedArray.size());
  }
  
  @Test
  //It directly accesses the OrderedArray instance variable orderedArray.array
  public void testAdd_oneEl() throws Exception{
    orderedArray.add(i1);
    assertTrue(i1==orderedArray.array.get(0));
  }
  
  
  @Test
  public void testGet_oneEl() throws Exception{
    orderedArray.add(i1);
    assertTrue(i1==orderedArray.get(0));
  }
  
  @Test
  //It directly access the instance variable orderedArray.array
  public void testAdd_threeEl_1() throws Exception{
    
    Integer[] arrExpected = {i1,i2,i3};
    
    orderedArray.add(i2);
    orderedArray.add(i1);
    orderedArray.add(i3);
    
    assertArrayEquals(arrExpected,orderedArray.array.toArray());
  }
  
  @Test
  //It does not directly access the instance variable orderedArray.array
  public void tesAdd_threeEl_2() throws Exception{
    
    Integer[] arrExpected = {i1,i2,i3};
    
    orderedArray.add(i2);
    orderedArray.add(i1);
    orderedArray.add(i3);
    
    Integer[] arrActual = new Integer[3];
    
    for(int i=0;i<3;i++)
      arrActual[i] = orderedArray.get(i);
    
    assertArrayEquals(arrExpected,arrActual);
  }
 
}

