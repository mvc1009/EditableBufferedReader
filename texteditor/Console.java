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

    public Console(){
    }

    public void addCharOverWrite(int caract){
        System.out.print((char)caract);
    }
    public void addCharInsert(int puntero, int size, int caract, String str){
        int pos= 0;
        if(puntero < size){
            String corte = str.substring(puntero);
            System.out.print((char)caract + corte);
            pos = corte.length();
        }else{
            System.out.print((char)caract);
        }
        for(int i =0 ; i< pos; i++){
            System.out.print("\033[D");
        }
    }
    public void home(int puntero){
        for(int i = 0; i <= puntero; i++){
            System.out.print("\033[C");
        }
    }
    public void end(int caractersRestants){
        for(int i = 0; i<= caractersRestants;i++){
            System.out.print("\033[C");
        }
    }
    public void del(int puntero, String str){
        String corte = str.substring(puntero +1);
        System.out.print(corte);
        System.out.print(" ");
        for (int i = 0; i <= corte.length(); i++) {
            System.out.print("\033[D");
        }
    }
    public void backspaceinMiddle(int puntero, int size, String str){
        System.out.print("\033[D");
        int num = 0;
        String corte = str.substring(puntero);
        if(puntero < size){
           System.out.print(str);
           System.out.print(" ");
           for(int i=0; i<= str.length();i++){
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
