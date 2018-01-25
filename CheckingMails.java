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

/* Using JavaMail Api to store NFC IDs */

public class CheckingMails 
{
   static Scanner read = new Scanner(System.in);
   static public String [] ID = new String[10]; 
   public static String id_1, id_2, id_3;
   
   public static void check(String host, String storeType, String user, String password) 
   {   
	   try 
      {
		   
		   /*
		    * Obtain system date and time
		    *
		    */
		 
		   
//		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		   Calendar cal = Calendar.getInstance();
//		   System.out.println(dateFormat.format(cal.getTime())); 
 
		   
		   
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
         /* read the first message and assign it to the first user */
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
      
      System.out.println("\n\nID 1 - " + id_1);
      System.out.println("ID 2 - " + id_2);
      System.out.println("ID 3 - " + id_3);
      
      
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

   public static void main(String[] args) 
   {
      String host = "pop.gmail.com";// server
      String mailStoreType = "pop3"; //type
      String username = "nfcarchive@gmail.com";// destination email username
      String password = "xxxxxxxxx";// destination email pass

      check(host, mailStoreType, username, password);
   }

}
