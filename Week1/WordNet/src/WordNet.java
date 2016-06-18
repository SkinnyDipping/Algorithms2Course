import edu.princeton.cs.algs4.*;

import java.util.Scanner;

public class WordNet {

    private static final int MAX_SIZE = 20;

    private SAP shortestAncestralPath;
    private Digraph hypernymsGraph;
    private SeparateChainingHashST<String, Integer> nounsHashSet;
    private SeparateChainingHashST<Integer, String> synsetsHashSet;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new NullPointerException();

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
            for (String noun : nouns)
                nounsHashSet.put(noun, synsetsId);
        }

        // hypernyms
        In hyprnymsIn = new In(hypernyms);
        hypernymsGraph = new Digraph(MAX_SIZE);
        while (hyprnymsIn.hasNextLine()) {
            String line = hyprnymsIn.readLine();
            Scanner sc = new Scanner(line);
            sc.useDelimiter(",");
            int v = sc.nextInt();
            int w = sc.nextInt();
            hypernymsGraph.addEdge(v, w);
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
        int v = nounsHashSet.get(nounA);
        int w = nounsHashSet.get(nounB);
        return shortestAncestralPath.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        for (char c = 'a'; c < 'z' + 1; c++) {
            if (c == 'q') continue;
            int id = wn.nounsHashSet.get(String.valueOf(c));
            StdOut.printf("String: %s id: %d\n", c, id);
        }
        String noun = "aa";
        StdOut.printf("%s is noun: %b\n", noun, wn.isNoun(noun));
        for (String s : wn.nouns()) {
            StdOut.print(s + "\n");
        }

//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            StdOut.printf("length=%d, anc=%d\n", wn.shortestAncestralPath.length(v, w), wn.shortestAncestralPath.ancestor(v, w));
//        }
    }

}
