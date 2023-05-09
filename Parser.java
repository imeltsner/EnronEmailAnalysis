import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
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

        while (scan.hasNextLine()) {
            line = scan.nextLine();
            parseLine(line);
        }
        scan.close();
    }

    private void parseLine(String line) {
        Matcher matcher = Pattern.compile("([a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9._-]+)").matcher(line);
        if (line.startsWith("From:")) {
            if (matcher.find()) {
                sent.put(matcher.group(), new ArrayList<String>());
            }
        }
    }
}
