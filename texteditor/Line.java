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
 * @author mario
 */
 
public class Line extends Observable {

    private ArrayList<Character> buffer;
    private int puntero;
    private boolean insertMode;

    public Line() {
        buffer = new ArrayList<Character>();
        puntero = 0;
        insertMode = false;
    }

    public void addChar(int puntero, int caract) {
        setChanged();
        buffer.add(puntero, (char) caract);
        this.puntero = puntero +1;
        notifyObservers(new Console.Command(Console.Opcode.cADD, caract));
    }

    public void removeChar() {
        setChanged();
        if(buffer.size() > 0){
        buffer.remove(puntero-1);
        puntero = puntero -1;
        notifyObservers(new Console.Command(Console.Opcode.cBACKSPACE, this.toString().substring(puntero)));
        }
    }
    public void delChar(){
        setChanged();
        if(puntero < buffer.size()){
            buffer.remove(puntero);
            notifyObservers(new Console.Command(Console.Opcode.cDEL));
        }
    }
    public int getPuntero() {
        return puntero;
    }

    public void setPuntero(int position) {
        setChanged();
        if(position == 0){
            notifyObservers(new Console.Command(Console.Opcode.cHOME, puntero,this.toString()));
        }else if (position == buffer.size()){
            notifyObservers(new Console.Command(Console.Opcode.cEND, puntero, this.toString()));
        }else if (position == puntero+1){
            notifyObservers(new Console.Command(Console.Opcode.cRIGHT));
        }else if (position == puntero-1){
            notifyObservers(new Console.Command(Console.Opcode.cLEFT));
        }
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
        setChanged();
        insertMode = !insertMode;
        notifyObservers(new Console.Command(Console.Opcode.cCHANGE));
    }

    public boolean getMode() {
        return insertMode;
    }

    public int getSize() {
        return buffer.size();
    }
}
