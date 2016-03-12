package me.antonle.algs4j.collinear;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Point data type.
 * Create an immutable data type Point
 * <p>
 * The compareTo() method should compare points by their y-coordinates, breaking ties by their x-coordinates.
 * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
 * if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
 * <p>
 * The slopeTo() method should return the slope between the invoking point
 * (x0, y0) and the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0).
 * Treat the slope of a horizontal line segment as positive zero;
 * treat the slope of a vertical line segment as positive infinity;
 * treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
 * <p>
 * The slopeOrder() method should return a comparator that compares its
 * two argument points by the slopes they make with the invoking point (x0, y0).
 * Formally, the point (x1, y1) is less than the point (x2, y2) if and only if
 * the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
 * Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
 * <p>
 * Corner cases.
 * To avoid potential complications with integer overflow or floating-point precision,
 * you may assume that the constructor arguments x and y are each between 0 and 32,767.
 */
public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (y == that.y) {
            if (x == that.x) {
                return Double.NEGATIVE_INFINITY;
            }
            return +0d;
        } else if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        }
        return ((double) (that.y - y)) / (that.x - x);

    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    @Override
    public int compareTo(Point that) {
        int diff = this.y - that.y;
        if (diff == 0) {
            diff = this.x - that.x;
        }
        return diff;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (point1, point2) -> {
            double slopeDiff = slopeTo(point1) - slopeTo(point2);
            if (slopeDiff > 0) {
                return 1;
            } else if (slopeDiff < 0) {
                return -1;
            } else {
                return 0;
            }
        };
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

}
