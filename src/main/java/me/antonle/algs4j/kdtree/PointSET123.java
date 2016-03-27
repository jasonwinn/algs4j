package me.antonle.algs4j.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

import static java.util.stream.Collectors.toList;

/**
 * Brute-force implementation.
 * Write a mutable data type PointSET123.java that represents a set of points in the unit square.
 * Implement the following API by using a red-black BST (using either SET from algs4.jar or java.util.TreeSet).
 * <p>
 * Corner cases.
 * Throw a java.lang.NullPointerException if any argument is null.
 * <p>
 * Performance requirements.
 * Your implementation should support insert() and contains() in time proportional to the logarithm
 * of the number of points in the set in the worst case; it should support nearest() and range() in time proportional
 * to the number of points in the set.
 */
public class PointSET123 {

    private TreeSet<Point2D> points;

    /**
     * construct an empty set of points
     */
    public PointSET123() {
        points = new TreeSet<>();
    }


    /**
     * is the set empty?
     */
    public boolean isEmpty() {   // is the set empty?
        return points.isEmpty();
    }

    /**
     * number of points in the set
     */
    public int size() {   // number of points in the set
        return points.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        checkNotNull(p, "Not supported to insert null as point");
        points.add(p);
    }

    /**
     * does the set contain point p?
     *
     * @throws NullPointerException
     */
    public boolean contains(Point2D p) {
        checkNotNull(p, "Null is never contained in a PointSET123");
        return points.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        checkNotNull(rect, "Can't calculate range for a rect will value null");
        return points.stream().filter(rect::contains).collect(toList());
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        checkNotNull(p, "Can't calculate nearest point to a point with value null");

        Point2D nearestPoint = null;
        for (Point2D point : points) {
            if (nearestPoint == null || point.distanceTo(p) < nearestPoint.distanceTo(p)) {
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    private static void checkNotNull(Object o, String messageIfObjectIsNull) {
        if (o == null) throw new NullPointerException(messageIfObjectIsNull);
    }

}