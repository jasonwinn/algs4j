package me.antonle.algs4j.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int N;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufTop;
    private final int virtualTopSiteIdx;
    private final int virtualBottomSiteIdx;
    private boolean hasOpenedSites = false;

    /**
     * create N-by-N grid, with all sites blocked
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be bigger than zero");
        }
        this.N = N;
        this.grid = new boolean[N][N];
        this.uf = new WeightedQuickUnionUF(N * N + 2);
        this.ufTop = new WeightedQuickUnionUF(N * N + 1);
        this.virtualTopSiteIdx = N * N;
        this.virtualBottomSiteIdx = N * N + 1;
        // link top row to their virtual site
        for (int i = 1; i <= N; i++) {
            uf.union(virtualTopSiteIdx, toUFIndex(1, i));
            uf.union(virtualBottomSiteIdx, toUFIndex(N, i));
            ufTop.union(virtualTopSiteIdx, toUFIndex(1, i));
        }
    }

    /**
     * open site (row i, column j) if it is not open already
     */
    public void open(int i, int j) {
        checkBounds(i, j);
        if (!isOpen(i, j)) {
            hasOpenedSites = true;
            grid[i - 1][j - 1] = true;
            // check the bounds to find the neighbours
            if (i != 1 && isOpen(i - 1, j)) {
                ufTop.union(toUFIndex(i, j), toUFIndex(i - 1, j));
                uf.union(toUFIndex(i, j), toUFIndex(i - 1, j));
            }
            if (i != N && isOpen(i + 1, j)) {
                ufTop.union(toUFIndex(i, j), toUFIndex(i + 1, j));
                uf.union(toUFIndex(i, j), toUFIndex(i + 1, j));
            }
            if (j != 1 && isOpen(i, j - 1)) {
                ufTop.union(toUFIndex(i, j), toUFIndex(i, j - 1));
                uf.union(toUFIndex(i, j), toUFIndex(i, j - 1));
            }
            if (j != N && isOpen(i, j + 1)) {
                ufTop.union(toUFIndex(i, j), toUFIndex(i, j + 1));
                uf.union(toUFIndex(i, j), toUFIndex(i, j + 1));
            }
        }
    }


    /**
     * is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        return grid[i - 1][j - 1];
    }

    /**
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        checkBounds(i, j);
        return isOpen(i, j) && ufTop.connected(virtualTopSiteIdx, toUFIndex(i, j));
    }

    public boolean percolates() {
        return hasOpenedSites && uf.connected(virtualTopSiteIdx, virtualBottomSiteIdx);
    }


    private int toUFIndex(final int row, final int column) {
        return (row - 1) * N + (column - 1);
    }

    private void checkBounds(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new IndexOutOfBoundsException();
        }
    }
}
