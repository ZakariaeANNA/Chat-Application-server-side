package ApresAuthentification;




import Main.DataBase;
import Main.User_Table;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zakar
 */
public class SendReceiveMsg extends Thread{
    
    String Username;
    String Message;
    Socket Client;

    public SendReceiveMsg(String Username,String Message, Socket Client) {
        this.Username = Username;
        this.Message = Message;
        this.Client = Client;
        super.start();
    }

    @Override
    public void run() {
        boolean trouve = false;
        DataBase Connection = new DataBase();
        HashMap<String,Socket> User = User_Table.GetHashMap();
        String[] M = Message.split("-");
        Iterator iterator = User.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapentry = (Map.Entry) iterator.next();
            if(mapentry.getKey().equals(M[1])){
                trouve = true;
                Socket send = (Socket) mapentry.getValue();
                PrintWriter out_msg = null;
                try {
                    out_msg = new PrintWriter(send.getOutputStream(), true);
                } catch (IOException ex) {
                }
                out_msg.println(M[0]+","+M[1]+","+M[2]+","+M[3]+";Message");
                break;
            }
        }
        if(trouve == false){
            Connection.mise_ajour("INSERT INTO message VALUES('"+this.Username+"','"+M[1]+"','"+M[2]+"','"+M[3]+"')");
        }
    }
 
    
}
