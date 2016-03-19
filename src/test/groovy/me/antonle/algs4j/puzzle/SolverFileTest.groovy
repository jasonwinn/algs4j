package me.antonle.algs4j.puzzle

import edu.princeton.cs.algs4.In
import spock.lang.Specification
import spock.lang.Unroll

class SolverFileTest extends Specification {

    private static String REGEX_FILE = /puzzle.*/
    private static String REGEX1 = /puzzle([0-9]+).txt/
    private static String REGEX2 = /puzzle.+-([0-9]+).txt/
    private static String REGEX_UNSOLVABLE = /puzzle.+unsolvable.+txt/

    private static List<TestBoard> testData = new ArrayList<>()

    static {
        def files = []
        def dir = new File("/Users/anton/Developer/edu/algs4j/src/test/resources/8puzzle")
        dir.eachFile {
            file ->
                if (file.name.matches(REGEX_FILE)) {
                    files.add(file)
                }
        }
        for (File file  : files) {
            String fileName = file.name
            def matcher1 = fileName =~ REGEX1
            def matcher2 = fileName =~ REGEX2
            def matcher_unsol = fileName =~ REGEX_UNSOLVABLE
            int moves
            if (matcher1.matches()) {
                moves = Integer.parseInt(matcher1.group(1))
            } else if (matcher2.matches()) {
                moves = Integer.parseInt(matcher2.group(1))
            }
            else if (matcher_unsol.matches()) {
                moves = -1
            }
            Board board = getBlocksFromFile(file.absolutePath)
            testData.add(new TestBoard(moves, board, fileName))
        }
    }

    @Unroll
    def "Testing file #input.filename"() {
        given:
        TestBoard testBoard = input

        when:
        def solver = new Solver(testBoard.board)

        then:
        solver.moves() == testBoard.moves

        where:
        input << testData

    }

    private static class TestBoard {
        int moves
        Board board
        String filename

        TestBoard(int moves, Board board, String fileName) {
            this.moves = moves
            this.board = board
            this.filename = fileName
        }
    }

    private static Board getBlocksFromFile(String fileName) {
        In input = new In(fileName);
        int N = input.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = input.readInt();
        return new Board(blocks);
    }


}
