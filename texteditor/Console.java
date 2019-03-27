/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package texteditor;

/**
 *
 * @author mvc1009
 */
 
public class Console {
    
    private static final String RIGHT = "\033[C";
    private static final String LEFT = "\033[D";
    private static final String HOME = "\033[%d;0f";
    private static final String END = "\033[%d;%df";
    private static final String INSERT_MODE = "\033[4h";
    private static final String OVERWRITE_MODE = "\033[4l";
    private static final String DEL = "\033[P";
    private static final String COPY_POS = "\033[s";
    private static final String PASTE_POS = "\033[u";
    private static final String WHERE_IS_MY_CURSOR = "\033[6n";
    private static final String CLEAR_TO_FINAL = "\033[K";
    
    private int linea_inicial = 1;
    private boolean insertMode;
    
    public Console(){
        insertMode = false;
        System.out.print(OVERWRITE_MODE);
    }
    public void addChar(char caract){
        System.out.print(caract);
    }
    public void changeMode(){
        insertMode = !insertMode;
        if(insertMode){
            System.out.print(INSERT_MODE);
        }else{
            System.out.print(OVERWRITE_MODE);
        }
    }
    public void home(){
        System.out.print(HOME);
    }
    public void end(int chars){
        System.out.print(String.format(END,chars));
    }
    public void del(){
        System.out.print(DEL);
    }
    public void backspace(int puntero, int size, String str){
        System.out.print(LEFT);
        System.out.print(COPY_POS);
        System.out.print(CLEAR_TO_FINAL);
        System.out.print(str.substring(puntero));
        System.out.print(PASTE_POS);
    }
    public void left(){
        System.out.print(LEFT);
    }
    public void right(){
        System.out.print(RIGHT);
    }
}
