package systemyklienckie;

/*
     @author:   Mateusz Langaj 
                Jakub Kapituła
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class TB {
    
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
        String TK = "false";
        boolean pierwszy=true;
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
        out.writeBytes(TK + '\n');
        out.flush();
        //Pętla główna klienta
        while (true) {
            try {
                if(pierwszy==true)
                    System.out.println("Witaj w Terminalu Bankiera! Co chcesz zrobić? [edytuj/dodaj/wyloguj]");
                else
                    System.out.println("Co chcesz zrobić? [edytuj/dodaj/wyloguj]");
                line = brLocalInp.readLine().toLowerCase();
                if ("wyloguj".equals(line)) {
                    System.out.println("Kończenie pracy...");
                    clientSocket.close();
                    System.exit(0);
                }
                else if ("edytuj".equals(line)) {
                    out.writeBytes(line + '\n');
                    out.flush();                    //wysyła
                    String edit = brSockInp.readLine();
                    while (edit.length()==0 && edit!=null){
                        edit = brSockInp.readLine();
                    }
                    System.out.println(edit);
                    line = brLocalInp.readLine();
                    out.writeBytes(line + '\n');
                    out.flush();                    //wysyła
                    String imie = brSockInp.readLine();
                    while (imie.length()==0 && imie!=null){
                        imie = brSockInp.readLine();
                    }
                    System.out.println(imie);
                    if("Podaj nowe imie".equals(imie)){
                        line = brLocalInp.readLine();
                        out.writeBytes(line + '\n');
                        out.flush();                    //wysyła
                        String nazwisko = brSockInp.readLine();
                        while (nazwisko.length()==0 && nazwisko!=null){
                            nazwisko = brSockInp.readLine();
                        }
                        System.out.println(nazwisko);
                        if("Podaj nowe nazwisko".equals(nazwisko)){
                            line = brLocalInp.readLine();
                            out.writeBytes(line + '\n');
                            out.flush();                    //wysyła
                            String pesel = brSockInp.readLine();
                            while (pesel.length()==0 && pesel!=null){
                                pesel = brSockInp.readLine();
                            }
                            System.out.println(pesel);
                            if("Podaj nowy pesel".equals(pesel)){
                                line = brLocalInp.readLine();
                                out.writeBytes(line + '\n');
                                out.flush();                    //wysyła
                                String odp = brSockInp.readLine();
                                while (odp.length()==0 && odp!=null){
                                    odp = brSockInp.readLine();
                                }
                                System.out.println(odp);
                            }
                        }
                    }
                }
                else if("dodaj".equals(line)){
                    out.writeBytes(line + '\n');
                    out.flush();                    //wysyła
                    String dodaj = brSockInp.readLine();
                    while (dodaj.length()==0 && dodaj!=null){
                        dodaj = brSockInp.readLine();
                    }
                    System.out.println(dodaj);
                    if("Podaj imie".equals(dodaj)){
                        line = brLocalInp.readLine();
                        out.writeBytes(line + '\n');
                        out.flush();                    //wysyła
                        String dodajj = brSockInp.readLine();
                        while (dodajj.length()==0 && dodajj!=null){
                            dodajj = brSockInp.readLine();
                        }
                        System.out.println(dodajj);
                        if("Podaj nazwisko".equals(dodajj)){
                            line = brLocalInp.readLine();
                            out.writeBytes(line + '\n');
                            out.flush();                    //wysyła
                            String dodajjj = brSockInp.readLine();
                            while (dodajjj.length()==0 && dodajjj!=null){
                                dodajjj = brSockInp.readLine();
                            }
                            System.out.println(dodajjj);
                            if("Podaj pesel".equals(dodajjj)){
                                line = brLocalInp.readLine();
                                out.writeBytes(line + '\n');
                                out.flush();                    //wysyła
                                String odpp = brSockInp.readLine();
                                while (odpp.length()==0 && odpp!=null){
                                    odpp = brSockInp.readLine();
                                }
                                System.out.println(odpp);
                            }
                        }
                    }
                }
                else{
                    System.out.println("Wprowadź poprawną komendę!");
                }
                pierwszy=false;
            } catch (IOException e) {
                System.out.println("Błąd wejścia-wyjścia: " + e);
                System.exit(-1);
            }
        }
     }
}
