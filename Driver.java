import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String filePath = args[0];
        Parser parser = new Parser(filePath);
        try {
            parser.traverse();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, ArrayList<String>> sent = parser.getSent();
        HashMap<String, ArrayList<String>> received = parser.getReceived();

        Scanner scan = new Scanner(System.in);
        String cmd = new String();
        while (true) {
            System.out.print("Email Address of Individual: ");
            cmd = scan.next();
            if (cmd.equalsIgnoreCase("exit")) {
                break;
            }
            if (sent.get(cmd) == null && received.get(cmd) == null) {
                System.out.println("Email Address (" + cmd + ") not found in the dataset");
            }
            else {
                System.out.println("* " + cmd + " has sent messages to " + sent.get(cmd).size() + " others");
                System.out.println("* " + cmd + " has received messages from " + received.get(cmd).size() + " others");
            }
        }
        scan.close();
    }
}