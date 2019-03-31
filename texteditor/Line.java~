/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class Line {

    private ArrayList<Character> buffer;
    private int puntero;
    private boolean insertMode;

    public Line() {
        buffer = new ArrayList<Character>();                       //pruebas
        puntero = 0;
        insertMode = true;
    }

    public void addChar(int puntero, int caract) {
        buffer.add(puntero, (char) caract);

    }

    public void removeChar() {
        if(buffer.size() > 0){
            buffer.remove(puntero-1);
            puntero --;
        }
    }
    public void delChar(){
        if(puntero < buffer.size()){
            buffer.remove(puntero);
            puntero--;
        }
    }
    public int getPuntero() {
        return puntero;
    }

    public void setPuntero(int position) {
        puntero = position;
    }

    @Override
    
    public String toString() {
        String str = "";
        for (int i = 0; i < buffer.size(); i++) {
            str = str + buffer.get(i);
        }

        return str;
    }

    public void changeMode() {
        insertMode = !insertMode;
    }

    public boolean getMode() {
        return insertMode;
    }

    public int getSize() {
        return buffer.size();
    }
}
