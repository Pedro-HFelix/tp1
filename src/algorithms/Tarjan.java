package src.algorithms;
import src.Graph;

import java.util.*;

public class Tarjan {


    // Variáveis de Controle para aplicar o Tarjan a partir de um Grafo
    private int V; // Número total de vértices
    private Graph G; // Grafo passado por parâmetro
    int time = 0; // Tempo de descoberta do Tarjan


    // Aplicar o Tarjan recebendo um Grafo por parâmetro
    public Tarjan(Graph G){
        this.G = G;
        this.time = 0;
        this.V = G.V();
    }

    // Função recursiva que encontra e imprime arestas-ponte usando Busca em profundidade
    // u --> O vértice a ser visitado
    // visited[] --> controla os vértices visitados
    // disc[] --> armazena o TD (tempo de descoberta) dos vértices
    // parent[] --> armazena os pais dos vértices na árvore DFS
    void bridgeUtil(int u, boolean visited[], int disc[], int low[], int parent[]) {

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
            }

            // Atualiza o valor mínimo de u para chamadas recursivas
            else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }
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

    public static void main(String args[]) {
        // Cria os grafos dos exemplos
        System.out.println("Aplicando o Tarjan no primeiro grafo (Bridge1):");

        Graph g1 = new Graph(5);
        g1.addEdge(2, 1);
        g1.addEdge(1, 3);
        g1.addEdge(3, 2);
        g1.addEdge(1, 4);
        g1.addEdge(4, 5);

        Tarjan tarjan = new Tarjan(g1);
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

        Tarjan tarjan2 = new Tarjan(g2);
        tarjan2.bridge();
        System.out.println();

        System.out.println("Aplicando o Tarjan no terceiro grafo (Bridge3):");
        Graph g3 = new Graph(4);
        g3.addEdge(1, 2);
        g3.addEdge(2, 3);
        g3.addEdge(3, 4);

        Tarjan tarjan3 = new Tarjan(g3);
        tarjan3.bridge();
    }
}