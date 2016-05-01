package me.antonle.algs4j.wordnet;

public class Outcast {

    private final WordNet wordNet;

    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }

    public String outcast(String[] nouns) {
        int[][] distances = new int[nouns.length][nouns.length];

        for (int i = 0, sz = nouns.length; i < sz; i++) {
            for (int j = i + 1; j < sz; j++) {
                distances[i][j] = wordNet.distance(nouns[i], nouns[j]);
            }
        }

        return nouns[findMaximumDistance(distances)];
    }

    private int findMaximumDistance(int[][] distances) {
        int result = 0, maximumDistance = 0, sum;

        for (int i = 0, sz = distances.length; i < sz; i++) {
            sum = 0;

            for (int j = 0, sz2 = distances[i].length; j < sz2; j++) {
                if (j < i) {
                    sum += distances[j][i];
                } else {
                    sum += distances[i][j];
                }

                if (i == 0 || sum > maximumDistance) {
                    maximumDistance = sum;
                    result = i;
                }
            }
        }

        return result;
    }
}
