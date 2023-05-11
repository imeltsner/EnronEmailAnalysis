import java.util.HashMap;

public class Vertex {
    private HashMap<String, Vertex> connections;
    private int sentTo;
    private int receivedFrom;

    public Vertex() {
        this.connections = new HashMap<>();
        this.sentTo = 0;
        this.receivedFrom = 0;
    }

    public void addSentTo(String email, Vertex recipient) {
        this.sentTo++;
        connections.putIfAbsent(email, recipient);
    }

    public void addReceivedFrom(String email, Vertex sender) {
        this.receivedFrom++;
        connections.putIfAbsent(email, sender);
    }

    public int getSentTo() {
        return sentTo;
    }

    public int getReceivedFrom() {
        return receivedFrom;
    }

    public HashMap<String, Vertex> getConnections() {
        return connections;
    }
}