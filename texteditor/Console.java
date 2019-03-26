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
    private static final String HOME = "\033[0;0f";
    private static final String END = "\033[0;%df";
    private static final String INSERT_MODE = "\033[4h";
    private static final String OVERWRITE_MODE = "\033[4l";
    private static final String DEL = "\033[P";
    private static final String COPY_POS = "\033[s";
    private static final String PASTE_POS = "\033[1u";
    
    private boolean insertMode;
    public Console(){
        insertMode = false;
        System.out.print(OVERWRITE_MODE);
    }
    public void addChar(char caract){
        System.out.print((char)caract);
    }
    public void changeMode(){
        insertMode = !insertMode;
        if(insertMode){
            System.out.print(INSERT_MODE);
        }else{
            System.out.print(OVERWRITE_MODE);
        }
    }
    public void home(int puntero){
        for(int i = 0; i <= puntero; i++){
            System.out.print("\033[C");
        }
    }
    public void end(int chars){
        System.out.print(String.format(END,chars));
    }
    public void del(int puntero, String str){
        System.out.print(DEL);
    }
    public void backspaceinMiddle(int puntero, int size, String str){
        System.out.print("\033[D");
        int num = 0;
        String corte = str.substring(puntero);
        if(puntero < size){
           System.out.print(corte);
           System.out.print(" ");
           for(int i=0; i<= corte.length();i++){
                System.out.print("\033[D");
           }
        }
    }
    public void backspace(){
        System.out.print("\033[D");
        System.out.print(" ");
        System.out.print("\033[D");
    }
    public void left(){
        System.out.print("\033[D");
    }
    public void right(){
        System.out.print("\033[C");
    }
}
