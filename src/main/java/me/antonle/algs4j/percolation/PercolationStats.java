package me.antonle.algs4j.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_LEVEL = 1.96;
    private final double[] thresholds;

    /**
     * perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T) {
        if (N < 0 || T < 1) {
            throw new IllegalArgumentException("N should be > 0, T > 0");
        }
        thresholds = new double[T];
        runMonteCarloSimulations(N, T);

    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (CONFIDENCE_LEVEL * stddev() / Math.sqrt(thresholds.length));
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (CONFIDENCE_LEVEL * stddev() / Math.sqrt(thresholds.length));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            StdOut.println("You must pass two arguments");
        }

        final int N = Integer.valueOf(args[0]);
        final int T = Integer.valueOf(args[1]);

        final PercolationStats stats = new PercolationStats(N, T);

        final double mean = stats.mean();
        final double stddev = stats.stddev();
        StdOut.println("mean                    = " + mean);
        StdOut.println("stddev                  = " + stddev);
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());

    }

    private void runMonteCarloSimulations(final int N, final int T) {
        final int totalSites = N * N;

        for (int i = 0; i < T; i++) {
            int numberOfOpenSites = 0;
            Percolation percolation = new Percolation(N);

            while (!percolation.percolates()) {
                int randomRow = randInt(N);
                int randomColumn = randInt(N);
                if (!percolation.isOpen(randomRow, randomColumn)) {
                    percolation.open(randomRow, randomColumn);
                    numberOfOpenSites++;
                }
            }
            thresholds[i] = ((double) numberOfOpenSites) / totalSites;
        }
    }

    private static int randInt(final int noOfBlockedSites) {
        return StdRandom.uniform(noOfBlockedSites) + 1;
    }
}
