package me.antonle.algs4j.kdtree

import edu.princeton.cs.algs4.Point2D
import edu.princeton.cs.algs4.RectHV
import spock.lang.Specification

class PointSETTest extends Specification {

    private PointSET pointSET

    void setup() {
        pointSET = new PointSET()
    }

    def "Initial pointSet is empty"() {
        expect:
        pointSET.isEmpty()
        pointSET.size() == 0
    }

    def "Null insert throws NPE"() {
        when:
        pointSET.insert(null)

        then:
        thrown NullPointerException
    }

    def "Inserting one point"() {
        given:
        def d = new Point2D(1d, 1d)

        when:
        pointSET.insert(d)

        then:
        !pointSET.isEmpty()
        pointSET.size() == 1
        pointSET.contains(d)

    }

    def "The correct range is given"() {
        when:
        pointSET.insert(new Point2D(1, 1))
        pointSET.insert(new Point2D(2, 2))
        pointSET.insert(new Point2D(3, 3))
        pointSET.insert(new Point2D(4, 4))

        then:
        def list = []
        def iterable = pointSET.range(new RectHV(minX as double, minY as double, maxX as double, maxY as double))
        if (iterable.size() != 0) {
            iterable.each { Point2D point ->
                list << point
            }
        }
        list.size() == size

        where:
        minX | minY | maxX | maxY || size
        0    | 0    | 0.1  | 0.1  || 0
        0    | 0    | 1    | 1    || 1
        1    | 1    | 2    | 2    || 2
        0    | 0    | 4    | 4    || 4

    }

    def "Testing nearest point"() {
        given:
        def nearest = new Point2D(1d, 1d)
        def farthest = new Point2D(5d, 5d)
        pointSET.insert(nearest)
        pointSET.insert(farthest)

        when:
        def actualNearest = pointSET.nearest(new Point2D(2d, 2d))

        then:
        nearest == actualNearest
    }
}
