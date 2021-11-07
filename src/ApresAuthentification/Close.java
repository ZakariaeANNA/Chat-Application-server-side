package ApresAuthentification;





import Main.User_Table;
import Main.DataBase;
import java.io.IOException;
import java.net.Socket;
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
public class Close extends Thread{
    String Username;
    Socket User;

    public Close(String Username, Socket User) {
        this.Username = Username;
        this.User = User;
        super.start();
    }

    @Override
    public void run() {
        DataBase DB = new DataBase();
        HashMap<String,Socket> User = User_Table.GetHashMap();
        Iterator iterator = User.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapentry = (Map.Entry) iterator.next();
            if(mapentry.getKey().equals(this.Username)){
                iterator.remove();
                DB.mise_ajour("UPDATE User SET Status = 'Offline' where Username = '"+mapentry.getKey()+"'");
                try {
                    this.User.close();
                } catch (IOException ex) {
                }
                this.stop();
                break;
          }
       }
    }
    
    
}
