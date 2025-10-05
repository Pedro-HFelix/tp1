package src;

import java.util.List;
import src.algorithms.*;

public class App {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------");

        // lista de arquivos que você quer testar
        String[] arquivos = {
                // "src/g100_euleriano.txt",
                // "src/g100_semi.txt",
                // "src/g100_nao.txt",
                // "src/g1000_euleriano.txt",
                // "src/g1000_semi.txt",
                // "src/g1000_nao.txt",
                // "src/g10000_euleriano.txt",
                // "src/g10000_semi.txt",
                // "src/g10000_nao.txt",
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
                System.out.println("Aplicando o Fleury: ");
                // Executa Fleury
                List<int[]> res = Fleury.getEulerTour(g,"Tarjan");
            } else if (g.isSemiEulerian()) {
                System.out.println("O grafo é Semi-Euleriano (possui trilha Euleriana).");
                // Executa Fleury
                List<int[]> res = Fleury.getEulerTour(g,"Tarjan");
            } else {
                System.out.println("O grafo não é Euleriano.");
                System.out.println();
            }


            // if (res.getFirst()[0] == res.getLast()[1]){
            //     System.out.println("Grafo eureliano: Ciclo eureliano encontrado");
            // }
            // // Imprime o caminho Euleriano
            // for (int i = 0; i < res.size(); i++) {
            //     System.out.print("("+ res.get(i)[0] + "," + res.get(i)[1] + ")");
            //     if (i != res.size() - 1) System.out.print(", ");
            // }
        }
    }
}
