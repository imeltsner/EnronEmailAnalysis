import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * Traverses enron email files and constructs an unweighted undirected graph
 * representing the communication between employees
 */
public class Parser {
    //member variables
    private File root;
    private HashMap<String, Vertex> graph;

    /**
     * Constructor
     * @param filePath the path to the directory containing all files in the dataset
     */
    public Parser(String filePath) {
        root = new File(filePath);
        graph = new HashMap<>();

    }

    /**
     * Traverses filesytem
     * @throws FileNotFoundException
     */
    public void traverse() throws FileNotFoundException {
        traverse(root);
    }

    /**
     * Checks contents of every file in directory
     * @param file the file or directory to be parsed
     * @throws FileNotFoundException
     */
    private void traverse(File file) throws FileNotFoundException {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    traverse(f);
                }
                else {
                    processFile(f);
                }
            }
        }
        else {
            processFile(file);
        }
        return;
    }

    /**
     * Scans each line of a file and extracts relevant email data
     * @param file file containing email data
     * @throws FileNotFoundException
     */
    private void processFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String line = new String();
        String sender = new String();

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.startsWith("From:")) {
               sender = parseSender(line);
            }
            else if (line.startsWith("To:") || line.startsWith("\t") || line.startsWith("Bcc:") || line.startsWith("Cc:")) {
                parseRecipient(line, sender);
            }
        }
        scan.close();
    }

    /**
     * Looks for email pattern in line of file and
     * adds sender to the communication graph
     * @param line line from the email file
     * @return the email of the sender
     */
    private String parseSender(String line) {
        Matcher matcher = Pattern.compile("([a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9._-]+)").matcher(line);
        String sender = new String();

        if (matcher.find()) {
            sender = matcher.group();
            if (sender.contains("enron.com")) {
                graph.putIfAbsent(sender, new Vertex());
            }
            else {
                sender = null;
            }
        }
        return sender;
    }

    /**
     * Looks for email pattern in line of file and
     * adds recipient to the communication graph
     * @param line line from the email file
     * @param sender sender of email
     */
    private void parseRecipient(String line, String sender) {
        Matcher matcher = Pattern.compile("([a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9._-]+)").matcher(line);

        while (matcher.find()) {
            String recipient = matcher.group();
            if (graph.get(sender) != null) {
                addToGraph(recipient, sender);
            }
        }
    }

    /**
     * Adds a recipient to the graph
     * Connects sender and recipient in graph
     * Increments sendTo and receivedFrom counts
     * @param recipient
     * @param sender
     */
    private void addToGraph(String recipient, String sender) {
        if (recipient.contains("enron.com")) {
            graph.putIfAbsent(recipient, new Vertex());
            graph.get(recipient).addReceivedFrom(sender, graph.get(sender));
            graph.get(sender).addSentTo(recipient, graph.get(recipient));
        }
    }

    public HashMap<String, Vertex> getGraph() {
        return graph;
    }
}
