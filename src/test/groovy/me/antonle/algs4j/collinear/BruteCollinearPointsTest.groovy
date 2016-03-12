package me.antonle.algs4j.collinear

import spock.lang.Specification

class BruteCollinearPointsTest extends Specification {

    private List<Point> points
    private BruteCollinearPoints bruteCollinearPoints

    void setup() {
        points = new ArrayList<>()

    }

    def "Simple check duplicates"() {
        given:
        points.add(new Point(1, 1))
        points.add(new Point(1, 0))
        points.add(new Point(1, 1))

        when:
        bruteCollinearPoints = new BruteCollinearPoints(points.toArray() as Point[])

        then:
        thrown IllegalArgumentException

    }

    def "NPE thrown on argument constructor"() {
        when:
        bruteCollinearPoints = new BruteCollinearPoints(null)

        then:
        thrown NullPointerException
    }

    def "NPE thrown on null point"() {
        given:
        points.add(new Point(1, 1))
        points.add(null)

        when:
        bruteCollinearPoints = new BruteCollinearPoints(points.toArray() as Point[])

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
        bruteCollinearPoints = new BruteCollinearPoints(insertData)

        then:
        for (int i = 0; i < insertData.length; i++) {
            assert originalData[i].compareTo(insertData[i]) == 0
        }


    }
}
