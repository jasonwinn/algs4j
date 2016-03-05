package me.antonle.algs4j.exercise

class SortAssignment {

    public static void main(String[] args) {
        insertionSortQuestion()
    }

    private static void insertionSortQuestion() {
        def arrayOfStrings = "21 36 48 88 96 42 44 70 27 77".split(" ")
        def a = new int[arrayOfStrings.length]
        for (int i = 0; i < arrayOfStrings.length; i++) {
            def num = Integer.parseInt(arrayOfStrings[i])
            a[i] = num
        }
        def exchangeCount = 6
        def sortedArray = insertionSort(a, exchangeCount)
        println("insertion sort with exchange count = $exchangeCount : $sortedArray")
        println("Answer: " + sortedArray.toString().replaceAll(",", ""))
    }


    static int[] insertionSort(int[] a, int exchangeCount) {
        println("initial array: $a")
        int count = 0
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                int k = j - 1
                if (a[k] > a[j]) {
                    swap(a, k, j)
                    println(a)
                    count++
                } else {
                    break
                }
                if (count == exchangeCount) {
                    return a
                }
            }
        }
        return a
    }

    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]
        arr[i] = arr[j]
        arr[j] = tmp
    }
}
