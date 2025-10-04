package src;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");

        // FileGraph fileGraph = new FileGraph("src/grafo_euleriano.txt");
        // FileGraph fileGraph = new FileGraph("src/grafo_semi.txt");
        FileGraph fileGraph = new FileGraph("src/grafo_nao.txt");
        Graph g = new Graph(fileGraph);
        System.out.println(g);
        System.out.println("-------------------------------------------------\n");

        if (g.isEulerian()) {
            System.out.println("O grafo é Euleriano (possui circuito Euleriano).");
        } else if (g.isSemiEulerian()) {
            System.out.println("O grafo é Semi-Euleriano (possui trilha Euleriana).");
        } else {
            System.out.println("O grafo não é Euleriano.");
        }
    }
}
