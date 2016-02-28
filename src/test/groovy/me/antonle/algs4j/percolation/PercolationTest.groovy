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
        percolation.isFull(5, 1)
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
        percolation.isFull(1, 1)
        !percolation.isFull(2, 2)
        !percolation.percolates()

    }

    def "Testing 5x5 no percolation is Full"() {
        when:
        percolation.open(1, 1)
        percolation.open(2, 1)
        percolation.open(1, 3)
        percolation.open(2, 3)

        then:
        percolation.isFull(2, 3)
        percolation.isFull(2, 1)
        !percolation.isFull(2, 2)
        !percolation.isFull(1, 2)
        !percolation.isFull(1, 4)
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

    def "Testing 5x5 backwash"() {
        when:
        percolation.open(1, 1)
        percolation.open(2, 1)
        percolation.open(3, 1)
        percolation.open(4, 1)
        percolation.open(5, 1)
        percolation.open(5, 3)
        percolation.open(4, 3)
        percolation.open(4, 4)

        then:
        percolation.percolates()
        !percolation.isFull(5, 3)
        !percolation.isFull(4, 4)

    }

    def "Simple 1x1 percolation"() {
        when:
        def percolation = new Percolation(1)

        then:
        !percolation.percolates()

    }

    def "Simple 2x2 percolation"() {
        when:
        def percolation = new Percolation(2)

        then:
        !percolation.percolates()

    }
}
