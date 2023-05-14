import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Reads and processes enron email data
 * Outputs statistics based on user request
 */
public class Driver {
    public static void main(String[] args) {
        //error checking
        if (args.length < 1) {
            System.out.println("Usage: first argument specifies location of data set.\nOptional second argument specifies connector output file");
            System.exit(0);
        }

        //read files and create graph
        String filePath = args[0];
        Parser parser = new Parser(filePath);
        try {
            parser.traverse();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //find connectors
        HashMap<String, Vertex> graph = parser.getGraph();
        Searcher searcher = new Searcher(graph);
        if (args.length < 2) {
            searcher.findConnectors();
        }
        else {
            try {
                searcher.findConnectors(args[1]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        //get user commands
        Scanner scan = new Scanner(System.in);
        String cmd = new String();
        while (true) {
            System.out.print("Email Address of Individual: ");
            cmd = scan.next();
            if (cmd.equalsIgnoreCase("exit")) {
                break;
            }
            //email not found
            if (graph.get(cmd) == null) {
                System.out.println("Email Address (" + cmd + ") not found in the dataset");
            }
            //output statistics
            else {
                System.out.println("* " + cmd + " has sent messages to " + graph.get(cmd).getSentTo() + " others");
                System.out.println("* " + cmd + " has received messages from " + graph.get(cmd).getReceivedFrom() + " others");
                System.out.println("* " + cmd + " is on a team with " + searcher.findTeamSize(cmd) + " individuals");
            }
        }
        scan.close();
    }
}