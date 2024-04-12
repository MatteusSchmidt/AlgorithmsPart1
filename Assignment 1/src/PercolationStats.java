/* *****************************************************************************
 *  Name:              Matteus Schmidt
 *  Coursera User ID:
 *  Last modified:     January 20, 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CI_95 = 1.96;
    private double[] means;
    private int trials;

    public PercolationStats(int n, int trials) {
        means = new double[trials];
        this.trials = trials;

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid and Trials must be larger than 0");
        }
        
        for (int k = 0; k < trials; k++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int i = StdRandom.uniformInt(1, n + 1);
                int j = StdRandom.uniformInt(1, n + 1);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                }
            }
            means[k] = ((double) perc.numberOfOpenSites() / (double) (n * n));
        }
    }

    public double mean() {
        return StdStats.mean(means);
    }

    public double stddev() {
        return StdStats.stddev(means);
    }

    public double confidenceLo() {
        return mean() - ((CI_95 * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + ((CI_95 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats output = new PercolationStats(Integer.parseInt(args[0]),
                                                       Integer.parseInt(args[1]));

        StdOut.printf("%-23s", "mean");
        StdOut.println(" = " + output.mean());
        StdOut.printf("%-23s", "stddev");
        StdOut.println(" = " + output.stddev());
        StdOut.println(
                "95% confidence interval = [" + output.confidenceLo() + ", " + output.confidenceHi()
                        + "]\n");
    }
}
