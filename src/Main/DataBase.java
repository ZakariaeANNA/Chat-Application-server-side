package Main;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zakar
 */
    import java.sql.*;
import javax.swing.JOptionPane;


public class DataBase{
    
    private Connection cn;

    public DataBase(){
        cn=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/chat application","root","");

        } catch (Exception e) {
            System.err.println("Connection");
        }
    }

    public int mise_ajour(String sql){
	   int t=0;
		try {
			Statement st=cn.createStatement();
			t=st.executeUpdate(sql);
		  
		} catch (Exception e) {
			 System.err.println("mise_a_jour");
		}
	 
		   return t;
	   }
   
    public ResultSet selection(String sql){
	  ResultSet rs=null;
		try {
			Statement st=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
		} catch (Exception e) {
			 System.err.println("selection");
		}
	  return rs;
	 }
    


        public boolean Rechercher(String sql,String Username,String Password){
            try {
                ResultSet rs=selection(sql);
                
                while (rs.next()) {         
                    String User = rs.getString(1) , Pass = rs.getString(2) ;
                    if (Pass.equals(Password) && User.equals(Username)) {
                        
                        return true;
                    }
                }
            } catch (Exception e) {
                 System.err.println(e.getMessage());
            }
           return false;
        }
        
        public boolean Rechercher(String sql,String Username){
            try {
                ResultSet rs=selection(sql);
                while (rs.next()) {         
                    String User = rs.getString(1);
                    if (User.equals(Username)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                 System.err.println(e.getMessage());
            }
           return false;
        }
        
        public void close() throws SQLException{
            cn.close();
        }
}
