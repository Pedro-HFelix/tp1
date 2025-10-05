package src.algorithms;

import java.util.*;
import src.Graph;

/**
 * Implementação do Algoritmo de Fleury para encontrar caminho/ciclo Euleriano.
 *
 * Referência de base:
 * GeeksforGeeks - Fleury's Algorithm for printing Eulerian Path or Circuit
 * https://www.geeksforgeeks.org/dsa/fleurys-algorithm-for-printing-eulerian-path/
 */

// Versão mais otimizada do Fleury adaptada do GeeksForGeeks
public class feury_opt{

    // visit tracking sem limpar array
    static int[] visited;
    static int visitMark = 1;

    // Clona adjacência do Graph para Set<Integer>[] (remoção O(1) e sem duplicatas)
    @SuppressWarnings("unchecked")
    private static Set<Integer>[] cloneAdj(Graph g) {
        int V = g.V();
        Set<Integer>[] adj = new HashSet[V + 1]; // índice de 1 até V
        for (int i = 1; i <= V; i++) {
            adj[i] = new HashSet<>(); // (remoção O(1) e sem duplicatas)
            for (int v : g.adj[i]) {
                adj[i].add(v);
            }
        }
        return adj;
    }

    // Remove aresta u-v simetricamente
    static void removeEdge(Set<Integer>[] adj, int u, int v) {
        adj[u].remove(v);
        adj[v].remove(u);
    }

    // Busca em Profundidade iterativa para marcar alcançáveis a partir de 'start'
    static void dfsMark(int start, Set<Integer>[] adj) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        visited[start] = visitMark;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int nei : adj[u]) {
                if (visited[nei] != visitMark) {
                    visited[nei] = visitMark;
                    stack.push(nei);
                }
            }
        }
    }

    // Verifica se a aresta u-v é válida (não-ponte) usando uma única DFS após remoção temporária
    static boolean isValidNextEdge(int u, int v, Set<Integer>[] adj) {
        // Se u tem grau 1, a aresta é obrigatória
        if (adj[u].size() == 1) return true;

        // Remove temporariamente u-v
        adj[u].remove(v);
        adj[v].remove(u);

        // Marca alcançáveis a partir de u
        visitMark++;
        dfsMark(u, adj);

        // v é alcançável sem a aresta u-v?
        boolean reachable = (visited[v] == visitMark);

        // Restaura aresta
        adj[u].add(v);
        adj[v].add(u);

        // Se v continua alcançável, u-v não é ponte → pode usar agora
        return reachable;
    }

    // Constrói caminho/ciclo Euleriano removendo arestas válidas
    static void getEulerUtil(int u, Set<Integer>[] adj, List<int[]> edges, int totalV) {
        while (!adj[u].isEmpty()) {
            List<Integer> neighbors = new ArrayList<>(adj[u]);

            boolean moved = false;
            for (int next : neighbors) {
                if (adj[u].contains(next) && isValidNextEdge(u, next, adj)) {
                    edges.add(new int[]{u, next});
                    removeEdge(adj, u, next);
                    u = next;
                    moved = true;
                    break;
                }
            }

            // Fallback (se nenhuma aresta foi aceita pegar qualquer uma)
            if (!moved && !neighbors.isEmpty()) {
                int next = neighbors.get(0);
                edges.add(new int[]{u, next});
                removeEdge(adj, u, next);
                u = next;
            }
        }
    }

    // Função principal: retorna lista de arestas do caminho/ciclo Euleriano
    public static List<int[]> getEulerTour(Graph g) {
        Set<Integer>[] adj = cloneAdj(g); //clone

        // Inicializa visited uma vez
        if (visited == null || visited.length != g.V() + 1) {
            visited = new int[g.V() + 1];
            visitMark = 1;
        }

        // Cronometrar apenas o núcleo
        long startTime = System.nanoTime();

        // Escolhe start: se houver vértice de grau ímpar, começa nele; senão, qualquer com grau > 0
        int start = -1;
        for (int i = 1; i <= g.V(); i++) {
            if (adj[i].size() % 2 != 0) {
                start = i;
                break;
            }
        }
        if (start == -1) {
            for (int i = 1; i <= g.V(); i++) {
                if (adj[i].size() > 0) { start = i; break; }
            }
        }
        if (start == -1) {
            // Grafo sem arestas
            System.out.println("Tempo de execução do Fleury [OPT]:");
            System.out.println("≈ " + 0 + " ms");
            return Collections.emptyList();
        }

        List<int[]> edges = new ArrayList<>();
        getEulerUtil(start, adj, edges, g.V());

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Tempo de execução do Fleury [NAIVE-OPT]:");
        System.out.println("≈ " + (duration / 1_000_000.0) + " ms");

        return edges;
    }

    // Testes
    public static void main(String[] args) {
        // Criar grafo com 4 vértices (addEdge é não-direcional)
        Graph g1 = new Graph(4);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);


        // Executar Fleury otimizado
        List<int[]> res = getEulerTour(g1);



        if (!res.isEmpty() && res.getFirst()[0] == res.getLast()[1]){
            System.out.println("Grafo eureliano: Ciclo eureliano encontrado");
        }

        // Imprime o caminho Euleriano (não será usado na medição de tempo)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.size(); i++) {
            sb.append("(").append(res.get(i)[0]).append(",").append(res.get(i)[1]).append(")");
            if (i != res.size() - 1) sb.append(", ");
        }
        System.out.println(sb.toString());
    }
}