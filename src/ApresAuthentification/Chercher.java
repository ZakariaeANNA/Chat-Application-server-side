package ApresAuthentification;





import Main.DataBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zakar
 */
public class Chercher extends Thread{

    String Username;
    String Inviter;
    Socket Client;
    
    public Chercher(String Message, Socket client) {
        this.Client = client;
        String[] str = Message.split(",");
        this.Username = str[0];
        this.Inviter = str[1];
        super.start();
    }

    @Override
    public void run() { 
        PrintWriter out = null;
        try{
            out = new PrintWriter(Client.getOutputStream(), true);
        }catch (IOException e) {
            System.out.println("in or out failed");
            System.exit(-1);
        }
        
        DataBase DB = new DataBase();
        boolean Trouve = DB.Rechercher("Select username from user", Username);
       
        if(Trouve){
            try{
                ResultSet rs = DB.selection("Select U1.id,U2.id from user as U1,user as U2 where U1.username ='"+this.Username+"' and U2.username='"+this.Inviter+"' ");
                while(rs.next()){
                    String eme_id = rs.getString(1);
                    String rece_id = rs.getString(2);
                    DB.mise_ajour("INSERT INTO invitation VALUES('"+eme_id+"','"+rece_id+"','"+java.sql.Date.valueOf(LocalDate.now())+"')");
                    out.println("Invitation est envoyé;Chercher");
                }
            }catch(Exception e){

            }
        }else{
            out.println("Utilsateur non trouve;Chercher");
        }
    }
    
    
}
