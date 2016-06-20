import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Scanner;

public class WordNet {

    private SAP shortestAncestralPath;
    private Digraph hypernymsGraph;
    private SeparateChainingHashST<String, Bag<Integer>> nounsHashSet;
    private SeparateChainingHashST<Integer, String> synsetsHashSet;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new NullPointerException();

        int maxSynsetId = 0;

        // sysnets
        In synsetsIn = new In(synsets);
        nounsHashSet = new SeparateChainingHashST<>();
        synsetsHashSet = new SeparateChainingHashST<>();
        while (synsetsIn.hasNextLine()) {
            String line = synsetsIn.readLine();
            Scanner sc = new Scanner(line);
            sc.useDelimiter(",");
            int synsetsId = sc.nextInt();
            String synset = sc.next();
            synsetsHashSet.put(synsetsId, synset);
            String[] nouns = synset.split(" ");
            for (String noun : nouns) {
                Bag<Integer> bag = nounsHashSet.get(noun);
                if (bag == null) {
                    bag = new Bag<>();
                    nounsHashSet.put(noun, bag);
                }
                bag.add(synsetsId);
                maxSynsetId = maxSynsetId < synsetsId ? synsetsId : maxSynsetId;
            }
        }

        // hypernyms
        In hyprnymsIn = new In(hypernyms);
        hypernymsGraph = new Digraph(maxSynsetId + 1);
        while (hyprnymsIn.hasNextLine()) {
            String line = hyprnymsIn.readLine();
            Scanner sc = new Scanner(line);
            sc.useDelimiter(",");
            int v = sc.nextInt();
            while (sc.hasNext()) {
                int w = sc.nextInt();
                hypernymsGraph.addEdge(v, w);
            }
        }

        shortestAncestralPath = new SAP(hypernymsGraph);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsHashSet.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException();
        return nounsHashSet.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Bag<Integer> v = nounsHashSet.get(nounA);
        Bag<Integer> w = nounsHashSet.get(nounB);
        return shortestAncestralPath.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Bag<Integer> v = nounsHashSet.get(nounA);
        Bag<Integer> w = nounsHashSet.get(nounB);
        int ancestor = shortestAncestralPath.ancestor(v, w);
        return synsetsHashSet.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        wn.check(wn, "worm", "bird", 5, "animal animate_being beast brute creature fauna");
        wn.check(wn, "individual", "edible_fruit", 7, "physical_entity");
        wn.check(wn, "white_marlin", "mileage", 23, "entity");
        wn.check(wn, "Black_Plague", "black_marlin", 33, "entity");
        wn.check(wn, "American_water_spaniel", "histology", 27, "entity");
        wn.check(wn, "Brown_Swiss", "barrel_roll", 29, "entity");
        wn.check(wn, "Waller", "civil_action", 0, "");

//        SeparateChainingHashST<Integer, Bag<Integer>> set = new SeparateChainingHashST<>();
//        if (set.get(1) == null) StdOut.print("asdf");
//        Bag<Integer> bag = new Bag<>();
//        set.put(1, bag);
//        bag.add(666);
//        set.get(1).add(666);
//        StdOut.print(set.get(1).iterator().next());
    }

    private void check(WordNet wn, String s1, String s2, int dexp, String aexp) {
        StdOut.printf("\nDistance: %d Expected: %d\n", wn.distance(s1, s2), dexp);
        StdOut.printf("Ancestor: %s Expected: %s\n", wn.sap(s1, s2), aexp);
    }

}
