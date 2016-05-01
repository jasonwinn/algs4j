package me.antonle.algs4j.wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Corner cases.
 * All methods should throw a java.lang.NullPointerException if any argument is null.
 * All methods should throw a java.lang.IndexOutOfBoundsException if any argument vertex is invalid—not between 0 and G.V() - 1.
 * <p>
 * Performance requirements.
 * All methods (and the constructor) should take time at most proportional to E + V in the worst case,
 * where E and V are the number of edges and vertices in the digraph, respectively.
 * Your data type should use space proportional to E + V.
 */
public class SAP {

    private final Digraph digraph;

    public SAP(Digraph digraph) {
        this.digraph = new Digraph(digraph);
    }


    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        if (!isValid(v, w)) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        int shortestPath = -1;
        Deque<Integer> ancestors = new ArrayDeque<>();

        for (int i = 0; i < this.digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                ancestors.push(i);
            }
        }

        for (Integer integer : ancestors) {
            int path = bfsV.distTo(integer) + bfsW.distTo(integer);
            if (shortestPath == -1 || path < shortestPath) {
                shortestPath = path;
            }
        }
        return shortestPath;
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        if (!isValid(v, w)) {
            throw new IndexOutOfBoundsException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        int closestAncestor = -1;
        int shortestPath = Integer.MAX_VALUE;
        Deque<Integer> ancestors = new ArrayDeque<>();

        for (int i = 0; i < this.digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                ancestors.push(i);
            }
        }

        for (Integer integer : ancestors) {
            if ((bfsV.distTo(integer) + bfsW.distTo(integer)) < shortestPath) {
                shortestPath = (bfsV.distTo(integer) + bfsW.distTo(integer));
                closestAncestor = integer;
            }
        }
        return closestAncestor;
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValid(v, w)) {
            throw new IndexOutOfBoundsException();
        }

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        int shortestPath = -1;
        Deque<Integer> ancestors = new ArrayDeque<>();

        for (int i = 0; i < this.digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                ancestors.push(i);
            }
        }

        for (Integer integer : ancestors) {
            int path = bfsV.distTo(integer) + bfsW.distTo(integer);
            if (shortestPath == -1 || path < shortestPath) {
                shortestPath = path;
            }
        }
        return shortestPath;
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValid(v, w)) {
            throw new IndexOutOfBoundsException();
        }

        int shortestPath = Integer.MAX_VALUE;
        Deque<Integer> ancestors = new ArrayDeque<>();
        int ancestor = -1;

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < this.digraph.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                ancestors.push(i);
            }
        }

        for (Integer a : ancestors) {
            if ((bfsV.distTo(a) + bfsW.distTo(a)) < shortestPath) {
                shortestPath = (bfsV.distTo(a) + bfsW.distTo(a));
                ancestor = a;
            }
        }
        return ancestor;
    }

    private boolean isValid(int v) {
        return (v >= 0 && v <= this.digraph.V() - 1);
    }

    private boolean isValid(Iterable<Integer> v) {
        for (Integer integer : v) {
            if (!isValid(integer)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(int v, int w) {
        return isValid(v) && isValid(w);
    }

    private boolean isValid(Iterable<Integer> v, Iterable<Integer> w) {
        return isValid(v) && isValid(w);
    }


}
