import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    File root;
    HashMap<String, ArrayList<String>> sent;
    HashMap<String, ArrayList<String>> received;

    public Parser(String filePath) {
        root = new File(filePath);
        sent = new HashMap<String, ArrayList<String>>();
        received = new HashMap<String, ArrayList<String>>();

    }

    public void traverse() throws FileNotFoundException {
        traverse(root);
    }

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

    private void processFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String line = new String();
        String sender = new String();

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.startsWith("From:")) {
               sender = parseSender(line);
            }
            else if (line.startsWith("To:") || line.startsWith("\t")) {
                parseRecipient(line, sender);
            }
        }
        scan.close();
    }

    private String parseSender(String line) {
        Matcher matcher = Pattern.compile("([a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9._-]+)").matcher(line);
        String sender = new String();

        if (matcher.find()) {
            sender = matcher.group();
            if (!sent.containsKey(sender)) {
                sent.put(sender, new ArrayList<String>());
            }
        }
        return sender;
    }

    private void parseRecipient(String line, String sender) {
        Matcher matcher = Pattern.compile("([a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9._-]+)").matcher(line);

        while (matcher.find()) {
            String recipient = matcher.group();
            if (sent.get(sender) != null) {
                sent.get(sender).add(recipient);
                if (!received.containsKey(recipient)) {
                    received.put(recipient, new ArrayList<String>());
                    received.get(recipient).add(sender);
                }
                else {
                    received.get(recipient).add(sender);
                }
            } 
        }
    }

    public HashMap<String, ArrayList<String>> getSent() {
        return sent;
    }

    public HashMap<String, ArrayList<String>> getReceived() {
        return received;
    }
}
