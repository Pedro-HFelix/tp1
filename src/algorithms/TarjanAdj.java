package src.algorithms;

import java.util.*;

import src.graphItens.Graph;

/**
 * Implementação do Algoritmo de Tarjan para detecção de pontes em grafos.
 *
 * Referência:
 * GeeksforGeeks - Bridges in a Graph (Tarjan’s Algorithm)
 * https://www.geeksforgeeks.org/dsa/bridge-in-a-graph/
 */
// Versão do Tarjan que não reconstrói o grafo (usando diretamente a adjacência passada por parâmetro)
public class TarjanAdj {
    private final Set<Integer>[] adj; // referência da adjacência
    private final int V;              // número de vértices
    private int time;                 // tempo de descoberta
    private boolean found;            // se a aresta alvo foi encontrada como ponte
    private int targetU, targetV;     // aresta que queremos testar

    TarjanAdj(Set<Integer>[] adj) {
        this.adj = adj;
        this.V = adj.length - 1; // índice de 1 até V
    }

    // Verifica se (u,v) é ponte
    public boolean isBridge(int u, int v) {
        this.time = 0;
        this.found = false;
        this.targetU = u;
        this.targetV = v;

        boolean[] visited = new boolean[V + 1];
        int[] disc = new int[V + 1];
        int[] low = new int[V + 1];
        int[] parent = new int[V + 1];
        Arrays.fill(parent, -1); // inicializa pais como -1

        for (int i = 1; i <= V && !found; i++) {
            if (!visited[i]) {
                buscaTarjan(i, visited, disc, low, parent);
            }
        }
        return found;
    }

    // Busca em profundidade recursiva usando Tarjan
    private void buscaTarjan(int u, boolean[] visited, int[] disc, int[] low, int[] parent) {
        if (found) return; // se ja encontrou a aresta alvo como ponte interromper a busca recursiva

        // Marca o vértice atual como visitado
        visited[u] = true;

        // Define o tempo de descoberta (disc) e o valor inicial de low
        // disc[u] = ordem em que o vértice foi descoberto
        // low[u]  = menor tempo de descoberta alcançável a partir de u
        disc[u] = low[u] = ++time;

        // Explorar todos os vizinhos de u
        for (int v : adj[u]) {

            //1 - v ainda não foi visitado → é uma aresta de árvore (tree edge)
            if (!visited[v]) {
                parent[v] = u; // define u como pai de v na árvore DFS

                // Chamada recursiva para explorar v
                buscaTarjan(v, visited, disc, low, parent);

                // Após retornar, atualiza o low[u] com base no low[v]
                // Se v ou algum descendente de v alcança um ancestral de u,
                // então low[v] será menor ou igual a disc[u]
                low[u] = Math.min(low[u], low[v]);

                // Condição de ponte:
                // Se o menor vértice alcançável a partir de v (low[v])
                // ainda é maior que o tempo de descoberta de u (disc[u]),
                // significa que não existe back-edge conectando v (ou seus descendentes) a u ou a algum ancestral de u.
                // Logo, a aresta (u,v) é uma ponte.
                if (low[v] > disc[u]) {
                    // Verifica se a aresta encontrada é justamente a que queremos testar
                    // Como o grafo é não-direcionado, testamos (u,v) e (v,u)
                    if ((u == targetU && v == targetV) || (u == targetV && v == targetU)) {
                        found = true; // marcamos que encontramos a aresta como ponte (flag de parada de chamadas recursivas)
                        return;       // forçar parada
                    }
                }
            }

            //2 - v já foi visitado e não é o pai de u então é uma aresta de retorno
            else if (v != parent[u]) {
                // Atualiza low[u] com base no tempo de descoberta de v
                // Existência de um ciclo ou caminho alternativo
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

        // ---------------- MAIN DE TESTE ----------------
    public static void main(String[] args) {
        System.out.println("Aplicando o TarjanAdj no primeiro grafo (Bridge1):");

        Graph g1 = new Graph(5);
        g1.addEdge(2, 1);
        g1.addEdge(1, 3);
        g1.addEdge(3, 2);
        g1.addEdge(1, 4);
        g1.addEdge(4, 5);

        TarjanAdj tarjan1 = new TarjanAdj(g1.getAdjSet());
        System.out.println("Aresta (4,5) é ponte? " + tarjan1.isBridge(4, 5));
        System.out.println("Aresta (1,4) é ponte? " + tarjan1.isBridge(1, 4));
        System.out.println();

        System.out.println("Aplicando o TarjanAdj no segundo grafo (Bridge2):");

        Graph g2 = new Graph(7);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(3, 1);
        g2.addEdge(2, 4);
        g2.addEdge(2, 5);
        g2.addEdge(2, 7);
        g2.addEdge(4, 6);
        g2.addEdge(5, 6);

        TarjanAdj tarjan2 = new TarjanAdj(g2.getAdjSet());
        System.out.println("Aresta (2,7) é ponte? " + tarjan2.isBridge(2, 7));
        System.out.println("Aresta (2,3) é ponte? " + tarjan2.isBridge(2, 3));
        System.out.println();

        System.out.println("Aplicando o TarjanAdj no terceiro grafo (Bridge3):");

        Graph g3 = new Graph(4);
        g3.addEdge(1, 2);
        g3.addEdge(2, 3);
        g3.addEdge(3, 4);

        TarjanAdj tarjan3 = new TarjanAdj(g3.getAdjSet());
        System.out.println("Aresta (3,4) é ponte? " + tarjan3.isBridge(3, 4));
        System.out.println("Aresta (2,3) é ponte? " + tarjan3.isBridge(2, 3));
        System.out.println("Aresta (1,2) é ponte? " + tarjan3.isBridge(1, 2));
    }

}