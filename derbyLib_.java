package db1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class derbyLib{
	private String protocol = "jdbc:derby:";
	private String framework = "embedded";
	public static void main(String args[]){
		new derbyLib().go(args);
		System.out.println("database finished");
	}
	private void go(String[] args){
		System.out.println("database starting in " + framework + " mode");
		Connection conn = null;
		ArrayList<Statement> statements = new ArrayList<Statement>();
		PreparedStatement psInsert;
		PreparedStatement psUpdate;
		Statement s;
		ResultSet rs = null;
		try{
			Properties props = new Properties();
			props.put("user", "user3");
			props.put("password", "user1");
			String dbName = "MusicDataBasenew51";
			conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);
			System.out.println("Created " + dbName);

			conn.setAutoCommit(false);
			s = conn.createStatement();
			statements.add(s);
			
			//creating tables
			s.execute("Create table happy(songNum int,"+"title varchar(10),"+"genre varchar(10))");
			System.out.println("Created table happy");
			s.execute("Create table sad("+"songNum int,"+"title varchar(10),"+"genre varchar(10))");
			System.out.println("Created table sad");

			//inserting dummy values

			psInsert = conn.prepareStatement("Insert into happy values(?, ?, ?)");
			//statements.add(psInsert);
			psInsert.setInt(1, 1);
			psInsert.setString(2, "Act of Darkening");
			psInsert.setString(3, "Doom Metal");
			psInsert.executeUpdate();
			
			psInsert.setInt(1, 2);
			psInsert.setString(2, "Happy");
			psInsert.setString(3, "Metal duh");
			psInsert.executeUpdate();
			
			psInsert.setInt(1, 3);
			psInsert.setString(2, "Are You Dead Yet");
			psInsert.setString(3, "Pop");
			psInsert.executeUpdate();
			
			psInsert.setInt(1, 4);
			psInsert.setString(2, "Goodness gracious great balls of fire");
			psInsert.setString(3, "Classic");
			psInsert.executeUpdate();
			
			psInsert.setInt(1, 5);
			psInsert.setString(2, "Nyan cat");
			psInsert.setString(3, "cat music");
			psInsert.executeUpdate();
			
			System.out.println("Inserted happy entries");
			

			psInsert = conn.prepareStatement("Insert into sad values(?, ?, ?)");
			//statements.add(psInsert);

			psInsert.setInt(1, 1);
			psInsert.setString(2, "Uptown Funk");
			psInsert.setString(3, "Death Metal");
			psInsert.setInt(1, 2);
			psInsert.setString(2, "Like a G6");
			psInsert.setString(3, "classical");
			psInsert.setInt(1, 3);
			psInsert.setString(2, "Bleed it out");
			psInsert.setString(3, "Pop");
			psInsert.setInt(1, 4);
			psInsert.setString(2, "Don't rain on my parade");
			psInsert.setString(3, "Classic");
			psInsert.setInt(1, 5);
			psInsert.setString(2, "Nyan cat");
			psInsert.setString(3, "cat music");
			psInsert.executeUpdate();
			System.out.println("Inserted sad entries"); 

			rs = s.executeQuery("SELECT * FROM happy");

			int number = 0;
			boolean failure = false;
			if(!rs.next()){
				failure = true;
				reportFailure("No rows in ResultSet");
			}

			while(rs.next()){
				System.out.println(rs.getInt(1));
			}
			conn.commit();
			if(framework.equals("embedded")){
				try{
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				}
				catch(SQLException se){
					if(((se.getErrorCode() == 50000) &&("XJ015".equals(se.getSQLState())))){
						System.out.println("Derby shut down normally");
					}
					else{
						System.err.println("Derby did not shut down normally");
						printSQLException(se);
					}
				}
			}
		}
		catch(SQLException e){
		}
		finally{
			try{
				if(rs!=null){
					rs.close();
					rs = null;
				}
			} catch(SQLException sqle){
				printSQLException(sqle);
			}
			int i = 0;
			while(!statements.isEmpty()){
				Statement st = (Statement)statements.remove(i);
				try{
					if(st!=null){
						st.close();
						st = null;
					}
				}catch(SQLException sqle){
					printSQLException(sqle);
				}
			}
		}
		try{
			if(conn!=null){
				conn.close();
				conn = null;
			}
		}
		catch(SQLException sqle){
			printSQLException(sqle);
		}
	}
	private void printSQLException(SQLException se){
		// TODO Auto-generated method stub
	}
	private void reportFailure(String string){
		// TODO Auto-generated method stub
	}
}