package mySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Driver class that reads unique NFC IDs from an external source.
 * This external source is an email ID which is on Google's secure gmail servers.
 * These IDs are used to identify users.
 * All data is contained in tables using the mySQL database
 * 
 * @author 
 * @version 
 *
 */


public class Driver 
{

	   static Scanner read = new Scanner(System.in);
	   static public String [] ID = new String[10]; 
	   static public String id_1, id_2, id_3;
	   private static String user_1, user_2, user_3;
	   private static String mood = "Happy";
	   
	   /**
	    * Reads NFC serial IDs from the application's email. 
	    * For simplicity, a maximum of 3 IDs are stored. 
	    * Each of these IDs are unique and can be used to identify a particular user.
	    * 
	    * @param host - the server hosting the email service, gmail
	    * @param storeType - server type, PoP3.
	    * @param user - the email id 
	    * @param password - password corresponding to the email
	    */
	   
	   public static void check(String host, String storeType, String user, String password) 
	   {   
		   try 
	      {
	      //create properties field
	      Properties properties = new Properties();

	      properties.put("mail.pop3.host", host);
	      properties.put("mail.pop3.port", "995");
	      properties.put("mail.pop3.starttls.enable", "true");
	      Session emailSession = Session.getDefaultInstance(properties);
	  
	      //create the POP3 store object and connect with the pop server
	      Store store = emailSession.getStore("pop3s");

	      store.connect(host, user, password);

	      //create the folder object and open it
	      Folder emailFolder = store.getFolder("INBOX");
	      emailFolder.open(Folder.READ_ONLY);

	      // retrieve the messages from the folder in an array and print it
	      Message[] messages = emailFolder.getMessages();
	      
	      System.out.println("Message in inbox: " + messages.length);
	     
	      for (int i = 0, n = messages.length; i < n; i++) 
	      	{
	         Message message = messages[i];
	         System.out.println("--------------------------------------------");
	         System.out.println("User Number " + (i + 1));
	         
	         ID[i] = message.getSubject();
	        	 
	         System.out.println("From: " + message.getFrom()[0]);
	         System.out.println("ID: " +  ID[i]);
	         }
	    
	      id_1 = ID[0];
	      id_2 = ID[1];
	      id_3 = ID[2];
	      
	      //close the store and folder objects
	      
	      emailFolder.close(false);
	      store.close();

	      } 
	      catch (NoSuchProviderException e) 
	      {
	         e.printStackTrace();
	      } 
	      catch (MessagingException e) 
	      {
	         e.printStackTrace();
	      } 
	      catch (Exception e) 
	      {
	         e.printStackTrace();
	      }
	   }
	   
	   /**
	    * Executes the check method to do external reading.
	    * Database connection is established and any data obtained from the application is put into the database.
	    * The database used to view tables and user details is mySQL.
	    * 
	    * 
	    * @param args
	    */
	   
	public static void main(String[] args) 
	{
	      String host = "pop.gmail.com";// server
	      
	      String mailStoreType = "pop3"; //type
	      String username = "nfcarchive@gmail.com";// destination email username
	      String password = "nfc_java";// destination email passphrase

	      check(host, mailStoreType, username, password);
		
	      try
		{
			
	    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance(); 
	    	  
	    	//Get a connection to DB
			ArrayList<Statement> statements = new ArrayList<Statement>();
			
			PreparedStatement psInsert;
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo?autoReconnect=true&useSSL=false", "root" , "nibby124*");
			//create a statement
			
			Statement myStmt = myConn.createStatement();
			
			//execute a SQL query
			
			ResultSet myRs = null; //executeQuery("Select * from actors");
			
			statements.add(myStmt);
			
			myStmt.execute("Create table Profile(Name varchar(40),"+"ID varchar(40))");
			
			myStmt.execute("Create table UserLog(ID varchar(40),"+"Mood varchar(40),"+"Time varchar(60))");
			
			
			psInsert = myConn.prepareStatement("Insert into Profile values(?, ?)");
			statements.add(psInsert);
			
			System.out.println("\nPlease enter your name: ");
			user_1 = read.nextLine();
			psInsert.setString(1, user_1);
			psInsert.setString(2, id_1);
			psInsert.executeUpdate();
			
			
			System.out.println("\nPlease enter your name: ");
			user_2 = read.nextLine();
			psInsert.setString(1, user_2);
			psInsert.setString(2, id_2);
			psInsert.executeUpdate();
			
			
			psInsert = myConn.prepareStatement("Insert into UserLog values(?, ?, ?)");
			statements.add(psInsert);
			
			psInsert.setString(1, user_1);
			psInsert.setString(2, mood);   //requires to be obtained from pulse sensor readings
			psInsert.setString(3, dateFormat.format(cal.getTime()));
			psInsert.executeUpdate();
			
			
			myRs = myStmt.executeQuery("Select * from UserLog");
			
			//process result set
			
			System.out.println("\n\n\n");
			
			while(myRs.next())
			{
				System.out.println(myRs.getString(1) + ", " + myRs.getString(2) + ", " + myRs.getString(3));
				//System.out.println("\n\n");
			}
			
		}
		
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
	}

}
