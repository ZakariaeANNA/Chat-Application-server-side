package Main;



import AvantAuthentification.Authentification;
import java.net.*;
import java.io.*;
import java.util.*;

public class Serveur{
 
    public static void main(String args[]) throws IOException{
        ServerSocket Server = new ServerSocket(4000);
        while(true){
            try{
                Socket Client = Server.accept();
                BufferedReader fluxLecture = new BufferedReader(new InputStreamReader(Client.getInputStream()));
                String Message = fluxLecture.readLine();
                new Authentification(Client,Message);
            }catch(IOException e){

            }
        }
    }
}