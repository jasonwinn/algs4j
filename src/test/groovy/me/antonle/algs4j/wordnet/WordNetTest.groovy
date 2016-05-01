package me.antonle.algs4j.wordnet

import edu.princeton.cs.algs4.Digraph
import spock.lang.Specification

class WordNetTest extends Specification {

    def "Testing graph itself"() {
        given:
        def digraph = new Digraph(2)

        when:
        digraph.addEdge(0, 1)

        then:
        println(digraph)

    }
}
