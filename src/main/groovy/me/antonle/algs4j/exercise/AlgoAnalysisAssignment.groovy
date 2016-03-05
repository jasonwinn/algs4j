package me.antonle.algs4j.exercise

class AlgoAnalysisAssignment {

    public static void main(String[] args) {
        memoryUsage()
        algoComplexity()
    }

    private static void memoryUsage() {
        def overhead = 16
        def x0 = 8 * 4
        def y0 = 1
        def z0 = 8
        def arr = 8 + 24 + 208 * 4
        def sum = overhead + x0 + y0 + z0 + arr
        def padding = 8 - sum % 8;
        def total = sum + padding
        assert total % 8 == 0
        println(total % 8)
        println("Total memory $total")
    }

    private static void algoComplexity() {
        def file = new File("export.csv")
        for (int i = 128; i < 2097152; i = 2 * i) {
            def start = System.currentTimeMillis()
            algo(i)
            def delta = System.currentTimeMillis() - start
            println("$i $delta")
            file.append("$i, $delta \n")
        }
    }

    private static void algo(int N) {
        long sum = 0;
        for (long i = 1; i <= N * N; i = i * 2)
            for (long j = 0; j < i; j++)
                sum++;
    }
}
