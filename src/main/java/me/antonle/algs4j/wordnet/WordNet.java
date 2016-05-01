package me.antonle.algs4j.wordnet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.Map;

/**
 * WordNet data type
 * <p>
 * Corner cases.
 * All methods and the constructor should throw a java.lang.NullPointerException if any argument is null.
 * The constructor should throw a java.lang.IllegalArgumentException if the input does not correspond to a rooted DAG.
 * The distance() and sap() methods should throw a java.lang.IllegalArgumentException
 * unless both of the noun arguments are WordNet nouns.
 * <p>
 * Performance requirements.
 * Your data type should use space linear in the input size (size of synsets and hypernyms files).
 * The constructor should take time linearithmic (or better) in the input size.
 * The method isNoun() should run in time logarithmic (or better) in the number of nouns.
 * The methods distance() and sap() should run in time linear in the size of the WordNet digraph.
 * For the analysis, assume that the number of nouns per synset is bounded by a constant.
 */
public class WordNet {

    private final Map<Integer, String> id2synset;
    private final Map<String, Bag<Integer>> noun2ids;
    private final SAP sap;


    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {
        id2synset = new HashMap<>();
        noun2ids = new HashMap<>();

        readSynsets(synsets);
        Digraph digraph = createDigraph(hypernyms);
        verifyDigraph(digraph);
        sap = new SAP(digraph);

    }

    private void readSynsets(String synsetsFilename) {
        In input = new In(synsetsFilename);

        while (input.hasNextLine()) {
            String[] tokens = input.readLine().split(",");
            int id = Integer.parseInt(tokens[0]);
            String synset = tokens[1];
            id2synset.put(id, synset);

            for (String noun : synset.split(" ")) {
                Bag<Integer> bag = noun2ids.get(noun);
                if (bag == null) {
                    bag = new Bag<>();
                    noun2ids.put(noun, bag);
                }
                bag.add(id);
            }
        }
    }

    private Digraph createDigraph(String hypernymsFilename) {
        Digraph digraph = new Digraph(id2synset.size());
        In input = new In(hypernymsFilename);

        while (input.hasNextLine()) {
            String[] ids = input.readLine().split(",");
            int rootID = Integer.parseInt(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                int id = Integer.parseInt(ids[i]);
                digraph.addEdge(id, rootID);
            }
        }
        verifyDigraph(digraph);
        return digraph;
    }

    private void verifyDigraph(Digraph digraph) {
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle()) {
            throw new IllegalStateException("Not a valid DAG");
        }

        // checking that graph has only one root
        int roots = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) {
                roots++;
            }
        }
        if (roots != 1) {
            throw new IllegalStateException("Not a rooted DAG");
        }

    }

    /**
     * returns all WordNet nouns
     */
    public Iterable<String> nouns() {
        return noun2ids.keySet();
    }

    /**
     * is the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        return noun2ids.containsKey(word);
    }


    /**
     * distance between nounA and nounB (defined below)
     */
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalStateException();
        }
        return sap.length(noun2ids.get(nounA), noun2ids.get(nounB));
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in a shortest ancestral path (defined below)
     */
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalStateException();
        }
        return id2synset.get(sap.ancestor(noun2ids.get(nounA), noun2ids.get(nounB)));
    }
}
