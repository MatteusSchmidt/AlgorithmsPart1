/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     January 20, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int TOP = 0;
    private final int bottom;
    private final int size;
    private final boolean[][] opened;
    private int openSites;
    private final WeightedQuickUnionUF qf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "The grid must be at least a 1x1 square. Pass a valid integer.");
        }
        size = n;
        qf = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[size][size];
        openSites = 0;
        bottom = size * size + 1;
    }

    public void open(int row, int col) {
        checkIndex(row, col);

        if (!isOpen(row, col)) {
            opened[row - 1][col - 1] = true;
            openSites++;
        }

        // first row
        if (row == 1) {
            qf.union(getQuickFindIndex(row, col), TOP);
        }

        // final row
        else if (row == size) {
            qf.union(getQuickFindIndex(row, col), bottom);
        }

        // checks for all the surrounding blocks
        if (row > 1 && isOpen(row - 1, col)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row - 1, col));
        }

        if (row < size && isOpen(row + 1, col)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col - 1));
        }

        if (col < size && isOpen(row, col + 1)) {
            qf.union(getQuickFindIndex(row, col), getQuickFindIndex(row, col + 1));
        }
    }


    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return opened[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        return qf.find(TOP) == qf.find(getQuickFindIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return qf.find(TOP) == qf.find(bottom);
    }

    // helper methods
    private int getQuickFindIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    private void checkIndex(int row, int col) {
        if (!((row <= size && row >= 1) && (col <= size && col >= 1))) {
            throw new IllegalArgumentException("Enter a valid index");
        }
    }

    public static void main(String[] args) {

    }
}
