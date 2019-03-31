/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package texteditor;
import java.io.*;
import java.util.*;

/**
 *
 * @author mvc1009
 */

public class Console implements Observer{

    private static final String RIGHT = "\033[C";
    private static final String LEFT = "\033[D";
    private static final String HOME = "\033[%sD";
    private static final String END = "\033[%sC";
    private static final String INSERT_MODE = "\033[4h";
    private static final String OVERWRITE_MODE = "\033[4l";
    private static final String DEL = "\033[P";
    private static final String COPY_POS = "\033[s";
    private static final String PASTE_POS = "\033[u";
    private static final String WHERE_IS_MY_CURSOR = "\033[6n";
    private static final String CLEAR_TO_FINAL = "\033[K";
    private Line line;

    public static enum Opcode{ cADD, cHOME,cEND,cDEL,cBACKSPACE,cRIGHT,cLEFT}

    public static class Command{
        Opcode code;
        String corteString;
        int caract;
        public Command (Opcode code){
            this.code = code;
            this.corteString = "";
            this.caract = 0;
        }
        public Command (Opcode code, int caract){
            this.code = code;
            this.caract = caract;
            this.corteString = "";
        }
        public Command (Opcode code, String corteString){
            this.code = code;
            this.corteString = corteString;
            this.caract = 0;
        }
        public Command (Opcode code, int puntero, String str){
            this.code = code;
            this.corteString = str;
            this.caract = puntero;
        }
    }
    public Console(Line line){
        this.line = line;
        if(this.line.getMode()){
            System.out.print(INSERT_MODE);
        }else{
            System.out.print(OVERWRITE_MODE);
        }
    }
    public void update(Observable o, Object arg){
        Command comando = (Command) arg;
        switch(comando.code){
            case cADD:
                System.out.print((char)comando.caract);
                break;
            case cHOME:
                System.out.format(HOME, comando.caract);
                break;
            case cEND:
                System.out.format(END, comando.corteString.length()-comando.caract);
                break;
            case cDEL:
                System.out.print(DEL);
                break;
            case cBACKSPACE:
                System.out.print(LEFT);
                System.out.print(COPY_POS);
                System.out.print(CLEAR_TO_FINAL);
                System.out.print(comando.corteString);
                System.out.print(PASTE_POS);
                break;
            case cRIGHT:
                System.out.print(RIGHT);
                break;
            case cLEFT:
                System.out.print(LEFT);
                break;
        }
    }
}
