/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     April 6, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solver {
    private ArrayList<Board> solution = new ArrayList<>();

    private static class Node {
        private final Node previous;
        private final Board board;
        private final int manhattan;
        private final int moves;

        public Node(Node previous, Board board, int moves) {
            this.previous = previous;
            this.board = board;
            this.manhattan = board.manhattan() + moves;
            this.moves = moves;
        }
    }

    private static class NodeCompare implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            return o1.manhattan - o2.manhattan;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> minPQ = new MinPQ<>(new NodeCompare());
        MinPQ<Node> twinMinPQ = new MinPQ<>(new NodeCompare());

        Node currentNode = new Node(null, initial, 0);
        Node currentTwinNode = new Node(null, initial.twin(), 0);

        minPQ.insert(currentNode);
        twinMinPQ.insert(currentTwinNode);

        while (!currentNode.board.isGoal() && !currentTwinNode.board.isGoal()) {
            currentNode = minPQ.delMin();
            for (Board neighBoard : currentNode.board.neighbors()) {
                if (currentNode.previous == null || !currentNode.previous.board.equals(
                        neighBoard)) {
                    minPQ.insert(new Node(currentNode, neighBoard, currentNode.moves + 1));
                }
            }

            currentTwinNode = twinMinPQ.delMin();
            for (Board neighBoard : currentTwinNode.board.neighbors()) {
                if (currentTwinNode.previous == null || !currentNode.previous.board.equals(
                        neighBoard)) {
                    twinMinPQ.insert(
                            new Node(currentTwinNode, neighBoard, currentTwinNode.moves + 1));
                }
            }
        }

        if (currentNode.board.isGoal() && !currentTwinNode.board.isGoal()) {
            while (currentNode != null) {
                solution.add(currentNode.board);
                currentNode = currentNode.previous;
            }
            Collections.reverse(solution);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) return solution;
        else return null;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
