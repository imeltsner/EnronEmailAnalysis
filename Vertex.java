import java.util.HashMap;

/**
 * Represents a node in the communication graph
 */
public class Vertex {
    //class variables
    private HashMap<String, Vertex> connections;
    private int sentTo;
    private int receivedFrom;

    //Constructor
    public Vertex() {
        this.connections = new HashMap<>();
        this.sentTo = 0;
        this.receivedFrom = 0;
    }

    /**
     * Creates connection in communication graph and increments sentTo count
     * @param email email of recipient
     * @param recipient graph node of recipient
     */
    public void addSentTo(String email, Vertex recipient) {
        this.sentTo++;
        connections.putIfAbsent(email, recipient);
    }

    /**
     * Creates connection in communication graph and increments received from count
     * @param email email of sender
     * @param sender graph node of sender
     */
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