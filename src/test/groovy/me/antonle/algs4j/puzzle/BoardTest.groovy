package me.antonle.algs4j.puzzle

import spock.lang.Specification

class BoardTest extends Specification {

    private Board board
    public static final int[][] EXAMPLE_BLOCKS = [[8, 1, 3], [4, 0, 2], [7, 6, 5]];
    private static final int[][] GOAL_BLOCKS = [[1, 2, 3], [4, 5, 6], [7, 8, 0]];
    private static final int[][] TWIN_BLOCK = [[1, 8, 3], [4, 0, 2], [7, 6, 5]];

    void setup() {
        board = new Board(EXAMPLE_BLOCKS)
    }

    def "Dimension test"() {
        expect:
        board.dimension() == 3
    }

    def "Hamming distance test"() {
        expect:
        board.hamming() == 5
    }

    def "Manhattan distance test"() {
        expect:
        board.manhattan() == 10
    }

    def "Testing goal board"() {
        when:
        def goalBoard = new Board(GOAL_BLOCKS)

        then:
        !board.isGoal()
        goalBoard.isGoal()
    }

    def "Testing toString()"() {
        expect:
        def expectedString = "3\n" +
                " 8 1 3\n" +
                " 4 0 2\n" +
                " 7 6 5\n"
        expectedString.equals(board.toString())
    }

    def "Testing equals()"() {
        when:
        def equalBoard = new Board(EXAMPLE_BLOCKS)
        def goalBlock = new Board(GOAL_BLOCKS)

        then:
        equalBoard.equals(board)
        !goalBlock.equals(board)
    }

    def "Testing twin()"() {
        expect:
        new Board(TWIN_BLOCK).equals(board.twin())
    }

    def "Testing neighbors"() {
        given:
        def block1 = new Board([[8, 0, 3], [4, 1, 2], [7, 6, 5]] as int[][])
        def block2 = new Board([[8, 1, 3], [0, 4, 2], [7, 6, 5]] as int[][])
        def block3 = new Board([[8, 1, 3], [4, 2, 0], [7, 6, 5]] as int[][])
        def block4 = new Board([[8, 1, 3], [4, 6, 2], [7, 0, 5]] as int[][])

        when:
        def neighbors = board.neighbors()

        then:
        neighbors.contains(block1)
        neighbors.contains(block2)
        neighbors.contains(block3)
        neighbors.contains(block4)
        neighbors.size() == 4

    }
}
