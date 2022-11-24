package systemyklienckie;

/*
     @author:   Mateusz Langaj 
                Jakub Kapituła
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoServerThread implements Runnable
        
{


    
  protected Socket socket;
  public EchoServerThread(Socket clientSocket)
        
  {
    this.socket = clientSocket;
  }
  
  public void run()
  {
    //Deklaracje zmiennych
    BufferedReader brinp = null;
    DataOutputStream out = null;
    String threadName = Thread.currentThread().getName();
    boolean log=false;
            boolean po=false;
            boolean zalog = false;
            double saldo=0.0;
            ArrayList<Dane> lista = new ArrayList<>();
            boolean TK = false;
    //inicjalizacja strumieni
    try{
      brinp = new BufferedReader(
        new InputStreamReader(
          socket.getInputStream()
        )
      );
      out = new DataOutputStream(socket.getOutputStream());
    }
    catch(IOException e){
      System.out.println(threadName + "| Błąd przy tworzeniu strumieni " + e);
      return;
    }
    String line = null;
    
    int ktoralinia=0;
            int ktoraliniaa=-1;
            String TKS;
        try {
            TKS = brinp.readLine();
       
            if("true".equals(TKS))
                TK=true;
            else
                TK=false;
            lista = new ArrayList<>();
                File myObj = new File("C:\\Users\\vdi-terminal\\Downloads\\sYSTEMYkLIENCKIE\\src\\systemyklienckie\\dane_Klientow.txt");
                Scanner myReader;
        try {
            myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                    Dane tmp = new Dane();
                    tmp.imie = myReader.next();
                    tmp.nazwisko = myReader.next();            
                    tmp.pesel=myReader.nextLong();
                    tmp.konto=myReader.nextInt();
                    tmp.kwota = Double.parseDouble(myReader.next());
                        lista.add(tmp);
                }
        
    
    //pętla główna
    while(true){
      try{
         
                    line = brinp.readLine();
                    System.out.println("Odczytano linię: " + line);
                    if(TK){
                        if (line == null && log==false && zalog==false || "quit".equals(line) && log==false && zalog==false) {
                            socket.close();
                            System.out.println("Zakończenie pracy z klientem...");
                            break;
                        }
                        else if ("tak".equals(line) && log==false && zalog==false){
                            log=true;
                            line = "Wpisz login: ";
                        }
                        else{
                            if(zalog){
                                 lista.clear();
                                    Scanner myReader3 = new Scanner(myObj);
                                    while (myReader3.hasNext()) {
                                        Dane tmp = new Dane();
                                        tmp.imie = myReader3.next();
                                        tmp.nazwisko = myReader3.next();            
                                        tmp.pesel=myReader3.nextLong();
                                        tmp.konto=myReader3.nextInt();
                                        tmp.kwota = Double.parseDouble(myReader3.next());
                                        lista.add(tmp);
                                    }
                                    saldo = lista.get(ktoralinia).kwota;
                                if ("saldo".equals(line)){
                                    line = "Na koncie zostalo Ci: "+ saldo+"zl";
                                }
                                if ("wyloguj".equals(line)){
                                    line = "Pomyslnie wylogowano";
                                    zalog=false;
                                    po=false;
                                }
                                if ("wplata".equals(line)) {
                                    line="Ile chcesz wplacic?";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String wplata = brinp.readLine();
                                    while(wplata.length()==0 && wplata!=null){
                                        wplata = brinp.readLine();
                                    }
                                    if(Double.parseDouble(wplata)>0){
                                        try {
                                            saldo+=Double.parseDouble(wplata);
                                            lista.get(ktoralinia).kwota=saldo;
                                            FileOutputStream strumien = new FileOutputStream("C:\\Users\\vdi-terminal\\Downloads\\sYSTEMYkLIENCKIE\\src\\systemyklienckie\\dane_Klientow.txt", false);
                                            PrintWriter zapis = new PrintWriter(strumien);
                                            for(int i=0;i<lista.size();i++){
                                                zapis.print(lista.get(i).imie+" ");
                                                zapis.print(lista.get(i).nazwisko+" ");
                                                zapis.print(lista.get(i).pesel+" ");
                                                zapis.print(lista.get(i).konto+" ");
                                                zapis.println(lista.get(i).kwota+" ");
                                            }zapis.close();
                                            line = "Na Twoje konto zostalo przelane "+ wplata+"zl, po operacji Twoje saldo wynosi: "+saldo+"zl";
                                        } catch (FileNotFoundException | NumberFormatException e) {
                                        line = "Blad wplaty: nie wprowadzono poprawnej kwoty.";
                                        }
                                    }
                                    else
                                        line = "Blad wplaty: Kwota wplaty powinna byc wieksza od zera";
                                }
                                if ("wyplata".equals(line)) {
                                    line="Ile chcesz wyplacic?";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String wyplata = brinp.readLine();
                                    while(wyplata.length() == 0 && wyplata != null){
                                        wyplata = brinp.readLine();
                                    }
                                    if(Double.parseDouble(wyplata)>0){
                                        try {
                                            if(Double.parseDouble(wyplata) <= saldo){
                                                saldo-=Double.parseDouble(wyplata);
                                                lista.get(ktoralinia).kwota=saldo;
                                                FileOutputStream strumien = new FileOutputStream("C:\\Users\\vdi-terminal\\Downloads\\sYSTEMYkLIENCKIE\\src\\systemyklienckie\\dane_Klientow.txt", false);
                                                PrintWriter zapis = new PrintWriter(strumien);
                                                for(int i=0; i<lista.size(); i++){
                                                    zapis.print(lista.get(i).imie+" ");
                                                    zapis.print(lista.get(i).nazwisko+" ");
                                                    zapis.print(lista.get(i).pesel+" ");
                                                    zapis.print(lista.get(i).konto+" ");
                                                    zapis.println(lista.get(i).kwota+" ");
                                                }zapis.close();
                                                line = "Z Twojego konta zostalo wyplacone "+ wyplata+"zl, po operacji Twoje saldo wynosi: "+saldo+"zl";
                                            }
                                            else {
                                                line = "Nie masz wystarczajacych srodkow na koncie, by wyplacic "+ wyplata+"zl. Twoje saldo wynosi: "+saldo+"zl";
                                            }
                                        } catch (FileNotFoundException | NumberFormatException e) {
                                            line = "Blad wyplaty: nie wprowadzono poprawnej kwoty.";
                                        }
                                    }
                                    else
                                        line = "Blad wyplaty: Kwota wyplaty powinna być wieksza od zera";
                                }
                                if ("przelew".equals(line)) {
                                    line="Komu chcesz wyslac przelew?";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String przelew = brinp.readLine();
                                    while(przelew.length()==0 && przelew!=null){
                                        przelew = brinp.readLine();
                                    }
                                    try{
                                        for(int i=0;i<lista.size();i++){
                                            if (lista.get(i).konto==Integer.parseInt(przelew)){
                                                if(i==ktoralinia){
                                                    line="Nie mozesz sam sobie przelac pieniedzy!";
                                                    break;
                                                }
                                                else{
                                                    ktoraliniaa=i;
                                                line="Ile chcesz przelac pieniedzy?"; 
                                                    break;
                                                }
                                            }
                                            if(i==lista.size()-1 && lista.get(i).konto!=Integer.parseInt(przelew)){
                                                line="Nie ma takiego konta!";
                                            }
                                        }
                                        
                                        if(ktoraliniaa>-1){
                                            out.writeBytes(line + "\n\r");
                                            out.flush();
                                            System.out.println("Wysłano linię: " + line);
                                            String przeleww = brinp.readLine();
                                            while(przeleww.length()==0 && przeleww!=null){
                                                przeleww = brinp.readLine();
                                            }
                                            if(Double.parseDouble(przeleww)>0){
                                                if(Double.parseDouble(przeleww)<=saldo){
                                                    saldo-=Double.parseDouble(przeleww);
                                                    lista.get(ktoralinia).kwota=saldo;
                                                    lista.get(ktoraliniaa).kwota+=Double.parseDouble(przeleww);
                                                    FileOutputStream strumien = new FileOutputStream("C:\\Users\\vdi-terminal\\Downloads\\sYSTEMYkLIENCKIE\\src\\systemyklienckie\\dane_Klientow.txt", false);
                                                    PrintWriter zapis = new PrintWriter(strumien);
                                                    for(int i=0;i<lista.size();i++){
                                                        zapis.print(lista.get(i).imie+" ");
                                                        zapis.print(lista.get(i).nazwisko+" ");
                                                        zapis.print(lista.get(i).pesel+" ");
                                                        zapis.print(lista.get(i).konto+" ");
                                                        zapis.println(lista.get(i).kwota+" ");
                                                    }zapis.close();
                                                    line = "Z Twojego konta zostalo przelane "+ przeleww+"zl na konto uzytkownika "+lista.get(ktoraliniaa).imie+", po operacji Twoje saldo wynosi: "+saldo+"zl";
                                                }
                                                else {
                                                    line = "Nie masz wystarczajacych srodkow na koncie, by przelac "+ przeleww+"zl, Twoje saldo wynosi: "+saldo+"zl";
                                                }
                                                ktoraliniaa=-1;
                                            }
                                            else
                                                line = "Blad przelewu: Kwota przelewu powinna byc wieksza od zera";
                                            }
                                        } catch (FileNotFoundException | NumberFormatException e) {
                                        line = "Blad przelewu: nie wprowadzono poprawnych danych.";
                                    }
                                }
                            }
                            if(log){
                                Scanner myReader1 = new Scanner(myObj);
                                ktoralinia=0;
                                String linee="Nie ma takiego konta!";
                                while (myReader1.hasNextLine()){
                                    String Data = myReader1.nextLine();
                                    String[] result = Data.split(" ");
                                    if(result[3].equals(line)){
                                        saldo = Double.parseDouble(result[4]);
                                        line = "Witaj: "+ result[0]+"! Co chcesz zrobic? [saldo/wplata/wyplata/przelew/wyloguj]";
                                        po=true;
                                        linee="nope";
                                        break;
                                    }
                                    else ktoralinia++;
                                }
                                if("Nie ma takiego konta!".equals(linee)){
                                    log=false;
                                    line=linee;
                                }
                            }
                        }
                    }
                    else{
                        if ("edytuj".equals(line)){
                            line = "Wpisz nr konta osoby, ktorej chcesz edytowac dane: ";
                            out.writeBytes(line + "\n\r");
                            out.flush();
                            System.out.println("Wysłano linię: " + line);
                            try{
                                String edit = brinp.readLine();
                                while(edit.length()==0 && edit!=null){
                                    edit = brinp.readLine();
                                }
                                Scanner myReader2 = new Scanner(myObj);
                                int edytlinia=0;
                                String lineee="Nie ma takiego konta!";
                                while (myReader2.hasNextLine()){
                                    String Dataedyt = myReader2.nextLine();
                                    String[] resultedyt = Dataedyt.split(" ");
                                    if(resultedyt[3].equals(edit)){
                                        line = "Podaj nowe imie";
                                        lineee="nope";
                                        break;
                                    }
                                    else edytlinia++;
                                }
                                if("Nie ma takiego konta!".equals(lineee)){
                                    line=lineee;
                                }
                                else{
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String editt = brinp.readLine();
                                    while(editt.length()==0 && editt!=null){
                                        editt = brinp.readLine();
                                    }
                                    lista.get(edytlinia).imie=editt.substring(0, 1).toUpperCase()+editt.substring(1).toLowerCase();
                                    line = "Podaj nowe nazwisko";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String edittt = brinp.readLine();
                                    while(edittt.length()==0 && edittt!=null){
                                        edittt = brinp.readLine();
                                    }
                                    lista.get(edytlinia).nazwisko=edittt.substring(0, 1).toUpperCase()+edittt.substring(1).toLowerCase();
                                    line = "Podaj nowy pesel";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String editttt = brinp.readLine();
                                    while(editttt.length()==0 && editttt!=null){
                                        editttt = brinp.readLine();
                                    }
                                    lista.get(edytlinia).pesel=Long.parseLong(editttt);
                                    FileOutputStream strumien = new FileOutputStream("C:\\Users\\vdi-terminal\\Downloads\\sYSTEMYkLIENCKIE\\src\\systemyklienckie\\dane_Klientow.txt", false);
                                    PrintWriter zapis = new PrintWriter(strumien);
                                    for(int i=0;i<lista.size();i++){
                                        zapis.print(lista.get(i).imie+" ");
                                        zapis.print(lista.get(i).nazwisko+" ");
                                        zapis.print(lista.get(i).pesel+" ");
                                        zapis.print(lista.get(i).konto+" ");
                                        zapis.println(lista.get(i).kwota+" ");
                                    }zapis.close();
                                    line = "Dane uzytkownika "+ lista.get(edytlinia).konto+" zostaly zmienione :)";
                                    editt="";
                                    edittt="";
                                    editttt="";
                                }
                                edit="";
                            }
                            catch(FileNotFoundException | NumberFormatException e){
                                line = "Blad edycji: nie wprowadzono poprawnych danych.";
                            }
                        }
                        if ("dodaj".equals(line)){
                            try{
                                Random rand = new Random();
                                int nr_konta = rand.nextInt(88889) + 11111;
                                if(lista.size()==88889){
                                    line = "Pula numerow kont się wyczerpala, w systemie jest juz 88889 kont, więc nie mozna tworzyc kolejnych!";
                                }
                                else{
                                    for(int i=0;i<lista.size();i++){
                                        if(nr_konta==lista.get(i).konto){
                                            nr_konta = rand.nextInt(88889) + 11111;
                                            i=-1;
                                        }
                                    }
                                    systemyklienckie.Dane tmp = new systemyklienckie.Dane();
                                    tmp.konto=nr_konta;
                                    line = "Podaj imie";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String dodaj = brinp.readLine();
                                    while(dodaj.length()==0 && dodaj!=null){
                                        dodaj = brinp.readLine();
                                    }
                                    tmp.imie=dodaj.substring(0, 1).toUpperCase()+dodaj.substring(1).toLowerCase();
                                    line = "Podaj nazwisko";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String dodajj = brinp.readLine();
                                    while(dodajj.length()==0 && dodajj!=null){
                                        dodajj = brinp.readLine();
                                    }
                                    tmp.nazwisko=dodajj.substring(0, 1).toUpperCase()+dodajj.substring(1).toLowerCase();
                                    line = "Podaj pesel";
                                    out.writeBytes(line + "\n\r");
                                    out.flush();
                                    System.out.println("Wysłano linię: " + line);
                                    String dodajjj = brinp.readLine();
                                    while(dodajjj.length()==0 && dodajjj!=null){
                                        dodajjj = brinp.readLine();
                                    }
                                    tmp.pesel=Long.parseLong(dodajjj);
                                    tmp.kwota=0.0;
                                    lista.add(tmp);
                                    FileOutputStream strumien = new FileOutputStream("C:\\Users\\vdi-terminal\\Downloads\\sYSTEMYkLIENCKIE\\src\\systemyklienckie\\dane_Klientow.txt", false);
                                    PrintWriter zapis = new PrintWriter(strumien);
                                    for(int i=0;i<lista.size();i++){
                                        zapis.print(lista.get(i).imie+" ");
                                        zapis.print(lista.get(i).nazwisko+" ");
                                        zapis.print(lista.get(i).pesel+" ");
                                        zapis.print(lista.get(i).konto+" ");
                                        zapis.println(lista.get(i).kwota+" ");
                                    }zapis.close();
                                    line = "Dodano nowego uzytkownika "+ lista.get(lista.size()-1).konto;
                                    dodaj="";
                                    dodajj="";
                                    dodajjj="";
                                }
                            }
                            catch(FileNotFoundException | NumberFormatException e){
                                line = "Blad dodawania: nie wprowadzono poprawnych danych.";
                            }
                        }
                    }
                    if(po){
                         log=false;
                         zalog=true;
                    }
                    out.writeBytes(line + "\n\r");
                    out.flush();
                    System.out.println("Wysłano linię: " + line);
                }
      catch(IOException e){
        System.out.println(threadName + "| Błąd wejścia-wyjścia." + e);
        return;
      }
      
    }
    } catch (FileNotFoundException ex) {
            Logger.getLogger(EchoServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
                
     } catch (IOException ex) {
            Logger.getLogger(EchoServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
}

