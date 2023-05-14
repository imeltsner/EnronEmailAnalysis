import java.util.HashMap;

/**
 * Represents a node in the communication graph
 */
public class Vertex {
    //class variables
    String email;
    private HashMap<String, Vertex> connections;
    private int sentTo;
    private int receivedFrom;
    private int dfsNum;
    private int back;

    //Constructor
    public Vertex(String email) {
        this.email = email;
        this.connections = new HashMap<>();
        this.sentTo = 0;
        this.receivedFrom = 0;
        this.dfsNum = -1;
        this.back = -1;
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

    public void setDFSNum(int n) {
        dfsNum = n;
        back = n;
    }

    public void setBack(int n) {
        back = n;
    }

    public int getDFSNum() {
        return dfsNum;
    }

    public int getBack() {
        return back;
    }

    public String getEmail() {
        return email;
    }
}