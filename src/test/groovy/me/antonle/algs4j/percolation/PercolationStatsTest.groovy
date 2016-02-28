package me.antonle.algs4j.percolation

import spock.lang.Specification

import static org.junit.Assert.assertEquals

class PercolationStatsTest extends Specification {

    PercolationStats stats

    def "Print test"() {
        when:
        stats = new PercolationStats(100, 100)

        then:
        println("Mean = " + stats.mean())
        println("StdDev = " + stats.stddev())
        println("Confidential interval = " + stats.confidenceLo() + ", " + stats.confidenceHi())
        assertEquals(0.593, stats.mean(), stats.stddev())
    }
}
