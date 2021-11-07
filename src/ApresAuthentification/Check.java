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
public class Check extends Thread{
    
    String[] Friends;
    Socket Client;

    public Check(String Message, Socket Client) {
        this.Friends = Message.split(",");
        this.Client = Client;
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
        String Status ="";
        DataBase DB = new DataBase();
        for(int i=0 ; i < Friends.length ; i++){
            ResultSet status =  DB.selection("Select Status from user where Username = '"+Friends[i]+"'");
            try{
                while(status.next()){
                    Status += status.getString(1)+";";
                }
                Status += ";FriendsOfUser";
            }catch(Exception e){}
            
        }
        
    }
    
    
    
    
}
