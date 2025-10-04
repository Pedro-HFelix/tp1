package src.algorithms;

import src.Graph;
import java.util.ArrayList;
import java.util.List;

public class Naive {

    public static List<int[]> foundBridge(Graph g) {
        List<int[]> bridge = new ArrayList<>();

        for (int u = 1; u <= g.V(); u++) {
            for (int v : g.adj(u)) {
                if (u < v) { 
                    g.removeEdge(u, v);
                    if (!g.isConnected()) {
                        bridge.add(new int[]{u, v});
                    }
                    g.addEdge(u, v);
                }
            }
        }
        return bridge;
    }
}