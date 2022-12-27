package minimumheap;

import java.util.Comparator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MinimumHeapTestsInteger {

    class CompareInteger implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    private Integer i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
    private MinimumHeap<Integer> heapInteger;

    @Before
    public void createMinimumHeap() throws MinimumHeapException {
        i1 = 1;
        i2 = 2;
        i3 = 3;
        i4 = 4;
        i5 = 5;
        i6 = 6;
        i7 = 7;
        i8 = 8;
        i9 = 9;
        i10 = 10;

        heapInteger = new MinimumHeap<>(new CompareInteger());
    }

    @Test
    public void testSizeEmpty() {
        assertEquals(0, heapInteger.size());
    }

    @Test(expected = MinimumHeapException.class)
    public void testNullExceptionThrown() throws MinimumHeapException {
        heapInteger.add(null);
    }

    @Test(expected = MinimumHeapException.class)
    public void testFindParentOfNullElement() throws MinimumHeapException {
        heapInteger.parent(null);
    }

    @Test(expected = MinimumHeapException.class)
    public void testFindLeftOfNullElement() throws MinimumHeapException {
        heapInteger.left(null);
    }

    @Test(expected = MinimumHeapException.class)
    public void testFindRightOfNullElement() throws MinimumHeapException {
        heapInteger.right(null);
    }

    @Test(expected = MinimumHeapException.class)
    public void testRemoveFromEmpty() throws MinimumHeapException {
        heapInteger.remove();
    }

    @Test
    public void testSizeOneEl() throws MinimumHeapException {
        heapInteger.add(i1);
        assertEquals(1, heapInteger.size());
    }

    @Test
    public void testAddOneEl() throws MinimumHeapException {
        heapInteger.add(i1);
        Object[] expected = { 1 };
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddTwoEl() throws MinimumHeapException {
        heapInteger.add(i2);
        heapInteger.add(i1);
        Object[] expected = { 1, 2 };
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddTenEl() throws MinimumHeapException {
        heapInteger.add(i5);
        heapInteger.add(i3);
        heapInteger.add(i7);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.add(i8);
        heapInteger.add(i6);
        heapInteger.add(i9);
        heapInteger.add(i10);
        heapInteger.add(i4);
        Object[] expected = { 1, 2, 6, 5, 3, 8, 7, 9, 10, 4 };
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddTenElFindParentOfOne() throws MinimumHeapException {
        heapInteger.add(i5);
        heapInteger.add(i3);
        heapInteger.add(i7);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.add(i8);
        heapInteger.add(i6);
        heapInteger.add(i9);
        heapInteger.add(i10);
        heapInteger.add(i4);
        Integer[] expected = { 1, 2, 6, 5, 3, 8, 7, 9, 10, 4 };
        assertEquals(expected[0], heapInteger.parent(i1));
    }

    @Test
    public void testAddTenElFindRightOfFour() throws MinimumHeapException {
        heapInteger.add(i5);
        heapInteger.add(i3);
        heapInteger.add(i7);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.add(i8);
        heapInteger.add(i6);
        heapInteger.add(i9);
        heapInteger.add(i10);
        heapInteger.add(i4);
        Integer[] expected = { 1, 2, 6, 5, 3, 8, 7, 9, 10, 4 };
        assertEquals(expected[9], heapInteger.right(i4));
    }

    @Test
    public void testAddTenElFindLeftOfFive() throws MinimumHeapException {
        heapInteger.add(i5);
        heapInteger.add(i3);
        heapInteger.add(i7);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.add(i8);
        heapInteger.add(i6);
        heapInteger.add(i9);
        heapInteger.add(i10);
        heapInteger.add(i4);
        Integer[] expected = { 1, 2, 6, 5, 3, 8, 7, 9, 10, 4 };
        assertEquals(expected[7], heapInteger.left(i5));
    }

    @Test
    public void testAddOneElRemoveRoot() throws MinimumHeapException {
        heapInteger.add(i1);
        heapInteger.remove();
        Object[] expected = {};
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddThreeElRemoveRoot() throws MinimumHeapException {
        heapInteger.add(i3);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.remove();
        Object[] expected = { 2, 3 };
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddTenElRemoveRoot() throws MinimumHeapException {
        heapInteger.add(i5);
        heapInteger.add(i3);
        heapInteger.add(i7);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.add(i8);
        heapInteger.add(i6);
        heapInteger.add(i9);
        heapInteger.add(i10);
        heapInteger.add(i4);
        heapInteger.remove();
        Object[] expected = { 2, 3, 6, 5, 4, 8, 7, 9, 10 };
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddThreeElDecreaseOneToFour() throws MinimumHeapException {
        heapInteger.add(i1);
        heapInteger.add(i2);
        heapInteger.add(i3);
        heapInteger.decrease(1, 4);
        Object[] expected = { 2, 4, 3 };
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }

    @Test(expected = MinimumHeapException.class)
    public void testAddThreeElDecreaseNullToOne() throws MinimumHeapException {
        heapInteger.add(i1);
        heapInteger.add(i2);
        heapInteger.add(i3);
        heapInteger.decrease(null, 1);
    }

    @Test(expected = MinimumHeapException.class)
    public void testAddThreeElDecreaseTwoToNull() throws MinimumHeapException {
        heapInteger.add(i1);
        heapInteger.add(i2);
        heapInteger.add(i3);
        heapInteger.decrease(2, null);
    }

    @Test(expected = MinimumHeapException.class)
    public void testAddThreeElDecreaseTenToOne() throws MinimumHeapException {
        heapInteger.add(i1);
        heapInteger.add(i2);
        heapInteger.add(i3);
        heapInteger.decrease(10, 1);
    }

    @Test
    public void testAddTenElDecreseNineToZero() throws MinimumHeapException {
        heapInteger.add(i5);
        heapInteger.add(i3);
        heapInteger.add(i7);
        heapInteger.add(i2);
        heapInteger.add(i1);
        heapInteger.add(i8);
        heapInteger.add(i6);
        heapInteger.add(i9);
        heapInteger.add(i10);
        heapInteger.add(i4);
        heapInteger.decrease(9, 0);
        Object[] expected = { 0, 1, 6, 2, 3, 8, 7, 5, 10, 4 };
        Object[] actual = heapInteger.toArray();
        assertArrayEquals(expected, actual);
    }
}