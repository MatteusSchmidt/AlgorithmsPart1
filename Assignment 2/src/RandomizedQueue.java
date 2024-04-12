/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:  123456
 *  Last modified:     Jan 20, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


/*  an array based list that sizes and resizes
    make sure to halve size when popping 1/4 resize
    double in size when at capacity

    Your randomized queue implementation must support each randomized
    queue operation (besides creating an iterator) in constant amortized time.
*/

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n = 0;
    private Item[] array;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int length) {
        Item[] copy = (Item[]) new Object[length];
        for (int i = 0; i < n; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == array.length) resize(2 * array.length);
        array[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniformInt(n);
        Item item = array[rand];
        array[rand] = array[--n];
        if (n > 0 && n == array.length / 4) resize(array.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) throw new NoSuchElementException();
        int rand = StdRandom.uniformInt(n);
        return array[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] arrayCopy = shuffle();

        private Item[] shuffle() {
            Item[] copy = (Item[]) new Object[n];
            for (int x = 0; x < n; x++) {
                copy[x] = array[x];
            }
            for (int x = 0; x < n; x++) {
                int rand = StdRandom.uniformInt(x, n);
                Item temp = copy[x];
                copy[x] = copy[rand];
                copy[rand] = temp;
            }
            return copy;
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return arrayCopy[i++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> bag = new RandomizedQueue<Integer>();
        StdOut.println(bag.isEmpty());
        StdOut.println(bag.size());
        bag.enqueue(20);
        bag.enqueue(30);
        bag.enqueue(40);
        StdOut.println();
        for (int s : bag) {
            StdOut.println(s);
        }
        StdOut.println();
        for (int s : bag) {
            StdOut.println(s);
        }
        StdOut.println();

        StdOut.println(bag.sample());
        StdOut.println(bag.dequeue());
        StdOut.println(bag.dequeue());
        StdOut.println(bag.isEmpty());
        StdOut.println(bag.size());
        for (int s : bag) {
            StdOut.println(s);
        }
    }
}
