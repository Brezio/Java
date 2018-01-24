import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;


public class MusicDatabase extends CheckingMails
{
	private String protocol = "jdbc:derby:";
	private String framework = "embedded";

	
	public static void main(String[] args) 
	{
		new MusicDatabase().go(args);
		System.out.println("Run successfully finished");
	}
	
	private void go(String[] args)
	{
		
		System.out.println("Commencing in " + framework + " mode");
		Connection conn = null;
		ArrayList<Statement> statements = new ArrayList<Statement>();
		PreparedStatement psInsert;
		PreparedStatement psUpdate;
		Statement s = null;
		ResultSet rs = null;
		
		try
		{
//			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			Calendar cal = Calendar.getInstance();
//			System.out.println(dateFormat.format(cal.getTime()));
//			
			Properties props = new Properties();
			props.put("user", "Te");
			props.put("password", "Te");
			String dbName = "MD7";
			conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
			System.out.println("Connected to and created database " + dbName);
			
			conn.setAutoCommit(false);
			s = conn.createStatement();
			statements.add(s);
			
			s.execute("Create table Profile(ID varchar(60), "+" Name varchar(40))");
			s.execute("Create table Stats("+"Date int, "+" Mood varchar(20)," + "Time varchar(20)," + "ID varchar(40))");
			
			psInsert = conn.prepareStatement("Insert into Profile values(?, ?)");
			statements.add(psInsert);
			
			psInsert.setString(1, id_1);
			System.out.println("\nEnter your name please: ");
			String name_1 = read.nextLine();
			psInsert.setString(2, name_1);
		    psInsert.executeUpdate();

			psInsert.setString(1, id_2);
			System.out.println("Enter your name please: ");
			String name_2 = read.nextLine();
			psInsert.setString(2, name_2);
			psInsert.executeUpdate();
			
		
//			psInsert = conn.prepareStatement("Insert into Stats values(?, ?, ?, ?)");
//			statements.add(psInsert);
//			
//			psInsert.setInt(1,1);
//			psInsert.setString(2, "lol" );
//			psInsert.setString(3, "John");
//			psInsert.setInt(4, 10);
//			psInsert.executeUpdate();
//	
			rs = s.executeQuery("SELECT * FROM Profile");
			
			int number = 0;
			boolean failure = false;

			if(!rs.next())
			{
				failure = true;
				reportFailure("No rows in ResultSet");
			}
			
			System.out.println("\nDisplaying User Profile\n\n");
			
			System.out.println("\n\tUser Profile");
			System.out.println("------------------------------");
			System.out.println("ID" +"\t\t" + "\tName");
			
			while(rs.next())
			{
				
				String id = rs.getString(1);
				name_1 = rs.getString(2);
						
				System.out.println("\n" + id + "\t\t\t" + name_1);
				System.out.println("\n" + id);
				System.out.println("------------------------------");
			}
			
			rs.close();
			conn.commit();
			
			System.out.println("\nCommitted the transaction");
			if(framework.equals("embedded"))
			{
				try
				{
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				}

				catch(SQLException se)
				{

					if(((se.getErrorCode() == 50000) &&("XJ015".equals(se.getSQLState()))))
					{
						System.out.println("Derby shut down normally");
					}

					else
					{
						System.err.println("Derby did not shut down normally");
						printSQLException(se);
					}

				}

			}

		}

		catch(SQLException e)

		{

		}

		finally

		{

			try

			{

				if(rs!=null)

				{
					rs.close();
					rs = null;
				}

			} 
			catch(SQLException sqle)
			{
				printSQLException(sqle);
			}

			int i = 0;

			while(!statements.isEmpty())

			{

				Statement st = (Statement)statements.remove(i);

				try
				{
					if(st!=null)
					{
						st.close();
						st = null;
					}

				}
				catch(SQLException sqle)
				{
					printSQLException(sqle);
				}
			}
		}

		try{
			if(conn!=null)
			{
				conn.close();
				conn = null;
			}

		}
		catch(SQLException sqle)
		{
			printSQLException(sqle);
		}

	}


	private void printSQLException(SQLException se)
	{
		// TODO Auto-generated method stub
	}

	private void reportFailure(String string)
	{
		// TODO Auto-generated method stub
	}
}
