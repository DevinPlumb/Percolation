/* *****************************************************************************
 *  Name:    Devin Plumb
 *  NetID:   dplumb
 *  Precept: P06
 *
 *  Description:  Estimates the percolation threshold.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96; // used for CIs

    private final double[] estimates; // the individual simulated values of p
    private final int t; // number of trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new IllegalArgumentException("n is too small");
        if (trials <= 0)
            throw new IllegalArgumentException("trials is too small");

        estimates = new double[trials];
        t = trials;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row, col;
                do {
                    row = StdRandom.uniform(n);
                    col = StdRandom.uniform(n);
                } while (percolation.isOpen(row, col));
                percolation.open(row, col);
                estimates[i] += ((double) 1 / (n * n));
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(estimates);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(estimates);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean()           = %8.6f \n", stats.mean());
        StdOut.printf("stddev()         = %8.6f \n", stats.stddev());
        StdOut.printf("confidenceLow()  = %8.6f \n", stats.confidenceLow());
        StdOut.printf("confidenceHigh() = %8.6f \n", stats.confidenceHigh());
        StdOut.printf("elapsed time     = %5.3f \n", stopwatch.elapsedTime());
    }

}
