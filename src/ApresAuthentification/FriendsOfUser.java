package ApresAuthentification;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Main.DataBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
/**
 *
 * @author zakar
 */
public class FriendsOfUser extends Thread{
    String Username;
    Socket Client;
    public FriendsOfUser(String Username,Socket Client) {
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
        String Friends = new String();
        DataBase DB = new DataBase();
        ResultSet rs = DB.selection("SELECT DISTINCT U2.username,U2.status from user as U1,User as U2 "
                                                + "where U1.username='"+this.Username+"' and U2.id IN(" 
                                                +"(SELECT id_f2 FROM amis where U1.id = id_f1) "
                                                + "UNION " 
                                                +"(SELECT id_f1 FROM amis where U1.id = id_f2)" 
                                                +")");
        try{
            while(rs.next()){
                Friends += rs.getString(1)+"/"+rs.getString(2)+",";
            }
            Friends += ";FriendsOfUser";
            out.println(Friends);
        }catch(Exception e){
            
        }
    }
    
    
    
}
