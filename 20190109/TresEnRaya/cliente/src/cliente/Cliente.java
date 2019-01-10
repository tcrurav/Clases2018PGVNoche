/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tibur
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            final String HOST = "localhost";
            final int PORT = 40080;
            
            Socket socket = new Socket(HOST, PORT);
             
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            Scanner sc = new Scanner(System.in);
            String[][] tablero = new String[3][3];
            inicializarTablero(tablero);
            
            while(true){
                pintarTablero();
                String line = sc.nextLine();
                
                bw.write(line);
                bw.newLine();
                bw.flush();
                
                
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static void pintarTablero() {
        for (int i = 0; i < 3; i++) {
            
        }
    }
    
    private static void inicializarTablero(String[][] tablero) {
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = "";
            }
        }
    }
    
}
