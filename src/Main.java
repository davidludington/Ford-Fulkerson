import java.util.Arrays;

public class Main{

    public static void main(String[] args) {
        int[][] G = {
            {0, 20, 0, 0, 0},
            {0, 0, 5, 6, 0},
            {0, 0, 0, 3, 7},
            {0, 0, 0, 0, 8},
            {0, 0, 0, 0, 0}
        };

        int source = 0;
        int sink = 4;
        System.out.println("Max Flow: " + fordFulkerson(G, source, sink));
    }

    private static boolean dfs(int[][] graph, int source, int sink, int[] parent, boolean[] visited) {
        visited[source] = true;
        if (source == sink)
            return true;
        for (int v = 0; v < graph.length; v++) {
            if (graph[source][v] > 0 && !visited[v]) {
                parent[v] = source;
                if (dfs(graph, v, sink, parent, visited))
                    return true;
            }
        }
        return false;
    }

    public static int fordFulkerson(int[][] graph, int source, int sink) {
        int[] parent = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        int maxFlow = 0;

        while (dfs(graph, source, sink, parent, visited)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, graph[u][v]);
            }
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                graph[u][v] -= pathFlow;
                graph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;

            Arrays.fill(visited, false);
            Arrays.fill(parent, -1);
        }
        return maxFlow;
    }
}