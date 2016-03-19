package me.antonle.algs4j.puzzle;

import java.util.*;

/**
 * Corner cases.
 * You may assume that the constructor receives an N-by-N array containing the N2 integers between 0 and N^2 − 1,
 * where 0 represents the blank square.
 * <p>
 * Performance requirements.
 * Your implementation should support all Board methods in time proportional to N^2 (or better) in the worst case.
 */
public class Board {

    private int[][] blocks;
    private Board[] neighbors;

    public Board(int[][] blocks) {
        this.blocks = copy(blocks);
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i], 0, copy[i], 0, blocks[i].length);
        }
        return copy;
    }

    /**
     * board dimension N
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * number of blocks out of place
     */
    public int hamming() {
        int hammingDistance = -1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int blockInPlace = i * blocks[i].length + j + 1;
                if (blocks[i][j] != blockInPlace) {
                    hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int blockInPlace = i * blocks[i].length + j + 1;
                if (blocks[i][j] != blockInPlace && blocks[i][j] != 0) {
                    int actualValue = blocks[i][j] - 1;
                    int expectedRow = actualValue / dimension();
                    int expectedCol = actualValue % dimension();
                    int manhattanForBlock = Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
                    manhattanDistance += manhattanForBlock;
                }
            }
        }
        return manhattanDistance;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * a board that is obtained by exchanging any pair of tiles
     * I dont understand why we need this
     */
    public Board twin() {
        int[][] twinBlocks = copy(blocks);

        int i = 0;
        int j = 0;
        while (twinBlocks[i][j] == 0 || twinBlocks[i][j + 1] == 0) {
            j++;
            if (j >= twinBlocks.length - 1) {
                i++;
                j = 0;
            }
        }

        exchangeBlocks(twinBlocks, i, j, i, j + 1);
        return new Board(twinBlocks);
    }

    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        return () -> {
            if (neighbors == null) {
                findNeighbors();
            }
            return new NeighborIterator();
        };
    }

    private void findNeighbors() {
        List<Board> foundNeighbors = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (blocks[i][j] != 0) {
            j++;
            if (j >= dimension()) {
                i++;
                j = 0;
            }
        }

        if (i > 0) {
            int[][] neighborBlocks = copy(blocks);
            exchangeBlocks(neighborBlocks, i - 1, j, i, j);
            foundNeighbors.add(new Board(neighborBlocks));
        }
        if (i < dimension() - 1) {
            int[][] neighborBlocks = copy(blocks);
            exchangeBlocks(neighborBlocks, i, j, i + 1, j);
            foundNeighbors.add(new Board(neighborBlocks));
        }
        if (j > 0) {
            int[][] neighborBlocks = copy(blocks);
            exchangeBlocks(neighborBlocks, i, j - 1, i, j);
            foundNeighbors.add(new Board(neighborBlocks));
        }
        if (j < dimension() - 1) {
            int[][] neighborBlocks = copy(blocks);
            exchangeBlocks(neighborBlocks, i, j, i, j + 1);
            foundNeighbors.add(new Board(neighborBlocks));
        }
        neighbors = foundNeighbors.toArray(new Board[foundNeighbors.size()]);
    }

    private void exchangeBlocks(int[][] blocks, int iFirstBlock, int jFirstBlock, int iSecondsBlock, int jSecondBlock) {
        int firstValue = blocks[iFirstBlock][jFirstBlock];
        blocks[iFirstBlock][jFirstBlock] = blocks[iSecondsBlock][jSecondBlock];
        blocks[iSecondsBlock][jSecondBlock] = firstValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(blocks.length + "\n");
        for (int[] row : blocks) {
            for (int block : row) {
                sb.append(" ").append(block);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(blocks, board.blocks);

    }

    private class NeighborIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < neighbors.length;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return neighbors[index++];
            } else {
                throw new NoSuchElementException("There is no next neighbor.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of neighbors not supported.");
        }
    }
}
