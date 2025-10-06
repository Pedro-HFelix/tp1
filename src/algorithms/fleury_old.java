package src.algorithms;

import java.util.*;

import src.graphItens.Graph;
/**
 * Implementação do Algoritmo de Fleury para encontrar caminho/ciclo Euleriano.
 *
 * Referência de base:
 * GeeksforGeeks - Fleury's Algorithm for printing Eulerian Path or Circuit
 * https://www.geeksforgeeks.org/dsa/fleurys-algorithm-for-printing-eulerian-path/
 */

// Classe do método de Fleury com Naive simples usada como base para o Fleury mais otimizado
public class fleury_old {

    /**
     * Cria uma cópia mutável da lista de adjacência do grafo.
     * O grafo original pode estar usando uma estrutura imutável (como Bag<Integer>),
     * então aqui convertemos para List<Integer>[] para permitir remoção de arestas (contudo O(n)).
     */
    private static List<Integer>[] cloneAdj(Graph g) {
        List<Integer>[] adj = new ArrayList[g.V() + 1]; // índice de 1 até V
        for (int i = 1; i <= g.V(); i++) {
            adj[i] = new ArrayList<>();
            for (int v : g.adj[i]) {
                adj[i].add(v); // copia cada vizinho
            }
        }
        return adj;
    }

    // Remove uma aresta da lista (O(n))
    static void removeEdge(List<Integer>[] adj, int u, int v) {
        adj[u].remove(Integer.valueOf(v));
        adj[v].remove(Integer.valueOf(u));
    }


    // Busca em profundidade para contar quantos vértices são alcançáveis a partir de um vértice inicial (verificar se é ponte)
    static void dfsCount(int v, List<Integer>[] adj, boolean[] visited) {
        visited[v] = true;
        for (int neighbor : adj[v]) {
            if (!visited[neighbor]) {
                dfsCount(neighbor, adj, visited);
            }
        }
    }


    //Verifica se a aresta u-v pode ser escolhida como próxima no caminho Euleriano.
    static boolean isValidNextEdge(int u, int v, List<Integer>[] adj, int totalV) {
        // Se só existe uma aresta saindo de u, ela é obrigatória (ponte)
        if (adj[u].size() == 1) return true;

        // Conta quantos vértices são alcançáveis ANTES de remover a aresta
        boolean[] visited = new boolean[totalV + 1];
        dfsCount(u, adj, visited);
        int count1 = 0;
        for (boolean x : visited) if (x) count1++;

        // Remove temporariamente a aresta u-v
        removeEdge(adj, u, v);

        // Conta novamente quantos vértices são alcançáveis DEPOIS da remoção
        Arrays.fill(visited, false);
        dfsCount(u, adj, visited);
        int count2 = 0;
        for (boolean x : visited) if (x) count2++;

        // Restaura a aresta (pois só foi um teste)
        adj[u].add(v);
        adj[v].add(u);

        // Se a remoção diminuiu o número de vértices alcançáveis,
        // significa que u-v era uma ponte → não pode ser escolhida agora.
        return count1 == count2;
    }


    //Função recursiva que constrói o caminho Euleriano percorre arestas válidas a partir de u, removendo-as e adicionando ao resultado
    static void getEulerUtil(int u, List<Integer>[] adj, List<int[]> edges, int totalV) {
        // copia para evitar ConcurrentModificationException
        List<Integer> neighbors = new ArrayList<>(adj[u]);

        for (int next : neighbors) {
            if (adj[u].contains(next) && isValidNextEdge(u, next, adj, totalV)) {
                edges.add(new int[]{u, next});
                removeEdge(adj, u, next);
                getEulerUtil(next, adj, edges, totalV);
                break;
            }
        }
    }

    // Função principal que retorna uma Lista com o caminho/ciclo Euleriano.
    public static List<int[]> getEulerTour(Graph g) {
        List<Integer>[] adj = cloneAdj(g);

        // Cronometrar desconsiderando o clone
        long startTime = System.nanoTime();

        // Escolhe vértice inicial:
        // se houver vértice de grau ímpar, começa nele (caminho Euleriano).
        // senão, começa em 1 (circuito Euleriano).
        int start = 1;
        for (int i = 1; i <= g.V(); i++) {
            if (adj[i].size() % 2 != 0) {
                start = i;
                break;
            }
        }

        List<int[]> edges = new ArrayList<>();
        getEulerUtil(start, adj, edges, g.V());

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Tempo de execução do Fleury [NAIVE]:");
        System.out.println("≈ " + (duration / 1_000_000.0) + " ms");

        return edges;
    }

    // Testes
    public static void main(String[] args) {
        // Criar grafo com 4 vértices
        Graph g1 = new Graph(4);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);

        g1.addEdge(2, 3);

        
        g1.addEdge(3, 4);



        // Executa Fleury
        List<int[]> res = getEulerTour(g1);

        if (res.getFirst()[0] == res.getLast()[1]){
            System.out.println("Grafo eureliano: Ciclo eureliano encontrado");
        }
        // Imprime o caminho Euleriano
        for (int i = 0; i < res.size(); i++) {
            System.out.print("("+ res.get(i)[0] + "," + res.get(i)[1] + ")");
            if (i != res.size() - 1) System.out.print(", ");
        }
    }
}