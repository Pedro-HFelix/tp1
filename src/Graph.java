package src;

import java.util.NoSuchElementException;
import java.util.Stack;

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    public Bag<Integer>[] adj;

    public Graph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V + 1];
        for (int v = 1; v <= V; v++) {
            adj[v] = new Bag<Integer>();
        }

    }

    public Graph(FileGraph fg) {

        try {

            int[] header = fg.ReadLine();
            if (header == null || header.length < 2) {
                throw new IllegalArgumentException("Arquivo inválido: primeira linha deve conter V e E");
            }

            this.V = header[0];
            this.E = 0;

            if (V < 0)
                throw new IllegalArgumentException("Número de vértices em um Graph não pode ser negativo");

            if (E < 0)
                throw new IllegalArgumentException("Número de arestas em um Graph não pode ser negativo");

            adj = (Bag<Integer>[]) new Bag[V + 1];
            for (int v = 1; v <= V; v++) {
                adj[v] = new Bag<Integer>();
            }

            int[] edge;
            while ((edge = fg.ReadLine()) != null) {
                if (edge.length < 2) {
                    throw new IllegalArgumentException("Arquivo inválido");
                }
                int v = edge[0];
                int w = edge[1];

                addEdge(v, w);
            }

            if (header[1] != E) {
                throw new IllegalArgumentException("Número de arestas lidas diferente do esperado");
            }

        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    public Graph(Graph G) {
        this.V = G.V();
        this.E = G.E();
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");

        adj = (Bag<Integer>[]) new Bag[V + 1];
        for (int v = 1; v <= V; v++) {
            adj[v] = new Bag<Integer>();
        }

        for (int v = 1; v <= G.V(); v++) {
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    public Bag<Integer>[] getAdj(){
        return this.adj;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 1 || v > V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;        
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 1; v <= V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        FileGraph fg = new FileGraph("t.txt");
        Graph g = new Graph(fg);
        System.out.println(g);
    }

    private void dfsUtil(int v, boolean[] visited) {
        visited[v] = true;
        // percorre todos os vértices adjacentes a v
        for (int neighbor : adj[v]) {
            // se o vértice ainda não foi visitado, chama dfsUtil recursivamente
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    public boolean isConnected() {
        if (V <= 1) return true; // grafos com 0 ou 1 vertices sao conexos
        
        boolean[] visited = new boolean[V + 1];
        int i;
        // encontra um vértice com grau maior que 0
        for (i = 1; i <= V; i++) {
            if (adj[i] != null && adj[i].size() > 0)
                break;
        }

        // inicia DFS a partir desse vértice com grau maior que 0
        dfsUtil(i, visited);

        // verifica se todos os vértices com grau maior que 0 foram visitados
        for (int j = 1; j <= V; j++) {
            if (!visited[j]) return false;
        }
        return true;
    }


    public void removeEdge(int v, int w) {
        // Remove a aresta entre v e w, se existir
        validateVertex(v);
        validateVertex(w);

        boolean found = false;
        Bag<Integer> newV = new Bag<>();

        // Reconstrói a lista de adjacência de v sem w
        for (int x : adj[v]) {
            if (x != w)
                newV.add(x);
            else
                found = true;
        }
        adj[v] = newV;

        // Reconstrói a lista de adjacência de w sem v
        Bag<Integer> newW = new Bag<>();
        for (int x : adj[w]) {
            if (x != v)
                newW.add(x);
        }
        adj[w] = newW;
        
        if (found) {
            E--;
        }
    }



}