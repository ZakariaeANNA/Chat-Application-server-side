package AvantAuthentification;






import Main.User_Table;
import Main.Client;
import Main.DataBase;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zakar
 */
public class Authentification extends Thread{
    
    Socket Client;
    String Username;
    String Password;
    String Auth;

    public Authentification(Socket Client, String Message) {
        this.Client = Client;
        String[] str = Message.split(";");
        this.Auth = str[1];
        String[] data = str[0].split(",");
        this.Username = data[0];
        this.Password = data[1];
        super.start();
    }
    
    @Override
    public void run() {
        PrintStream fluxEcriture = null;
        try{
            fluxEcriture = new PrintStream(this.Client.getOutputStream());
        }catch(Exception e){
            
        }
        
        DataBase DB = new DataBase();
        if(this.Auth.equals("Login")){
            String sql ="Select Username,Password from User";
            boolean trouve = DB.Rechercher(sql, Username, Password);
            if(trouve == true){
                fluxEcriture.println(true);
                User_Table.ajouter(Username, Client);
                DB.mise_ajour("UPDATE User SET Status = 'Online' where Username = '"+Username+"'");
                new Client(Client,Username);
            }else{
                fluxEcriture.println("Information non valide");
            }
        }else if(this.Auth.equals("Register")){
            String sql ="Select Username from User";
            boolean recherche = DB.Rechercher(sql,Username);
            if(recherche == false){
                DB.mise_ajour("Insert Into User (Username,Password,Status) Values('"+Username+"','"+Password+"','Online');");
                fluxEcriture.println(true);
                User_Table.ajouter(Username, Client);
                new Client(Client,Username);
            }else{
                fluxEcriture.println("Username est déjà existe.");
            }
        }
    }
    
    
    
    
    
}
