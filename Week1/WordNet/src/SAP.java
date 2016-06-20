import edu.princeton.cs.algs4.*;

public class SAP {

    private int[] dist1;
    private int[] dist2;
    private Digraph graph;
    private int nVertices;

    private int length, ancestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (hasCycle(G)) throw new IllegalArgumentException();
        graph = G;
        nVertices = graph.V();
        dist1 = new int[nVertices];
        dist2 = new int[nVertices];
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkValid(v);
        checkValid(w);
        Bag<Integer> from = new Bag<>();
        Bag<Integer> to = new Bag<>();
        from.add(v);
        to.add(w);
        process(from, to);
        return length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkValid(v);
        checkValid(w);
        Bag<Integer> from = new Bag<>();
        Bag<Integer> to = new Bag<>();
        from.add(v);
        to.add(w);
        process(from, to);
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkValid(v);
        checkValid(w);
        process(v, w);
        return length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkValid(v);
        checkValid(w);
        process(v, w);
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        Bag<Integer> first = new Bag<>();
//        Bag<Integer> second = new Bag<>();
//        first.add(3);
//        first.add(6);
//        first.add(10);
//        second.add(2);
//        second.add(6);
//        second.add(11);
//        StdOut.printf("length = %d, ance = %d\n", sap.length(first, second), sap.ancestor(first, second));
        StdOut.print(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private void process(Iterable<Integer> v, Iterable<Integer> w) {
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

    private void bfs(Iterable<Integer> initial_vertices, int[] dist) {
        Queue<Integer> bfs = new Queue<>();
        boolean[] visited = new boolean[nVertices];
        for (int v : initial_vertices) {
            bfs.enqueue(v);
            dist[v] = 0;
            visited[v] = true;
        }
//        StdOut.print("ASDASD");
        while (!bfs.isEmpty()) {
//            StdOut.printf("%d\n",bfs.size());
            int curr = bfs.dequeue();
            Iterable<Integer> vertices = graph.adj(curr);
            for (int vtx : vertices) {
                if (visited[vtx]) continue;
                bfs.enqueue(vtx);
                visited[vtx] = true;

                int prev_dist = dist[vtx];
                int curr_dist = dist[curr];
                dist[vtx] = (prev_dist != -1 && prev_dist < curr_dist) ? prev_dist : curr_dist + 1;
            }
        }
    }

    private void checkValid(int vtx) {
        if (vtx < 0 || vtx >= graph.V()) throw new IndexOutOfBoundsException();
    }

    private void checkValid(Iterable<Integer> vertices) {
        if (vertices == null) throw new NullPointerException();
        for (int vtx : vertices)
            if (vtx < 0 || vtx >= graph.V()) throw new IndexOutOfBoundsException();

    }

    private boolean hasCycle(Digraph graph) {
        Stack<Integer> dfs = new Stack<>();
        boolean[] visited = new boolean[graph.V()];

        dfs.push(0);
        dfs.push(0);
        visited[0] = true;
        while (!dfs.isEmpty()) {
            int curr = dfs.pop();
            int prev = dfs.pop();
            Iterable<Integer> vertices = graph.adj(curr);
            for (int vtx : vertices) {
                if (visited[vtx] && vtx != prev) return true;
                if (visited[vtx]) continue;
                dfs.push(curr);
                dfs.push(vtx);
                visited[vtx] = true;
            }

        }

        return false;
    }

    private void dfs(Digraph graph, int vtx) {
    }

}
