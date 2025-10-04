package src;

public class Main {
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
                // "src/g100000_euleriano.txt",
                // "src/g100000_semi.txt",
                // "src/g100000_nao.txt"
        };

        for (String nomeArquivo : arquivos) {
            System.out.println("Testando arquivo: " + nomeArquivo);
            FileGraph fileGraph = new FileGraph(nomeArquivo);
            Graph g = new Graph(fileGraph);

            if (g.isEulerian()) {
                System.out.println("→ O grafo é Euleriano (possui circuito Euleriano).");
            } else if (g.isSemiEulerian()) {
                System.out.println("→ O grafo é Semi-Euleriano (possui trilha Euleriana).");
            } else {
                System.out.println("→ O grafo não é Euleriano.");
            }
            System.out.println("-------------------------------------------------\n");
        }
    }
}
