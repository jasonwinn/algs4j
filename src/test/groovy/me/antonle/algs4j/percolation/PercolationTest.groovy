package me.antonle.algs4j.percolation

import spock.lang.Specification

class PercolationTest extends Specification {

    Percolation percolation

    void setup() {
        percolation = new Percolation(5)

    }

    def "Testing simple 5x5 percolation"() {
        when:
        percolation.open(1, 1)
        percolation.open(2, 1)
        percolation.open(3, 1)
        percolation.open(4, 1)
        percolation.open(5, 1)

        then:
        percolation.isOpen(1, 1)
        percolation.isOpen(2, 1)
        percolation.isOpen(3, 1)
        percolation.isOpen(4, 1)
        percolation.isOpen(5, 1)
        !percolation.isFull(3, 2)
        percolation.percolates()

    }

    def "Testing tricky 5x5 no percolation"() {
        when:
        percolation.open(1, 1)
        percolation.open(2, 2)
        percolation.open(3, 3)
        percolation.open(4, 4)
        percolation.open(5, 5)

        then:
        percolation.isOpen(1, 1)
        percolation.isOpen(2, 2)
        percolation.isOpen(3, 3)
        percolation.isOpen(4, 4)
        percolation.isOpen(5, 5)
        !percolation.isFull(3, 2)
        !percolation.percolates()

    }

    def "Testing tricky 5x5 percolation"() {
        when:
        percolation.open(1, 1)
        percolation.open(2, 2)
        percolation.open(3, 3)
        percolation.open(4, 4)
        percolation.open(5, 5)

        percolation.open(1, 2)
        percolation.open(2, 3)
        percolation.open(3, 4)
        percolation.open(4, 5)

        then:
        percolation.isOpen(1, 1)
        percolation.isOpen(2, 2)
        percolation.isOpen(3, 3)
        percolation.isOpen(4, 4)
        percolation.isOpen(5, 5)
        !percolation.isFull(3, 2)
        percolation.isFull(2, 3)
        percolation.percolates()

    }
}
