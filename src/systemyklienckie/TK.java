package systemyklienckie;

/*
     @author:   Mateusz Langaj 
                Jakub Kapituła
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class TK {
    
    static String sprawdzanie(String line2){
           if(line2=="")
               return "Pusta";
           return "Cholera wie";
    }

    public static void main(String args[]) throws IOException {
        String host = "localhost";
        int port = 0;
        try {
            port = new Integer("6666").intValue();
        } catch (NumberFormatException e) {
            System.out.println("Nieprawidłowy argument: port");
            System.exit(-1);
        }
        
        //Inicjalizacja gniazda klienckiego
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.out.println("Nieznany host.");
            System.exit(-1);
        } catch (ConnectException e) {
            System.out.println("Połączenie odrzucone.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Błąd wejścia-wyjścia: " + e);
            System.exit(-1);
        }
        System.out.println("Połączono z " + clientSocket);

        //Deklaracje zmiennych strumieniowych 
        String line = null;
        BufferedReader brSockInp = null;
        BufferedReader brLocalInp = null;
        DataOutputStream out = null;
        boolean logi=false;
        boolean zalogi=false;
        String TK = "true";
           
        //Utworzenie strumieni
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            brSockInp = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            brLocalInp = new BufferedReader(
                    new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Błąd przy tworzeniu strumieni: " + e);
            System.exit(-1);
        }
        
        //Pętla główna klienta
        out.writeBytes(TK + '\n');
        out.flush();
        while (true) {
            try {
                if (logi==false){
                    System.out.println("Czy chcesz się zalogować? [tak/nie]");
                    line = brLocalInp.readLine().toLowerCase();
                }
                if ("nie".equals(line)) {
                    System.out.println("Kończenie pracy...");
                    clientSocket.close();
                    System.exit(0);
                }
                else if ("tak".equals(line)) {
                    out.writeBytes(line + '\n');
                    out.flush();                    //wysyła
                    String log = brSockInp.readLine();
                    while (log.length()==0 && log!=null){
                        log = brSockInp.readLine();
                    }
                    System.out.println(log);       //otrzymano
                    logi=true;
                    String line1 = brLocalInp.readLine();
                    out.writeBytes(line1 + '\n');
                    out.flush();
                    String line2 = brSockInp.readLine();
                    while (line2.length()==0 && line2!=null){
                        line2 = brSockInp.readLine();
                    }
                    System.out.println(line2);
                    if(!"Nie ma takiego konta!".equals(line2)){
                        String line3 = brLocalInp.readLine().toLowerCase();
                        boolean kolejny=false;
                        while(line3 != null && !"quit".equals(line3)) {
                            if(kolejny){
                                System.out.println("Co chcesz zrobić? [saldo/wplata/wyplata/przelew/wyloguj]");
                                line3 = brLocalInp.readLine().toLowerCase();
                            }
                        
                            //Wyloguj
                            if ("wyloguj".equals(line3)) {
                                out.writeBytes(line3 + '\n');
                                out.flush();                    //wysyła
                                String wyloguj = brSockInp.readLine();
                                while (wyloguj.length()==0 && wyloguj!=null){
                                    wyloguj = brSockInp.readLine();
                                }
                                System.out.println(wyloguj);
                                logi=false;
                                break;
                            }
                        
                            //Saldo
                            if ("saldo".equals(line3)) {
                                out.writeBytes(line3 + '\n');
                                out.flush();                    //wysyła
                                String saldo = brSockInp.readLine();
                                while (saldo.length()==0 && saldo!=null){
                                    saldo = brSockInp.readLine();
                                }
                                System.out.println(saldo);
                                saldo="";
                            }
                        
                            //Wplata
                            if ("wplata".equals(line3)) {
                                out.writeBytes(line3 + '\n');
                                out.flush();                    //wysyła
                                String wplata = brSockInp.readLine();
                                while (wplata.length()==0 && wplata!=null){
                                    wplata = brSockInp.readLine();
                                }
                                System.out.println(wplata);
                                line3 = brLocalInp.readLine();
                                out.writeBytes(line3 + '\n');
                                out.flush();
                                String wplataa = brSockInp.readLine();
                                while (wplataa.length()==0 && wplataa!=null){
                                    wplataa = brSockInp.readLine();
                                }
                                System.out.println(wplataa);
                                wplata="";
                                wplataa="";
                            }
                        
                            //Wyplata
                            if ("wyplata".equals(line3)) {
                                out.writeBytes(line3 + '\n');
                                out.flush();                    //wysyła
                                String wyplata = brSockInp.readLine();
                                while (wyplata.length()==0 && wyplata!=null){
                                    wyplata = brSockInp.readLine();
                                }
                                System.out.println(wyplata);
                                line3 = brLocalInp.readLine();
                                out.writeBytes(line3 + '\n');
                                out.flush();
                                String wyplataa = brSockInp.readLine();
                                while (wyplataa.length()==0 && wyplataa!=null){
                                    wyplataa = brSockInp.readLine();
                                }
                                System.out.println(wyplataa);
                                wyplata="";
                                wyplataa="";
                            }
                   
                            //przelew
                            if ("przelew".equals(line3)) {
                                out.writeBytes(line3 + '\n');
                                out.flush();                    //wysyła
                                String przelew = brSockInp.readLine();
                                while (przelew.length()==0 && przelew!=null){
                                    przelew = brSockInp.readLine();
                                }
                                System.out.println(przelew);
                                line3 = brLocalInp.readLine();
                                out.writeBytes(line3 + '\n');
                                out.flush();
                                String przeleww = brSockInp.readLine();
                                while (przeleww.length()==0 && przeleww!=null){
                                    przeleww = brSockInp.readLine();
                                }
                                System.out.println(przeleww);
                                String przelewww = "";
                                if("Ile chcesz przelac pieniedzy?".equals(przeleww)){
                                    line3 = brLocalInp.readLine();
                                    out.writeBytes(line3 + '\n');
                                    out.flush();
                                    przelewww = brSockInp.readLine();
                                    while (przelewww.length()==0 && przelewww!=null){
                                        przelewww = brSockInp.readLine();
                                    }
                                    System.out.println(przelewww);
                                }
                                przelew="";
                                przeleww="";
                                przelewww="";
                            }
                            kolejny=true;
                        }
                    }
                    else
                        logi=false;
                }
                else {
                    System.out.println("Wprowadź poprawną odpowiedź!");
                }
            } catch (IOException e) {
                System.out.println("Błąd wejścia-wyjścia: " + e);
                System.exit(-1);
            }
        }
    }
}
