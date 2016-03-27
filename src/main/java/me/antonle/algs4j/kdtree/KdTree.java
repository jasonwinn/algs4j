package me.antonle.algs4j.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 2d-tree implementation.
 * Write a mutable data type KdTree.java that uses a 2d-tree to implement the same API (but replace PointSET with KdTree).
 * A 2d-tree is a generalization of a BST to two-dimensional keys.
 * The idea is to build a BST with points in the nodes,
 * using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 * <p>
 * Search and insert.
 * The algorithms for search and insert are similar to those for BSTs,
 * but at the root we use the x-coordinate
 * (if the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right);
 * then at the next level, we use the y-coordinate
 * (if the point to be inserted has a smaller y-coordinate than the point in the node, go left; otherwise go right);
 * then at the next level the x-coordinate, and so forth.
 * <p>
 * Draw.
 * A 2d-tree divides the unit square in a simple way: all the points to the left of the root go in the left subtree;
 * all those to the right go in the right subtree; and so forth, recursively.
 * Your draw() method should draw all of the points to standard draw in black and the subdivisions in red
 * (for vertical splits) and blue (for horizontal splits).
 * This method need not be efficient—it is primarily for debugging.
 */
public class KdTree {

    private Node root;

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return size(root);
    }

    private int size(final Node nodeToCheckSize) {
        if (nodeToCheckSize == null) return 0;
        else return nodeToCheckSize.size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(final Point2D pointToInsert) {
        checkNotNull(pointToInsert, "Not supported to insert null as point");
        root = put(root, pointToInsert, 0, new RectHV(0, 0, 1, 1));
    }

    private Node put(final Node node, final Point2D pointToInsert, final int level, final RectHV rect) {
        if (node == null) {
            return new Node(level, pointToInsert, rect);
        }

        RectHV rectLeft = null;
        RectHV rectRight = null;
        double cmp = node.compare(pointToInsert);

        if (cmp < 0 && node.left == null) {
            if (level % 2 == 0) {
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                rectLeft = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
            }
        } else if (cmp >= 0 && node.right == null) {
            if (level % 2 == 0) {
                rectRight = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            } else {
                rectRight = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
            }
        }

        if (cmp < 0) node.left = put(node.left, pointToInsert, level + 1, rectLeft);
        else if (cmp > 0) node.right = put(node.right, pointToInsert, level + 1, rectRight);
        else if (!pointToInsert.equals(node.point)) node.right = put(node.right, pointToInsert, level + 1, rectRight);

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    /**
     * does the set contain the given point?
     */
    public boolean contains(final Point2D searchedPoint) {
        checkNotNull(searchedPoint, "Null is never contained in a PointSET");
        return get(root, searchedPoint, 0) != null;
    }

    private Point2D get(final Node node, final Point2D searchedPoint, final int level) {
        if (node == null) return null;

        double cmp = node.compare(searchedPoint);
        if (cmp < 0) return get(node.left, searchedPoint, level + 1);
        else if (cmp > 0) return get(node.right, searchedPoint, level + 1);
        else if (!searchedPoint.equals(node.point)) return get(node.right, searchedPoint, level + 1);
        else return node.point;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(root);
    }

    private void draw(final Node nodeToDraw) {
        if (nodeToDraw == null) return;
        StdDraw.point(nodeToDraw.point.x(), nodeToDraw.point.y());
        draw(nodeToDraw.left);
        draw(nodeToDraw.right);
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(final RectHV queryRect) {
        checkNotNull(queryRect, "Can't calculate range for a rect will point null");
        return range(queryRect, root);
    }

    private List<Point2D> range(final RectHV queryRect, final Node node) {
        if (node == null) return Collections.emptyList();

        if (node.doesSpittingLineIntersect(queryRect)) {
            List<Point2D> points = new ArrayList<>();
            if (queryRect.contains(node.point)) {
                points.add(node.point);
            }
            points.addAll(range(queryRect, node.left));
            points.addAll(range(queryRect, node.right));
            return points;
        } else {
            if (node.isRightOf(queryRect)) return range(queryRect, node.left);
            else return range(queryRect, node.right);
        }
    }

    /**
     * a nearest neighbor in the set to point queryPoint; null if the set is empty
     */
    public Point2D nearest(final Point2D queryPoint) {
        checkNotNull(queryPoint, "Can't calculate nearest point to a point with point null");
        if (root == null) {
            return null;
        }
        return nearest(queryPoint, root, root.point, queryPoint.distanceTo(root.point));
    }

    private Point2D nearest(final Point2D queryPoint, final Node node, final Point2D currentlyClosestPoint,
                            final double currentlyClosestDistance) {
        if (node == null) return null;
        Point2D closestPoint = currentlyClosestPoint;
        double closestDistance = currentlyClosestDistance;

        Point2D currentPoint = node.point;
        double currentDistance = queryPoint.distanceTo(currentPoint);
        if (currentDistance < closestDistance) {
            closestDistance = currentDistance;
            closestPoint = currentPoint;
        }

        double cmp = node.compare(queryPoint);
        if (cmp < 0) {
            currentPoint = nearest(queryPoint, node.left, closestPoint, closestDistance);
        } else {
            currentPoint = nearest(queryPoint, node.right, closestPoint, closestDistance);
        }

        if (currentPoint != null) {
            if (currentPoint != closestPoint) {
                closestDistance = currentPoint.distanceTo(queryPoint);
                closestPoint = currentPoint;
            }
        }

        double nodeRectDistance = -1;
        if (cmp < 0 && node.right != null) {
            nodeRectDistance = node.right.rect.distanceTo(queryPoint);
        } else if (cmp >= 0 && node.left != null) {
            nodeRectDistance = node.left.rect.distanceTo(queryPoint);
        }

        if (nodeRectDistance != -1 && nodeRectDistance < closestDistance) {
            if (cmp < 0) {
                currentPoint = nearest(queryPoint, node.right, closestPoint, closestDistance);
            } else {
                currentPoint = nearest(queryPoint, node.left, closestPoint, closestDistance);
            }
        }

        if (currentPoint != null) {
            closestPoint = currentPoint;
        }

        return closestPoint;
    }

    private static void checkNotNull(final Object o, final String messageIfObjectIsNull) {
        if (o == null) throw new NullPointerException(messageIfObjectIsNull);
    }


    private static class Node {

        private final Point2D point;
        private final RectHV rect;
        private final int level;

        private Node left, right;   // left and right subtrees
        private int size;           // number of nodes in subtree


        Node(final int level, final Point2D point, final RectHV rect) {
            this.level = level;
            this.point = point;
            this.rect = rect;
            this.size = 1;
        }

        double compare(final Point2D pointToCompare) {
            if (level % 2 == 0) {
                return pointToCompare.x() - point.x();
            } else {
                return pointToCompare.y() - point.y();
            }
        }

        boolean doesSpittingLineIntersect(final RectHV rectToCheck) {
            if (level % 2 == 0) {
                return rectToCheck.xmin() <= point.x() && point.x() <= rectToCheck.xmax();
            } else {
                return rectToCheck.ymin() <= point.y() && point.y() <= rectToCheck.ymax();
            }
        }

        boolean isRightOf(final RectHV rectToCheck) {
            if (level % 2 == 0) {
                return rectToCheck.xmin() < point.x() && rectToCheck.xmax() < point.x();
            } else {
                return rectToCheck.ymin() < point.y() && rectToCheck.ymax() < point.y();
            }
        }

    }

}
