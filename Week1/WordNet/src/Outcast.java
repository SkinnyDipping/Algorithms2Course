import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int size = nouns.length;
        int max_dist = 0;
        String max_dist_noun = "";

        for (int i = 0; i < size; i++) {
            int curr_dist = 0;
            for (int j = 0; j < size; j++) {
                curr_dist += wordNet.distance(nouns[i], nouns[j]);
            }
            if (curr_dist > max_dist) {
                max_dist = curr_dist;
                max_dist_noun = nouns[i];
            }
        }

        return max_dist_noun;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
