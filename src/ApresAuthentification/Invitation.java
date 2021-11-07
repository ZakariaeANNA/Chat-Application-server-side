package ApresAuthentification;





import Main.DataBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zakar
 */
public class Invitation extends Thread{
    
    String Username;
    Socket Client;

    public Invitation(String Username, Socket Client) {
        this.Username = Username;
        this.Client = Client;
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
        ResultSet rs = DB.selection("SELECT DISTINCT U2.username from user as U1,User as U2 "
                                                + "where U1.username='"+this.Username+"' and U2.id IN(" 
                                                +"SELECT em_id FROM invitation where rece_id = U1.id"
                                                +")");
        String Invit = "";
        try{
            while(rs.next()){
                Invit += rs.getString(1)+",";
            }
            Invit+=";Invit";
            out.println(Invit);
        }catch(Exception e){
            
        }
    }
    
    
    
    
}
