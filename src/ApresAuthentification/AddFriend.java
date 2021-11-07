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
public class AddFriend extends Thread{
    String Username;
    String Inviter;
    String Choice;
    Socket Client;
    
    public AddFriend(String Message, Socket client) {
        String[] str = Message.split(",");
        Username = str[0];
        Inviter = str[1];
        Choice = str[2];
        this.Client = client;
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
        if(Choice.equals("Accept")){
            try{
                ResultSet rs = DB.selection("Select U1.id,U2.id from user as U1,user as U2 where U1.username ='"+this.Username+"' and U2.username='"+this.Inviter+"' ");
                while(rs.next()){
                    String rs1 = rs.getString(1);
                    String rs2 = rs.getString(2);
                    DB.mise_ajour("INSERT INTO amis VALUES('"+rs1+"','"+rs2+"')");
                    DB.mise_ajour("Delete from invitation where rece_id='"+rs1+"'and em_id='"+rs2+"'");
                }
                out.println(this.Inviter+" est ajouté dans la liste des amis;AddFriend");
            }catch(Exception e){
                
            }
        }else if(Choice.equals("Decline")){
            try{
                ResultSet rs = DB.selection("Select U1.id,U2.id from user as U1,user as U2 where U1.username ='"+this.Username+"' and U2.username='"+this.Inviter+"' ");
                while(rs.next()){
                    String rs1 = rs.getString(1);
                    String rs2 = rs.getString(2);
                    DB.mise_ajour("Delete from invitation where rece_id='"+rs1+"'and em_id='"+rs2+"' ");
                }
                out.println("L'invation de "+this.Inviter+" est rejeté;AddFriend");
            }catch(Exception e){
                
            }
        }
    }
    
    
    
}
