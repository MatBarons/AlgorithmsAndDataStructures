package minimumheap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * Class used to manage a {@code Minimum Heap} of {@code Generic Type}
 */
public class MinimumHeap<T> {

    private ArrayList<T> array = null;
    private Comparator<? super T> comparator = null;
    private Hashtable<T, Integer> table = null;

    /**
     * @param comparator used to compare two {@code Generic Type} elements
     * @throws MinimumHeapException when {@code comparator} is {@code null}
     */
    public MinimumHeap(Comparator<? super T> comparator) throws MinimumHeapException {
        if (comparator == null)
            throw new MinimumHeapException("\nMinimumHeap(comparator): comparator must be != null");

        this.array = new ArrayList<>();
        this.comparator = comparator;
        this.table = new Hashtable<>();
    }

    /**
     * @return the size of the {@code MinimumHeap}
     */
    public int size() {
        return this.array.size();
    }

    /**
     * @param element whose parent you want to find out
     * @return the parent element if exist, the element given otherwise
     * @throws MinimumHeapException when {@code element} is {@code null}
     */
    public T parent(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nparent(element): element must be != null");

        if (!this.table.containsKey(element))
            return element;

        Integer pos = this.table.get(element);

        if (pos == 0)
            return element;

        if (pos % 2 == 0)
            return this.array.get((pos / 2) - 1);

        return this.array.get(pos / 2);
    }

    /**
     * @param element whose left child you want to find
     * @return the left child element if exist, the element given otherwise
     * @throws MinimumHeapException when {@code element} is {@code null}
     */
    public T left(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nleft(element): element must be != null");

        if (!this.table.containsKey(element))
            return element;

        Integer pos = this.table.get(element);

        if (((2 * pos) + 1) < this.array.size())
            return this.array.get((2 * pos) + 1);
        else
            return element;
    }

    /**
     * @param element whose right child you want to find
     * @return the right child element if exist, the element given otherwise
     * @throws MinimumHeapException when {@code element} is {@code null}
     */
    public T right(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nright(element): element must be != null");

        if (!this.table.containsKey(element))
            return element;

        Integer pos = this.table.get(element);

        if (((2 * pos) + 2) < this.array.size())
            return this.array.get((2 * pos) + 2);
        else
            return element;
    }

    /**
     * @param o1 an element of the {@code MinimumHeap}
     * @param o2 an element of the {@code MinimumHeap}
     * @throws MinimumHeapException when {@code o1} or {@code o2} are {@code null}
     */
    private void swap(T o1, T o2) throws MinimumHeapException {
        if (o1 == null)
            throw new MinimumHeapException("\nswap(o1, o2): o1 must be != null");

        if (o2 == null)
            throw new MinimumHeapException("\nswap(o1, o2): o2 must be != null");

        Integer i_o1 = this.table.get(o1);
        Integer i_o2 = this.table.get(o2);
        T tmp = this.array.get(i_o1);

        this.array.set(i_o1, o2);
        this.table.put(o2, i_o1);

        this.array.set(i_o2, tmp);
        this.table.put(tmp, i_o2);
    }

    /**
     * Add an {@code element} of {@code generic type} into the {@code MinimumHeap}
     * 
     * @param element to add
     * @throws MinimumHeapException when:
     *                              <ul>
     *                              <li>{@code element} is {@code null}</li>
     *                              <li>{@code element} is already into the
     *                              {@code MinimumHeap}</li>
     *                              </ul>
     */
    public void add(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nadd(element): element must be != null");

        if (this.table.putIfAbsent(element, this.table.size()) != null)
            throw new MinimumHeapException("\nadd(element): element is already in the MinimumHeap");

        this.array.add(element);

        while ((this.comparator).compare(element, parent(element)) < 0)
            swap(element, parent(element));
    }

    /**
     * @param o1 an element of the {@code MinimumHeap}
     * @param o2 an element of the {@code MinimumHeap}
     * @throws MinimumHeapException when {@code o1} or {@code o2} are {@code null}
     */
    private T min(T o1, T o2) throws MinimumHeapException {
        if (o1 == null)
            throw new MinimumHeapException("\nmin(o1, o2): o1 must be != null");

        if (o2 == null)
            throw new MinimumHeapException("\nmin(o1, o2): o2 must be != null");

        return (((this.comparator).compare(o1, o2) < 0) ? o1 : o2);
    }

    /**
     * Makes the {@code MinimumHeap} the tree with root element the {@code element}
     * 
     * @param element where to start {@code heapify}
     * @throws MinimumHeapException when {@code element} is {@code null}
     * @see #min(T, T)
     * @see #swap(T, T)
     */
    private void heapify(T element) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\nheapify(element): element must be != null");

        T m = min(element, min(left(element), right(element)));

        if ((this.comparator).compare(element, m) != 0) {
            swap(m, element);

            heapify(element);
        }
    }

    /**
     * @return the root element in the {@code MinimumHeap}
     */
    private T root() {
        return this.array.get(0);
    }

    /**
     * @return the last element in the {@code MinimumHeap}
     */
    private T last() {
        return this.array.get(this.array.size() - 1);
    }

    /**
     * Remove the root element, then rebuild the {@code MinimumHeap}
     * 
     * @throws MinimumHeapException on an empty {@code MinimumHeap}
     */
    public T remove() throws MinimumHeapException {
        if (this.array.isEmpty())
            throw new MinimumHeapException("\nremove(): cannot remove the root element of an empty MinimumHeap");

        swap(root(), last());
        this.table.remove(last());
        T removed = this.array.remove(this.array.size() - 1);

        if (this.array.size() > 1)
            heapify(root());

        return removed;
    }

    /**
     * Change the given {@code element} with the given {@code newElement}
     * 
     * @param element    to change
     * @param newElement to change with
     * @throws MinimumHeapException when:
     *                              <ul>
     *                              <li>{@code element} is {@code null}</li>
     *                              <li>{@code newElement} is {@code null}</li>
     *                              <li>{@code element} is NOT into the
     *                              {@code MinimumHeap}</li>
     *                              </ul>
     */
    public void decrease(T element, T newElement) throws MinimumHeapException {
        if (element == null)
            throw new MinimumHeapException("\ndecrease(element, newElement): element must be != null");

        if (newElement == null)
            throw new MinimumHeapException("\ndecrease(element, newElement): newElement must be != null");

        if (!this.table.containsKey(element))
            throw new MinimumHeapException(
                    "\ndecrease(element, newElement): cannot decrease an element that is not in the MinimumHeap");

        Integer pos = this.table.get(element);
        this.table.remove(element);
        this.table.put(newElement, pos);
        this.array.set(pos, newElement);

        // move the decremented element towards the root by checking if now is less than
        // the parent, if so swap the element with his parent, than repeat until end
        while ((this.comparator).compare(newElement, parent(newElement)) < 0)
            swap(newElement, parent(newElement));

        // if the element cannot go towards the root anymore, check if it can go down
        if ((this.comparator).compare(newElement, parent(newElement)) >= 0)
            heapify(newElement);
    }

    @Override
    public String toString() {
        return this.array.toString();
    }

    /**
     * @return the {@code MinimumHeap} as an array of {@code Object}
     */
    public Object[] toArray() {
        return array.toArray();
    }
}