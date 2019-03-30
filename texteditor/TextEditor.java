package texteditor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mvc1009
 */
public class TextEditor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        EditableBufferedReader in = new EditableBufferedReader(
                new InputStreamReader(System.in));
        String str = null;
        try {
            str = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nline is: " + str);



    }

}
