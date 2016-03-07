package me.antonle.algs4j.exercise;

public class MergeSortAssignment {

    public static void main(String[] args) {
        topDownMergeSort(parseStringToArray("15 48 34 69 35 97 61 87 78 26 57 55"));
        bottomUpMergeSort(parseStringToArray("33 19 84 42 74 89 58 23 40 54"));
    }

    private static int[] parseStringToArray(String s) {
        String[] strings = s.split(" ");
        int[] a = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            a[i] = Integer.parseInt(strings[i]);
        }
        return a;
    }

    private static int[] topDownMergeSort(int[] array) {
        System.out.println("**********************************");
        System.out.println("TOP-DOWN MERGE SORT");
        int[] aux = new int[array.length];
        sort(array, aux, 0, array.length - 1);
        System.out.println("**********************************");
        return array;
    }

    private static int[] bottomUpMergeSort(int[] array) {
        System.out.println("**********************************");
        System.out.println("BOTTOM-UP MERGE SORT");
        int N = array.length;
        int[] aux = new int[N];
        for (int n = 1; n < N; n = n + n) {
            for (int i = 0; i < N - n; i += n + n) {
                int m = i + n - 1;
                int hi = Math.min(i + n + n - 1, N - 1);
                merge(array, aux, i, m, hi);
            }
        }
        System.out.println("**********************************");
        return array;
    }

    private static void sort(int[] a, int[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = (hi - lo) / 2 + lo;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j] < aux[i]) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        printArray(a);
    }

    private static void printArray(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int a : array) {
            sb.append(a).append(" ");
        }
        System.out.println(sb.append("]"));
    }
}
