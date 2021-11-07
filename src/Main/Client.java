package Main;




import ApresAuthentification.FriendsOfUser;
import ApresAuthentification.Chercher;
import ApresAuthentification.AddFriend;
import ApresAuthentification.Invitation;
import ApresAuthentification.Close;
import ApresAuthentification.MessageFromDatabase;
import ApresAuthentification.SendReceiveMsg;
import java.net.*;
import java.util.*;
import java.io.*;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Client extends Thread {
    private Socket client;
    public String Username;

  //Constructor
    public Client(Socket client, String Username) {
      this.client = client;
      this.Username = Username;
      super.start();
    }

    @Override
    public void run(){
      String line;
      BufferedReader in = null;
      try{
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      } catch (IOException e) {
        System.out.println("in or out failed");
        System.exit(-1);
      }

      while(true){
        boolean trouve = false;
        try{
            line = in.readLine();
            String[] str = line.split(";");
            
            if(str[1].equals("Message")){
               new SendReceiveMsg(Username,str[0],client);
            }else if(str[1].equals("close")){
               new Close(Username,client);
               break;
            }else if(str[1].equals("Friends")){
               new FriendsOfUser(str[0],client);
            }else if(str[1].equals("Message_DB")){
               new MessageFromDatabase(str[0],client);
            }else if(str[1].equals("Invitation")){
               new Invitation(str[0],client);
            }else if(str[1].equals("Chercher")){
               new Chercher(str[0],client);
            }else if(str[1].equals("AddFriend")){
               new AddFriend(str[0],client);
            }
        }catch (IOException e) {
          System.exit(-1);
        }
    }
  }
}