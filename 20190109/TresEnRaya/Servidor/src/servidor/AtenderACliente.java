/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tibur
 */
public class AtenderACliente extends Thread{
    Socket socket;

    public AtenderACliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        atenderAlCliente(socket);
    }

    private void atenderAlCliente(Socket socket) {
        InputStream is = null;
        try {
            is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            String[][] tablero = new String[3][3];
            
            inicializarTablero(tablero);
            String lineFromClient;
            while(true){
                lineFromClient = br.readLine();  // 0,0
                int i = Integer.valueOf(lineFromClient.substring(0, 1));
                int j = Integer.valueOf(lineFromClient.substring(2, 1));
                
                tablero[i][j] = "X";
                boolean ganador = averiguarSiHayGanador(tablero, "X");
                if(ganador){
                    bw.write("ganaste");
                    bw.newLine();
                    bw.flush();
                    break;
                } else {
                    String tirada = servidorTira(tablero);
                    ganador = averiguarSiHayGanador(tablero, "O");
                    if(ganador){
                        bw.write("perdiste");
                        bw.newLine();
                        bw.flush();
                        break;
                    } else {
                        bw.write(tirada);
                        bw.newLine();
                        bw.flush();
                    }
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AtenderACliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(AtenderACliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void inicializarTablero(String[][] tablero) {
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = "";
            }
        }
    }

    private boolean averiguarSiHayGanador(String[][] tablero, String caracter) {
        int[][][] combinacionesGanadoras = {
            {{0, 0}, {1, 1}, {2, 2}}, 
            {{0, 2}, {1, 1}, {2, 0}},
            {{0, 0}, {0, 1}, {0, 2}},
            {{1, 0}, {1, 1}, {1, 2}},
            {{2, 0}, {2, 1}, {2, 2}},
            {{0, 0}, {1, 0}, {2, 0}},
            {{0, 1}, {1, 1}, {2, 1}},
            {{0, 2}, {1, 2}, {2, 2}},
        };
        
        int aciertos;
        
        for (int c = 0; c < 8; c++) {
            aciertos = 0;
            for (int i = 0; i < 3; i++) {
                if(tablero[combinacionesGanadoras[c][i][0]][combinacionesGanadoras[c][i][1]].equals(caracter)){
                    aciertos++;
                }
            }
            if(aciertos == 3) return true;
        }
        
        return false;
    }

    private String servidorTira(String[][] tablero) {
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                if(tablero[i][j].equals("")) tablero[i][j] = "O";
                return i + "," + j;
            }
        }
        return "empate";
    }
    
    
}
