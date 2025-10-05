package src;

import java.util.List;
import src.algorithms.*;

public class App {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");

        // lista de arquivos que você quer testar
        String[] arquivos = {
                "src/g100_euleriano.txt",
                "src/g100_semi.txt",
                "src/g100_nao.txt",
                "src/g1000_euleriano.txt",
                "src/g1000_semi.txt",
                "src/g1000_nao.txt",
                "src/g10000_euleriano.txt",
                "src/g10000_semi.txt",
                "src/g10000_nao.txt",
                "src/g100000_euleriano.txt",
                "src/g100000_semi.txt",
                "src/g100000_nao.txt"
        };

        for (String nomeArquivo : arquivos) {
            System.out.println("-------------------------------------------------\n");
            System.out.println("Testando arquivo: " + nomeArquivo);
            FileGraph fileGraph = new FileGraph(nomeArquivo);
            Graph g = new Graph(fileGraph);

            if (g.isEulerian()) {
                System.out.println("O grafo é Euleriano (possui circuito Euleriano).");
                System.out.println();

                System.out.println(">> Euleriano - Fleury com Tarjan:");
                List<int[]> res1 = Fleury.getEulerTour(g, "Tarjan");
                res1 = null; // libera referência

                System.out.println(">> Euleriano - Fleury com Naive:");
                List<int[]> res2 = Fleury.getEulerTour(g, "Naive");
                res2 = null;

            } else if (g.isSemiEulerian()) {
                System.out.println("O grafo é Semi-Euleriano (possui trilha Euleriana).");
                System.out.println();

                System.out.println(">> Semi-Euleriano - Fleury com Tarjan:");
                List<int[]> res3 = Fleury.getEulerTour(g, "Tarjan");
                res3 = null;

                System.out.println(">> Semi-Euleriano - Fleury com Naive:");
                List<int[]> res4 = Fleury.getEulerTour(g, "Naive");
                res4 = null;

            } else {
                System.out.println("O grafo não é Euleriano nem Semi-Euleriano.");
                System.out.println();
            }
        }
    }
}
