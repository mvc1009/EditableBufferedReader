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
public class EditableBufferedReader extends BufferedReader {

    static final char ESC = '\033';
    
    static final int LEFT = 200;
    static final int RIGHT = 201;
    static final int HOME = 203;
    static final int END = 204;
    static final int DEL = 205;
    static final int INS = 206;

    static final int USELESS = 202;
    static final int BACKSPACE = 127;

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() throws InterruptedException, IOException {
        String[] cmd = {"sh", "-c", "stty -echo raw</dev/tty"};
        Runtime.getRuntime().exec(cmd);
    }

    public void unsetRaw() throws IOException, InterruptedException {
        String[] cmd = {"sh", "-c", "stty echo cooked</dev/tty"};
        Runtime.getRuntime().exec(cmd);
    }

    @Override
    /*
    *Read es una funció que llegeix caracter a caracter, on s'ha de tenir en compte
    *els simbols següents:
    *F. Dreta:       ESC [ C
    *F. Esquerra:    ESC [ D
    *Inici Linea:    ESC [ H o ESC O H, ESC [ 1 -
    *Final Linea:    ESC [ F o ESC O F, ESC [ 4 -
    *Insertar:       ESC [ 2 -
    *SEL:            ESC [ 3 -
    *
     */
    public int read() throws IOException {
        int car = super.read();
        int num = 0;

        if (car != ESC) {
            return car;
        }

        car = super.read();
        if (car == '[' || car == 'O') {
            switch (car = super.read()) {
                case 'H':
                    return HOME;
                case 'F':
                    return END;
                case 'C':
                    return RIGHT;
                case 'D':
                    return LEFT;
                case 'B':
                    return BACKSPACE;
                case '2':
                case '3':
                    car = super.read();
                    if (car == '2' && (car = super.read()) == '~') {
                        return INS;
                    } else {
                        return DEL;
                    }

                default:
                    return car;
            }
        }
        return car;
    }

    public String readLine() throws IOException {
        try {
            setRaw();
            String str = "";
            Line line = new Line();
            int key;
            while ((key = this.read()) != '\n') {

                switch (key) {

                    case LEFT:

                        if (line.getPuntero() > 0) {
                            System.out.print("\033[D");
                            line.setPuntero(line.getPuntero() - 1);
                        }
                        break;

                    case RIGHT:

                        if (line.getPuntero() < line.getSize()) {
                            System.out.print("\033[C");
                            line.setPuntero(line.getPuntero() + 1);
                        }
                        break;

                    case HOME:

                        while (line.getPuntero() > 0) {
                            System.out.print("\033[C");
                            line.setPuntero(line.getPuntero() - 1);
                        }
                        break;

                    case END:

                        while (line.getPuntero() < line.getSize()) {
                            System.out.print("\033[C");
                            line.setPuntero(line.getPuntero() + 1);
                        }
                        break;

                    case DEL:

                        line.removeChar();

                        String corte = line.toString().substring(line.getPuntero() + 1);
                        System.out.print(corte);
                        System.out.print(" ");
                        for (int i = 0; i <= corte.length(); i++) {
                            System.out.print("\033[D");
                        }

                        break;

                    case INS:
                        line.changeMode();
                        line.toString();
                        break;

                    case BACKSPACE:
                        System.out.print("\033[D");
                        System.out.print(" ");
                        System.out.print("\033[D");

                        line.removeChar();
                        break;

                    case USELESS:
                        break;

                    default:
                        if(line.getMode()){
                            
                        }else{
                            
                        }

                }

            }

            unsetRaw();
            return line.toString();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
