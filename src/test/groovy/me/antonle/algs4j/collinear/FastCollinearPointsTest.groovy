package me.antonle.algs4j.collinear

import spock.lang.Specification

class FastCollinearPointsTest extends Specification {

    private List<Point> points
    private FastCollinearPoints fastCollinearPoints

    void setup() {
        points = new ArrayList<>()

    }

    def "Simple check duplicates"() {
        given:
        points.add(new Point(1, 1))
        points.add(new Point(1, 0))
        points.add(new Point(1, 1))

        when:
        fastCollinearPoints = new FastCollinearPoints(points.toArray() as Point[])

        then:
        thrown IllegalArgumentException

    }

    def "NPE thrown on argument constructor"() {
        when:
        fastCollinearPoints = new FastCollinearPoints(null)

        then:
        thrown NullPointerException
    }

    def "NPE thrown on null point"() {
        given:
        points.add(new Point(1, 1))
        points.add(null)

        when:
        fastCollinearPoints = new FastCollinearPoints(points.toArray() as Point[])

        then:
        thrown NullPointerException
    }

    def "Input data is not changed"() {
        given:
        points.add(new Point(1, 1))
        points.add(new Point(1, 0))
        points.add(new Point(0, 0))
        def originalData = points.toArray(points.toArray() as Point[])
        def insertData = points.toArray(points.toArray() as Point[])

        when:
        fastCollinearPoints = new FastCollinearPoints(insertData)

        then:
        for (int i = 0; i < insertData.length; i++) {
            assert originalData[i].compareTo(insertData[i]) == 0
        }


    }
}
