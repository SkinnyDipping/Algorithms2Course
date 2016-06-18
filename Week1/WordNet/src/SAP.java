import edu.princeton.cs.algs4.*;

import java.util.ArrayList;


public class SAP {

    private int[] edgeTo;
    private int[] dist1;
    private int[] dist2;
    private Digraph graph;
    private int nVertices;

    private int length, ancestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = G;
        nVertices = graph.V();
        dist1 = new int[nVertices];
        dist2 = new int[nVertices];
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) throws IndexOutOfBoundsException, NullPointerException {
        process(v, w);
        return length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) throws IndexOutOfBoundsException, NullPointerException {
        process(v, w);
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) throws IndexOutOfBoundsException, NullPointerException {
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) throws IndexOutOfBoundsException, NullPointerException {
        return -1;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private void process(int v, int w) {
        for (int d = 0; d < dist1.length; d++) dist1[d] = -1;
        for (int d = 0; d < dist2.length; d++) dist2[d] = -1;
        bfs(v, dist1);
        bfs(w, dist2);
        length = Integer.MAX_VALUE;
        ancestor = -1;
        for (int i = 0; i < nVertices; i++) {
            if (dist1[i] == -1 || dist2[i] == -1) continue;
            int curr_l = dist1[i] + dist2[i];
            if (curr_l < length) {
                length = curr_l;
                ancestor = i;
            }
        }
        length = length == Integer.MAX_VALUE ? -1 : length;
    }

    private void bfs(int v, int[] dist) {
        Queue<Integer> bfs = new Queue<>();
        boolean[] visited = new boolean[nVertices];
        bfs.enqueue(v);
        dist[v] = 0;
        visited[v] = true;
        while (!bfs.isEmpty()) {
            int curr = bfs.dequeue();
            Iterable<Integer> vertices = graph.adj(curr);
            for (int vtx : vertices) {
                if (visited[vtx]) continue;
                bfs.enqueue(vtx);
                dist[vtx] = dist[curr] + 1;
            }
        }
    }

}
