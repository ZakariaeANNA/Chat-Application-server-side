package ApresAuthentification;





import Main.DataBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zakar
 */
public class MessageFromDatabase extends Thread{
    String Username;
    Socket Client;
    
    public MessageFromDatabase(String Username,Socket Client) {
        this.Username = Username;
        this.Client = Client;
        super.start();
    }

    @Override
    public void run() {
        DataBase DB = new DataBase();
        String Message = new String();
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            out = new PrintWriter(Client.getOutputStream(), true);
        }catch (IOException e) {
            System.out.println("in or out failed");
            System.exit(-1);
        }
        ResultSet rs = DB.selection("Select eme,recep,text,date from message where "
                                                                            + "recep='"+Username+"' "
                                                                            + "ORDER BY date ");
        try{
            while(rs.next()){
                String recepteur = rs.getString(2);
                String emetteur = rs.getString(1);
                String text = rs.getString(3);
                String date = rs.getString(4);
                Message +=recepteur+","+emetteur+","+text+","+date+"Sep";
                DB.mise_ajour("Delete from message where  eme='"+emetteur+"' "
                                                              + "and recep='"+Username+"' and text='"+text+"' ");
            }
            Message += ";MessageDB";
            out.println(Message);
        }catch(Exception e){
            
        }
        
    }
    
    
}
