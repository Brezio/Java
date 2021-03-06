package db1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class database

{
    //to link java and the database together
    private String protocol = "jdbc:derby:";
    private String framework = "embedded";

public static void main(String args[])
{
  new database().go(args);
  System.out.println("database finished");
}
  
private void go (String[] args)
{

  System.out.println("database starting in " + framework + " mode");
  Connection conn = null;
  ArrayList<Statement> statements = new ArrayList<Statement>();
  PreparedStatement psInsert;
  PreparedStatement psUpdate; 
  Statement s;
  ResultSet rs = null;

  try
{
  Properties props = new Properties();
  props.put("user", "user1");
  props.put("password", "user1");

  String dbName = "musicDatabase13";
  System.out.println("created "+dbName);
  
  //to connect to database
  conn = DriverManager.getConnection(protocol + dbName + ";create=true", props);

  System.out.println("Connected to and created database " + dbName);
  conn.setAutoCommit(false);

  s = conn.createStatement();

  statements.add(s);
  //here we make the tables and datatype of columns
  s.execute("Create table romantic(songnum int," +"title varchar(40)," +"genre varchar(40))");

  System.out.println("Created table romantic");

  s.execute("Create table angry("+"songnum int," +"title varchar(40)," +"genre varchar(40))");
  System.out.println("Created table angry");

//inserting into romantic mood table
/******************************************************/
  psInsert = conn.prepareStatement("Insert into romantic values(?, ?, ?)");

  psInsert.setInt(1, 1);//for column 1 row 1, we put song num 1
  psInsert.setString(2, "addicted to pain");
  psInsert.setString(3, "rock");
  psInsert.executeUpdate();
  System.out.println("inserted 1, addicted to pain, rock");
  psInsert.setInt(1, 2);//for column 2 row 1, we put song num 2
  psInsert.setString(2, "get lucky");
  psInsert.setString(3, "death metal");
  psInsert.executeUpdate();
  System.out.println("inserted 2, get lucky, death metal");
  System.out.println("created romantic table");
  /***********************************************************/
  //inserting into angry
  psInsert = conn.prepareStatement("Insert into angry values(?, ?, ?)");

  psInsert.setInt(1, 1);//for column 1 row 1, we put song num 1
  psInsert.setString(2, "numb");
  psInsert.setString(3, "rock");
  psInsert.executeUpdate();
  System.out.println("inserted 1, numb, rock");
  psInsert.setInt(1, 2);//for column 2 row 1, we put song num 2
  psInsert.setString(2, "beautiful pain");
  psInsert.setString(3, "rap");
  psInsert.executeUpdate();
  System.out.println("inserted 2, beautiful pain, rap");
  System.out.println("created angry table");

  rs = s.executeQuery("SELECT * FROM romantic");

  int number = 0;

  boolean failure = false;

  if(!rs.next())

{

failure = true;

reportFailure("No rows in ResultSet");

}

while(rs.next())

{

System.out.println(rs.getInt(1)); 
System.out.println(rs.getString(2));
System.out.println(rs.getString(3));

}

conn.commit();

System.out.println("Committed the transaction");

if(framework.equals("embedded"))

    {
      try
      {
        DriverManager.getConnection("jdbc:derby:;shutdown=true");
      } catch(SQLException se)

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
  } catch(SQLException sqle)

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
  }catch(SQLException sqle)


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

}catch(SQLException sqle)

private void printSQLException(SQLException se)

{

// TODO Auto-generated method stub

}

private void reportFailure(String string)

{

// TODO Auto-generated method stub

}

}
