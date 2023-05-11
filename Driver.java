import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Reads and processes enron email data
 * Outputs statistics based on user request
 */
public class Driver {
    public static void main(String[] args) {
        String filePath = args[0];
        Parser parser = new Parser(filePath);

        //read files and create graph
        try {
            parser.traverse();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HashMap<String, Vertex> graph = parser.getGraph();
        System.out.println("Unique emails: " + graph.keySet().size());
        Searcher searcher = new Searcher(graph);
        Scanner scan = new Scanner(System.in);
        String cmd = new String();

        //get user commands
        while (true) {
            System.out.print("Email Address of Individual: ");
            cmd = scan.next();
            if (cmd.equalsIgnoreCase("exit")) {
                break;
            }
            if (graph.get(cmd) == null) {
                System.out.println("Email Address (" + cmd + ") not found in the dataset");
            }
            else {
                System.out.println("* " + cmd + " has sent messages to " + graph.get(cmd).getSentTo() + " others");
                System.out.println("* " + cmd + " has received messages from " + graph.get(cmd).getReceivedFrom() + " others");
                System.out.println("* " + cmd + " is on a team with " + searcher.findTeamSize(cmd) + " individuals");
            }
        }
        scan.close();
    }
}