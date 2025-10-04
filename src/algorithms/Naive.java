package src.algorithms;

import src.Graph;
import java.util.ArrayList;
import java.util.List;

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

        System.out.println("Aplicando o Naive no terceiro grafo (Bridge3):");
        Graph g3 = new Graph(4);
        g3.addEdge(1, 2);
        g3.addEdge(2, 3);
        g3.addEdge(3, 4);

        for (int[] pontes : foundBridge(g3)){
            System.out.println("Aresta-ponte encontrada: (" + pontes[0] + ", " + pontes[1] + ")");
        }
    }
}