package me.antonle.algs4j.percolation

import edu.princeton.cs.algs4.QuickFindUF
import edu.princeton.cs.algs4.WeightedQuickUnionUF

class UnionFindAssignment {

    public static void main(String[] args) {
        quickFind(10)
        weightedUnionFind(10)
    }

    static void quickFind(int N) {
        println("Quick find")
        def quickFindUF = new QuickFindUF(N)
        for (int i = 0; i < N; i++) {
            print(quickFindUF.find(i) + " ")
        }
        quickFindUF.union(0, 1)
        quickFindUF.union(2, 1)
        quickFindUF.union(6, 1)
        quickFindUF.union(9, 0)
        quickFindUF.union(8, 0)
        quickFindUF.union(9, 5)
        println()
        for (int i = 0; i < N; i++) {
            print(quickFindUF.find(i) + " ")
        }
        println()
    }

    static void weightedUnionFind(int N) {
        println("Weighted union find")
        def unionUF = new WeightedQuickUnionUF(N)
        for (int i = 0; i < N; i++) {
            print(unionUF.find(i) + " ")
        }
        unionUF.union(1, 4)
        unionUF.union(5, 8)
        unionUF.union(0, 6)
        unionUF.union(9, 2)
        unionUF.union(2, 8)
        unionUF.union(9, 3)
        unionUF.union(1, 0)
        unionUF.union(6, 3)
        unionUF.union(3, 7)
        println()
        for (int i = 0; i < N; i++) {
            print(unionUF.find(i) + " ")
        }
    }
}
