package texteditor;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lsadusr12
 */
public class TextEditor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String ruta = "./Editable.txt";
        File archivo = new File(ruta);
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        EditableBufferedReader in = new EditableBufferedReader(
                new InputStreamReader(System.in));
        String str = null;
        try {
            str = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nline is: " + str);
        bw.write(str + '\n');
        bw.close();
    }

}
