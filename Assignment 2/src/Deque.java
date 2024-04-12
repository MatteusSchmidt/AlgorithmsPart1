/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:  123456
 *  Last modified:     Jan 20, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


// a SingleLinkedList which impliments generic types.
// Performance requirements.  Your deque implementation must support
// each deque operation (including construction) in constant worst-case time
public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            newNode.previous = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.previous = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    // print and call all of the different methods to ensure they are working
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println("isEmpty = " + deque.isEmpty());
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        for (Integer s : deque) {
            System.out.println(s);
        }
        Iterator<Integer> i = deque.iterator();
        while (i.hasNext()) {
            Integer s = i.next();
            StdOut.println(s);
        }
        System.out.println("isEmpty = " + deque.isEmpty());
        System.out.println("size: " + deque.size());
        System.out.println("remove last: " + deque.removeLast());
        System.out.println("remove first: " + deque.removeFirst());
        System.out.println("remove first: " + deque.removeFirst());
        System.out.println("isEmpty = " + deque.isEmpty());
        System.out.println("size: " + deque.size());
    }
}
