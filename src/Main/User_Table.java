package Main;



import java.util.*;
import java.net.*;

public class User_Table {
    public static HashMap<String, Socket> User = new HashMap<String, Socket>();
    public static void ajouter(String Username,Socket C){
        User.put(Username,C);
    }
    public static HashMap GetHashMap(){
        return User;
    }
}
