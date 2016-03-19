package me.antonle.algs4j.puzzle

import spock.lang.Specification

class SolverTest extends Specification {

    def "Testing solver with example"() {
        given:
        def step0 = new Board([[0, 1, 3], [4, 2, 5], [7, 8, 6]] as int[][])
        def step1 = new Board([[1, 0, 3], [4, 2, 5], [7, 8, 6]] as int[][])
        def step2 = new Board([[1, 2, 3], [4, 0, 5], [7, 8, 6]] as int[][])
        def step3 = new Board([[1, 2, 3], [4, 5, 0], [7, 8, 6]] as int[][])
        def step4 = new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][])

        when:
        def solver = new Solver(step0)

        then:
        solver.isSolvable()
        solver.moves() == 4
        step0.equals(solver.solution().getAt(0))
        step1.equals(solver.solution().getAt(1))
        step2.equals(solver.solution().getAt(2))
        step3.equals(solver.solution().getAt(3))
        step4.equals(solver.solution().getAt(4))

    }
}
