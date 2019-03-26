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
 * @author mvc1009
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
    *   Read es una funció que llegeix caracter a caracter, on s'ha de tenir en compte
    *   els simbols següents:
    *   F. Dreta:       ESC [ C
    *   F. Esquerra:    ESC [ D
    *   Inici Linea:    ESC [ H o ESC O H, ESC [ 1 -
    *   Final Linea:    ESC [ F o ESC O F, ESC [ 4 -
    *   Insertar:       ESC [ 2 -
    *   DEL:            ESC [ 3 -
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
                case 'A':
                case 'B':
                    return USELESS;
                case '2':
                    if((car = super.read()) == '~') return INS;
                    else   return '2';
                case '3':
                    if((car = super.read()) == '~') return DEL;
                    else    return '3';

                default:
                    return car;
            }
        }
        return car;
    }

    public String readLine() throws IOException {
        try {
            setRaw();
            Line line = new Line();
            Console console = new Console();
            int key = this.read();
            while (key != '\r') {

                switch (key) {

                    case LEFT:

                        if (line.getPuntero() > 0) {
                            console.left();
                            line.setPuntero(line.getPuntero() - 1);
                        }
                        break;

                    case RIGHT:

                        if (line.getPuntero() < line.getSize()) {
                            console.right();
                            line.setPuntero(line.getPuntero() + 1);
                        }
                        break;

                    case HOME:
                        console.home(line.getPuntero());
                        line.setPuntero(0);
                        break;

                    case END:
                        console.end(line.getSize()-line.getPuntero());
                        line.setPuntero(line.getSize());
                        break;

                    case DEL:
                        if(line.getPuntero()<line.getSize()){
                            console.del(line.getPuntero(), line.toString());
                            line.delChar();
                        }
                        break;

                    case INS:
                        line.changeMode();
                        console.changeMode();
                        break;

                    case BACKSPACE:
                        if(line.getPuntero()< line.getSize()){
                            console.backspaceinMiddle(line.getPuntero(),line.getSize(),line.toString());
                        }else{
                            console.backspace();
                        }
                        line.removeChar();
                        break;

                    case USELESS:
                        break;

                    default:

                        if(!line.getMode()){                         //OverwriteMode
                            if(line.getPuntero() < line.getSize()){
                                line.delChar();
                                line.setPuntero(line.getPuntero()+1);
                            }
                            line.addChar(line.getPuntero(),key);
                            line.setPuntero(line.getPuntero()+1);
                         }else{                                      //InsertMode
                            line.addChar(line.getPuntero(),key);
                            line.setPuntero(line.getPuntero()+1);
                        }
                        console.addChar((char)key);

                }
            key = this.read();
            }

            unsetRaw();
            return line.toString();
        } catch (InterruptedException ex) {
            Logger.getLogger(EditableBufferedReader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
