/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lsadusr12
 */
public class EditableBufferedReader2 extends BufferedReader {

    private Line linia;
    private boolean eow;  //end of write
    ArrayList<Character> texto = new ArrayList<Character>();
    private int numChars;
    private int puntero;
    private boolean insertMode;
    private String str;

    public EditableBufferedReader2(Reader in) {
        super(in);
        linia = new Line();
        eow = false;
        numChars = 0;
        puntero = 0;
        insertMode = false;
        str = "";

    }

    public void setRaw() throws InterruptedException, IOException {
        String[] cmd = {"sh", "-c", "stty -echo raw</dev/tty"};
        Runtime.getRuntime().exec(cmd);
    }

    public void unsetRaw() throws IOException, InterruptedException {
        String[] cmd = {"sh", "-c", "stty echo cooked</dev/tty"};
        Runtime.getRuntime().exec(cmd);
    }

    public int read() throws IOException {
        //int[] comand = new int[5];
        int key;
        key = super.read();
        if (key == 13) {
            eow = true;                                                         //Intro
        } else if (key == 27) {                                                 //Escape Mode
            key = super.read();
            if (key == 91) {
                key = super.read();
                if ((char) key == 'D') {                                        // 200 -> LEFT ARROW
                    key = 200;
                } else if ((char) key == 'C') {                                 // 201 -> RIGHT ARROW
                    key = 201;
                } else if ((char) key == 'A' || (char) key == 'B') {            // 202 -> UP & DOWN ARROWS
                    key = 202;
                } else if ((char) key == 'H') {                                 // 203 -> START
                    key = 203;
                } else if ((char) key == 'F') {                                 // 204 -> END
                    key = 204;
                } else if (key == 51) {                                         // 205 -> SUPR
                    key = super.read();
                    if (key == 126) {
                        key = 205;
                    }
                } else if (key == 50) {                                         // InsertMode
                    key = super.read();
                    if (key == 126) {
                        key = 202;
                        insertMode = !insertMode;
                    }

                }

            }
        }
        return key;
    }

    public String readLine() throws IOException {
        try {
            setRaw();

            while (!eow) {            //ciclo lectura
                int key = this.read();
                if (key == 200) {                   //flecha izquierda
                    if (puntero > 0) {
                        System.out.print("\033[D");
                        puntero--;
                    }
                } else if (key == 201) {          //flecha derecha
                    if (puntero < numChars) {
                        System.out.print("\033[C");
                        puntero++;
                    }
                } else if (key == 202) {
                } else if (key == 127) {
                    System.out.print("\033[D");
                    System.out.print(" ");
                    System.out.print("\033[D");
                    numChars--;
                    puntero--;

                    // borrar ultima letra del string str
                    String str1 = str.substring(0, str.length() - 1);
                    str = str1;

                } else if (key == 203) {            //inicio
                    while (puntero > 0) {
                        System.out.print("\033[D");
                        puntero--;
                    }
                } else if (key == 204) {            //final
                    while (puntero < numChars) {
                        System.out.print("\033[C");
                        puntero++;
                    }
                } else if (key == 205) {            //suprimir
                    scrollTextLeft();
                    numChars--;

                } else if (insertMode) {
                    if (puntero == numChars) {
                        numChars++;
                    }
                    String str1 = str.substring(0, puntero - 1) + (char) key + str.substring(puntero + 1);
                    str = str + (char) key;
                    System.out.print((char) key);
                    puntero++;
                } else if (!insertMode) {
                    if (puntero < numChars) {
                        scrollTextRight(key);

                    } else {
                        str = str + (char) key;
                        puntero++;
                        numChars++;
                        System.out.print((char) key);
                    }
                }
            }

            unsetRaw();
            return str;
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void scrollTextLeft() {
        // Visual

        String corte = str.substring(puntero + 1);
        System.out.print(corte);
        System.out.print(" ");
        for (int i = 0; i <= corte.length(); i++) {
            System.out.print("\033[D");
        }

        // Borrar la letra en el puntero del string str y correr el lo restante a la izquierda
        String str1 = str.substring(0, str.length() - corte.length() - 1);
        str = str1 + corte;

    }

    public void scrollTextRight(int caracter) {

        //Visual
        int punteroInicial = puntero;
        String corte = str.substring(puntero);
        System.out.print((char) caracter);
        puntero++;
        System.out.print(corte);
        numChars++;

        for (int i = 0; i <= corte.length(); i++) {
            System.out.print("\033[D");
        }

        // Mover la parte posterior del puntero un caracter a la derecha
        if (punteroInicial > 0) {
            String str1 = str.substring(0, punteroInicial - 1);
            String str = str1 + (char) caracter + corte;
        }
    }

}
