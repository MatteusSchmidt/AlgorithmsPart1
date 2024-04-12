/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     April 6, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int n;
    private int hamming;
    private int manhattan;
    private int row0;
    private int column0;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        n = tiles.length;
        hamming = 0;
        manhattan = 0;

        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) copy[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        this.tiles = copy;

        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                if (tiles[row][column] == 0) {
                    row0 = row;
                    column0 = column;
                }
                else if (!(tiles[row][column] == (n * row) + column + 1)) hamming++;
                if (tiles[row][column] != 0) {
                    int rowCalc = (tiles[row][column] - 1) / n;
                    int colCalc = (tiles[row][column] - 1) % n;

                    if (row != rowCalc || column != colCalc) {
                        int vertDist = row - rowCalc;
                        manhattan += vertDist < 0 ? vertDist * -1 : vertDist;

                        int horizDist = column - colCalc;
                        manhattan += horizDist < 0 ? horizDist * -1 : horizDist;
                    }
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n ");
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                sb.append(tiles[row][column]);
                if (column < n - 1) sb.append(" ");
                else sb.append("\n ");
            }
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        else if (!(this.getClass() == y.getClass())) return false;
        return Arrays.deepEquals(tiles, ((Board) y).tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        neighborNullChecker(neighbors, exchange(row0, column0, row0 - 1, column0));
        neighborNullChecker(neighbors, exchange(row0, column0, row0 + 1, column0));
        neighborNullChecker(neighbors, exchange(row0, column0, row0, column0 - 1));
        neighborNullChecker(neighbors, exchange(row0, column0, row0, column0 + 1));
        return neighbors;
    }

    private void neighborNullChecker(ArrayList<Board> neighbors, int[][] list) {
        if (list != null) neighbors.add(new Board(list));
    }

    private int[][] exchange(int row, int col, int row1, int col1) {
        if (row1 < 0 || row1 >= n || col1 < 0 || col1 >= n) return null;

        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) copy[i] = Arrays.copyOf(tiles[i], tiles[i].length);

        copy[row][col] = copy[row1][col1];
        copy[row1][col1] = tiles[row][col];
        return copy;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int row;
        if (row0 == 0) row = row0 + 1;
        else row = row0 - 1;

        int column;
        if (column0 == 0) column = column0 + 1;
        else column = column0 - 1;

        return new Board(exchange(row, column0, row, column));
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        Board n = new Board(new int[][] { { 1, 2 }, { 3, 4 } });
        Board x = new Board(new int[][] { { 1, 2 }, { 3, 4 } });
        Board b = new Board(new int[][] { { 1, 2 }, { 3, 0 } });
        Board r = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } });
        StdOut.println(n.toString());
        StdOut.println(n.isGoal() + " " + b.isGoal());
        StdOut.println(x.equals(n) + " " + n.equals(b));
        StdOut.println(r.manhattan + " " + r.hamming + r.neighbors());
    }
}
