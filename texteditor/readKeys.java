/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author mvc1009
 */
public class readKeys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO code application logic here
        setRaw();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int key = in.read();
        while(key != 13){
            System.out.print(key);
            System.out.println((char)key);
            key = in.read();
        }
        
        unsetRaw();
        
        
        
    }
    public static void setRaw() throws InterruptedException, IOException{
        String[] cmd = {"sh", "-c", "stty -echo raw</dev/tty"};
        Runtime.getRuntime().exec(cmd);
    }
    public static void unsetRaw() throws IOException, InterruptedException{
        String[] cmd = {"sh", "-c", "stty echo cooked</dev/tty"};
        Runtime.getRuntime().exec(cmd);
    }
    
}
