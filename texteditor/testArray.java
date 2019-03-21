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
public class testArray {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Character> array = new ArrayList<Character>();
        array.add('c');
        array.add(0, 'f');
        System.out.print(array.toString());
    }

}
