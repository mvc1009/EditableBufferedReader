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

    private static final char ESC = '\033';

    private static final int LEFT = 200;
    private static final int RIGHT = 201;
    private static final int HOME = 203;
    private static final int END = 204;
    private static final int DEL = 205;
    private static final int INS = 206;

    private static final int USELESS = 202;
    private static final int BACKSPACE = 127;

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
        Line line = null;
        Console console = null;
        try {
            setRaw();
            line = new Line();
            console = new Console(line);
            line.addObserver(console);
            int key = this.read();
            while (key != '\r') {

                switch (key) {

                    case LEFT:

                        if (line.getPuntero() > 0) {
                            line.setPuntero(line.getPuntero() - 1);
                        }
                        break;

                    case RIGHT:

                        if (line.getPuntero() < line.getSize()) {
                            line.setPuntero(line.getPuntero() + 1);
                        }
                        break;

                    case HOME:
                        line.setPuntero(0);
                        break;

                    case END:
                        line.setPuntero(line.getSize());
                        break;

                    case DEL:
                        if(line.getPuntero()<line.getSize()){
                            line.delChar();
                        }
                        break;

                    case INS:
                        line.changeMode();
                        break;

                    case BACKSPACE:
                        line.removeChar();
                        break;

                    case USELESS:
                        break;

                    default:
                        if(!line.getMode()&&(line.getPuntero()<line.getSize())){
                            line.delChar();
                        }
                            line.addChar(line.getPuntero(),key);
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
