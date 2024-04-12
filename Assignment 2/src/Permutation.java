/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:  123456
 *  Last modified:     Jan 20, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int intInput = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < intInput; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
