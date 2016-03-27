package me.antonle.algs4j.kdtree

import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import spock.lang.Specification

class KdTreeTest extends Specification {

    private KdTree kdTree

    void setup() {
        kdTree = new KdTree()
    }

    def "Initial KdTree is empty"() {
        expect:
        kdTree.isEmpty()
        kdTree.size() == 0
    }

    def "Null insert throws NPE"() {
        when:
        kdTree.insert(null)

        then:
        thrown NullPointerException
    }

    def "Inserting one point"() {
        given:
        def d = new Point2D(1d, 1d)

        when:
        kdTree.insert(d)

        then:
        !kdTree.isEmpty()
        kdTree.size() == 1
        kdTree.contains(d)
    }

    def "The correct range is given"() {
        when:
        kdTree.insert(new Point2D(0.1d, 0.1d))
        kdTree.insert(new Point2D(0.2d, 0.2d))
        kdTree.insert(new Point2D(0.3, 0.3))
        kdTree.insert(new Point2D(0.4, 0.4))

        then:
        def list = []
        def iterable = kdTree.range(new RectHV(minX as double, minY as double, maxX as double, maxY as double))
        if (iterable.size() != 0) {
            iterable.each { Point2D point ->
                list << point
            }
        }
        list.size() == size

        where:
        minX | minY | maxX | maxY || size
        0d   | 0d   | 0.01 | 0.01 || 0
        0    | 0    | 0.1  | 0.1  || 1
        0.1  | 0.1  | 0.2  | 0.2  || 2
        0    | 0    | 0.4  | 0.4  || 4

    }
}
