package src.algorithms;

import java.util.ArrayList;
import java.util.List;

import src.graphItens.Graph;

/**
 * Implementação do método Naive para encontrar pontes.
 *
 * Referência de base:
 * GeeksforGeeks - Fleury's Algorithm for printing Eulerian Path or Circuit
 * https://www.geeksforgeeks.org/dsa/fleurys-algorithm-for-printing-eulerian-path/
 */
public class Naive {

    public static List<int[]> foundBridge(Graph g) {
        // lista das arestas-ponte encontradas
        List<int[]> bridge = new ArrayList<>();

        // percorre todas as arestas do grafo
        for (int u = 1; u <= g.V(); u++) {
            for (int v : g.adj(u)) {
                if (u < v) { 
                    // remove a aresta (u, v) temporariamente
                    g.removeEdge(u, v);
                    // verifica se o grafo ainda é conexo, se não for, (u, v) é uma aresta-ponte
                    if (!g.isConnected()) {
                        bridge.add(new int[]{u, v});
                    }
                    // adiciona a aresta (u, v) de volta
                    g.addEdge(u, v);
                }
            }
        }
        return bridge;
    }

    // DFS recursivo para marcar vértices alcançáveis
    private static void dfs(int u, boolean[] visited, Graph g) {
        visited[u] = true;
        for (int v : g.adj(u)) {
            if (!visited[v]) {
                dfs(v, visited, g);
            }
        }
    }

    // Testa se a aresta (u,v) é ponte
    public static boolean isBridge(Graph g, int u, int v) {
        int V = g.V();
        boolean[] visited = new boolean[V + 1];

        // Conta alcançáveis antes
        dfs(u, visited, g);
        int count1 = 0;
        for (boolean b : visited) if (b) count1++;

        // Remove (u,v)
        g.removeEdge(u, v);

        // Conta alcançáveis depois
        visited = new boolean[V + 1];
        dfs(u, visited, g);
        int count2 = 0;
        for (boolean b : visited) if (b) count2++;

        // Recoloca (u,v)
        g.addEdge(u, v);

        // Se reduziu alcançáveis, é ponte
        return count2 < count1;
    }


    public static void main(String args[]) {
        // Cria os grafos dos exemplos
        System.out.println("Aplicando o Naive no primeiro grafo (Bridge1):");

        Graph g1 = new Graph(5);
        g1.addEdge(2, 1);
        g1.addEdge(1, 3);
        g1.addEdge(3, 2);
        g1.addEdge(1, 4);
        g1.addEdge(4, 5);

        for (int[] pontes : foundBridge(g1)){
            System.out.println("Aresta-ponte encontrada: (" + pontes[0] + ", " + pontes[1] + ")");
        }
        System.out.println();

        System.out.println("Naive novo (isBridge):");
        for (int u = 1; u <= g1.V(); u++) {
            for (int v : g1.adj(u)) {
                if (u < v && isBridge(g1, u, v)) {
                    System.out.println("Aresta-ponte encontrada: (" + u + ", " + v + ")");
                }
            }
        }
        System.out.println();


        System.out.println("Aplicando o Naive no segundo grafo (Bridge2):");
        Graph g2 = new Graph(7);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(3, 1);
        g2.addEdge(2, 4);
        g2.addEdge(2, 5);
        g2.addEdge(2, 7);
        g2.addEdge(4, 6);
        g2.addEdge(5, 6);

        for (int[] pontes : foundBridge(g2)){
            System.out.println("Aresta-ponte encontrada: (" + pontes[0] + ", " + pontes[1] + ")");
        }
        System.out.println();

        System.out.println("Naive novo (isBridge):");
        for (int u = 1; u <= g2.V(); u++) {
            for (int v : g2.adj(u)) {
                if (u < v && isBridge(g2, u, v)) {
                    System.out.println("Aresta-ponte encontrada: (" + u + ", " + v + ")");
                }
            }
        }

        System.out.println();
        System.out.println("Aplicando o Naive no terceiro grafo (Bridge3):");
        Graph g3 = new Graph(4);
        g3.addEdge(1, 2);
        g3.addEdge(2, 3);
        g3.addEdge(3, 4);

        for (int[] pontes : foundBridge(g3)){
            System.out.println("Aresta-ponte encontrada: (" + pontes[0] + ", " + pontes[1] + ")");
        }

        System.out.println();
        System.out.println("Naive novo (isBridge):");
        for (int u = 1; u <= g3.V(); u++) {
            for (int v : g3.adj(u)) {
                if (u < v && isBridge(g3, u, v)) {
                    System.out.println("Aresta-ponte encontrada: (" + u + ", " + v + ")");
                }
            }
        }

    }
}