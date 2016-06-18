import edu.princeton.cs.algs4.*;

import java.util.Scanner;

public class WordNet {

    private static final int MAX_SIZE = 20;

    private SAP sap;
    private Digraph hypernyms_graph;
    private int[] ids;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // sysnets
        In sysnetsIn=new In(synsets);


        // hypernyms
        In hyprnymsIn = new In(hypernyms);
        hypernyms_graph = new Digraph(MAX_SIZE);
        while (hyprnymsIn.hasNextLine()) {
            String line = hyprnymsIn.readLine();
            Scanner sc = new Scanner(line);
            sc.useDelimiter(",");
            int v = sc.nextInt();
            int w = sc.nextInt();
            hypernyms_graph.addEdge(v, w);
        }
        sap = new SAP(hypernyms_graph);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return -1;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return null;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            StdOut.printf("length=%d, anc=%d\n", wn.sap.length(v, w), wn.sap.ancestor(v, w));
        }
    }

    private int getID(String noun) {
        for (String s:nouns()){

        }
        return -1;
    }
}
