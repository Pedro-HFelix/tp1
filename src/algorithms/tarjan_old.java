package src.algorithms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import src.graphItens.Graph;
/**
 * Implementação do Algoritmo de Tarjan para detecção de pontes em grafos.
 *
 * Referência de base:
 * GeeksforGeeks - Bridges in a Graph (Tarjan’s Algorithm)
 * https://www.geeksforgeeks.org/dsa/bridge-in-a-graph/
 */
// Classe Tarjan usada como base para a adaptação no algoritmo de Fleury
public class tarjan_old {
    // Variáveis de Controle para aplicar o Tarjan a partir de um Grafo (G) recebido por parâmetro
    private int V; // Número total de vértices
    private Graph G; // Grafo passado por parâmetro
    private Set<String> bridges; // pontes
    private boolean found;   // se a aresta requisitada foi encontrada como ponte
    private int targetU, targetV; // aresta que queremos testar
    int time = 0; // Tempo de descoberta do Tarjan
    


    // Aplicar o Tarjan recebendo um Grafo por parâmetro
    public tarjan_old(Graph G){
        this.G = G;
        this.time = 0;
        this.V = G.V();
        this.bridges = new HashSet<>();
    }

    public boolean isBridge(int u, int v) {
        this.time = 0;
        this.found = false;
        this.targetU = u;
        this.targetV = v;

        boolean[] visited = new boolean[V + 1];
        int[] disc = new int[V + 1];
        int[] low = new int[V + 1];
        int[] parent = new int[V + 1];
        Arrays.fill(parent, -1);

        for (int i = 1; i <= V && !found; i++) {
            if (!visited[i]) {
                bridgeUtil(i, visited, disc, low, parent);
            }
        }
        return found;
    }

    // Função recursiva que encontra e imprime arestas-ponte usando Busca em profundidade
    // u --> O vértice a ser visitado
    // visited[] --> controla os vértices visitados
    // disc[] --> armazena o TD (tempo de descoberta) dos vértices
    // parent[] --> armazena os pais dos vértices na árvore DFS
    void bridgeUtil(int u, boolean visited[], int disc[], int low[], int parent[]) {
        if (found) return; // já encontrou, não precisa continuar
        
        // Marca o nó atual como visitado
        visited[u] = true;

        // Inicializa tempo de descoberta e valor mínimo
        disc[u] = low[u] = ++time;

        // Percorre todos os vértices adjacentes a este
        Iterator<Integer> i = G.adj[u].iterator();
        while (i.hasNext()) {
            int v = i.next(); // v é o vértice adjacente de u

            // Se v ainda não foi visitado, faz dele filho de u
            // na árvore DFS e chama recursivamente
            if (!visited[v]) {
                parent[v] = u;
                bridgeUtil(v, visited, disc, low, parent);

                // Verifica se a subárvore enraizada em v tem
                // conexão com algum ancestral de u
                low[u] = Math.min(low[u], low[v]);

                // Se o menor vértice alcançável a partir de v
                // está abaixo de u na árvore DFS, então u-v é uma ponte
                if (low[v] > disc[u])
                    System.out.println("Aresta-ponte encontrada: (" + u + ", " + v + ")");
                if (low[v] > disc[u]) {
                    if ((u == targetU && v == targetV) || (u == targetV && v == targetU)) {
                        found = true;
                        return; // já achou, pode parar
                    }
                }
            }

            // Atualiza o valor mínimo de u para chamadas recursivas
            else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }
    }

    public Set<String> getBridges() {
        boolean visited[] = new boolean[V + 1];
        int disc[] = new int[V + 1];
        int low[] = new int[V + 1];
        int parent[] = new int[V + 1];

        for (int i = 1; i <= V; i++) {
            parent[i] = -1;
            visited[i] = false;
        }

        for (int i = 1; i <= V; i++)
            if (!visited[i])
                bridgeUtil(i, visited, disc, low, parent);

        return bridges;
    }

    // Função baseada na Busca em Profundidade para encontrar todas as arestas-ponte.
    // Utiliza a função recursiva bridgeUtil()
    public void bridge() {
        // Marca todos os vértices como não visitados
        boolean visited[] = new boolean[V + 1];
        int disc[] = new int[V + 1];
        int low[] = new int[V + 1];
        int parent[] = new int[V + 1];

        // Inicializa os arrays de pai e visitados
        for (int i = 1; i <= V; i++) {
            parent[i] = 0; // Indicar Pai nulo
            visited[i] = false;
        }

        // Chama a função auxiliar recursiva para encontrar
        // arestas-ponte na árvore DFS enraizada em 'i'
        for (int i = 1; i <= V; i++)
            if (visited[i] == false)
                bridgeUtil(i, visited, disc, low, parent);
    }

    // Testes
    public static void main(String args[]) {
        // Cria os grafos dos exemplos
        System.out.println("Aplicando o Tarjan no primeiro grafo (Bridge1):");

        Graph g1 = new Graph(5);
        g1.addEdge(2, 1);
        g1.addEdge(1, 3);
        g1.addEdge(3, 2);
        g1.addEdge(1, 4);
        g1.addEdge(4, 5);

        tarjan_old tarjan = new tarjan_old (g1);
        tarjan.bridge();
        System.out.println();

        System.out.println("Aplicando o Tarjan no segundo grafo (Bridge2):");
        Graph g2 = new Graph(7);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(3, 1);
        g2.addEdge(2, 4);
        g2.addEdge(2, 5);
        g2.addEdge(2, 7);
        g2.addEdge(4, 6);
        g2.addEdge(5, 6);

        tarjan_old  tarjan2 = new tarjan_old (g2);
        tarjan2.bridge();
        System.out.println();

        System.out.println("Aplicando o Tarjan no terceiro grafo (Bridge3):");
        Graph g3 = new Graph(4);
        g3.addEdge(1, 2);
        g3.addEdge(2, 3);
        g3.addEdge(3, 4);

        tarjan_old  tarjan3 = new tarjan_old (g3);
        tarjan3.bridge();
    }
}
