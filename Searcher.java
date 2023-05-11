import java.util.ArrayDeque;
import java.util.HashMap;

public class Searcher {
    HashMap<String, Vertex> graph;

    public Searcher(HashMap<String, Vertex> graph) {
        this.graph = graph;
    }

    public int findTeamSize(String email) {
        HashMap<String, Boolean> visited = new HashMap<>();
        int teamates = bfs(email, visited);
        return teamates;
    }

    private int bfs(String email, HashMap<String, Boolean> visited) {
        ArrayDeque<Vertex> q = new ArrayDeque<>();
        int teamates = 0;
        visited.put(email, true);
        q.add(graph.get(email));

        while (!q.isEmpty()) {
            Vertex v = q.remove();
            HashMap<String, Vertex> connections = v.getConnections();
            for (String e : connections.keySet()) {
                if (visited.get(e) == null) {
                    visited.put(e, true);
                    teamates++;
                    q.add(connections.get(e));
                }
            }
        }
        return teamates;
    }
}
