import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 */

/**
 * @author anshika
 *
 */
public class Aagarw6 {
	
	String dbName;
	static String instructor, teaches;
	
	public Aagarw6()
	{
		
		dbName = "hw4";
		//dbName = "university1";
		//dbName = "university2";
	}
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("number of tuples for table \"instructor\" (max 99999): ");
		try {
			instructor = br.readLine();
			while(!checkString(instructor))
			{
				System.out.print("number of tuples for table \"instructor\" (max 99999): ");
				instructor = br.readLine();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print("number of tuples for table \"teaches\" (max 99999): ");
		try {
			teaches = br.readLine();
			while(!checkString(teaches))
			{
				System.out.print("number of tuples for table \"teaches\" (max 99999): ");
				teaches = br.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Aagarw6 aagarw6 = new Aagarw6();
        Connection conn = null;
        conn = aagarw6.connectDB();
        aagarw6.creatTable(conn);
      //  aagarw6.readInputFile(conn);
        aagarw6.insertTupple(conn);
       // aagarw6.deleteTable(conn);
        aagarw6.closeConnection(conn);
        

	}
	
	static boolean checkString(String name)
	{
		int val=Integer.valueOf(name);
		if (val>99999) {
			System.out.println("Value greater than 99999");
			return false;
		}
		
		return true;
		
	}
	

	/**
	 * Create connection class
	 * @return
	 */
	
	 public Connection connectDB() {
	        String url = "jdbc:mysql://localhost:3306/";
	        String driver = "com.mysql.jdbc.Driver";
	        String userName = "root";
	        String password = "root";
	        Connection conn = null;
	        try {
	            Class.forName(driver).newInstance();
	            System.out.println("connecting to database");
	            conn = DriverManager.getConnection(url, userName, password);
	            Statement stmt = conn.createStatement();
            	String sql ="Create database if not exists "+dbName;
	            stmt.executeUpdate(sql);
	            System.out.println("Connected to the database");

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return conn;
	    }
	 
	 /**
	  * close connection class
	  * @param conn
	  */

	    public void closeConnection(Connection conn) {
	        try {
	            if (conn != null) {
	                conn.close();
	                System.out.println("Close the connection to database!");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * Create Table Class
	     * @param conn
	     */
		private void creatTable(Connection conn) {
	        Statement stmt = null;
	        String sql1 = "CREATE TABLE `"+dbName+"`.`instructor` (`ID` varchar(5) not null,`name` varchar(20) not null,`deptName` varchar(20) not null,`salary` numeric(8,2) not null, primary key (ID)) engine=memory;";
	        String sql2 = "CREATE TABLE `"+dbName+"`.`teaches`(`ID` varchar(5) not null,`courseId` varchar(8) NOT NULL,`semester` varchar(6) not null, `year` numeric(4,0) not null, primary key (ID, courseId, semester, year), foreign key (ID) references instructor(ID)on delete cascade) engine=memory;"; 
	        try {
	            stmt = conn.createStatement();
	            stmt.execute(sql1);
	            stmt.execute(sql2);
	            stmt.close();
	        } catch (SQLException e) {
	            //e.printStackTrace();
	        }
	    }
		/**
		 * Create Delete Table
		 * @param conn
		 */
	    private void deleteTable(Connection conn) {
	       Statement stmt = null;
	        String sql1 = "drop table `"+dbName+"`.`Course`";
	        String sql2 = "drop table `"+dbName+"`.`PrerequisiteCourse`";
	        try {
	            stmt = conn.createStatement();
	            stmt.execute(sql1);
	            stmt.execute(sql2);
	            stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	   }
	    static String createRandomString()
	    {
		    String name = "";
			int length = createRandom(2, 20);
			for (int i = 0; i < length; i++) {
				name = name + (char)(createRandom(97, 123));
			}
			return name;
	    }
	    
	    static String createRandomCourseId()
	    {
		    String name = "";
			
			for (int i = 0; i < 8; i++) {
				name = name + (char)(createRandom(97, 123));
			}
			return name;
	    }
	    
	    static String createRandomSemester()
	    {
	    	String[] sem = {"one", "two","three","four","five","six","seven","eight"};
		    
			return sem[createRandom(0,7)];
	    }
	    
	    static int createRandomYear()
	    {
	    	return createRandom(2001,2012);
	    }
	    
	    static int createRandomSalary()
	    {
	    	return createRandom(29001, 100000);
	    }
	    
	   static int createRandom(int i, int f) {
	    	final Random r = new Random(); 
			int x = r.nextInt(f);
			while(x < i || x > f)
				x = r.nextInt(f);
			return x;
	    }
	    	
	    
	    /**
	     * Create Insert Tupple
	     * @param conn
	     */
	    private void insertTupple(Connection conn)
	    {
	    	Statement stmt = null;
	        String insertInstructor = "insert into `"+dbName+"`.`instructor` values";
	        String insertTeaches = "insert into `"+dbName+"`.`teaches` values";
	        
    			try {
    	            
    	            for(int i=1;i<=Integer.valueOf(instructor);i++)
    	            {	
    	            	stmt = conn.createStatement();
    	            	String ID=String.valueOf(i);
    	            	String name=createRandomString();
    	            	String deptname=createRandomString();
    	            	float salary=(float)createRandomSalary();
    	            	stmt.execute(insertInstructor+"('"+ID+"','"+name+"','"+deptname+"',"+salary+")");
    	            	System.out.println(i + " : " + (float)((float)i/(float)Integer.valueOf(instructor)*100));
    	            	stmt.close();
    	            }
    	            System.out.println("instructor done");
    	            for(int i=1;i<=Integer.valueOf(teaches);i++)
    	            {	
    	            	stmt = conn.createStatement();
    	            	String ID=String.valueOf(createRandom(1, Integer.valueOf(instructor)));
    	            	String courseId=createRandomCourseId();
    	            	String semester=createRandomSemester();
    	            	int year=Integer.valueOf(createRandomYear());
    	            	stmt.execute(insertTeaches+"('"+ID+"','"+courseId+"','"+semester+"',"+year+")");
    	            	System.out.println(i + " : " + (float)((float)i/(float)Integer.valueOf(teaches)*100));
    	            	stmt.close();
    	            }
    	            System.out.println("teaches done");
    	           
    	            
    	            System.out.println("Done!!");
    	        } catch (SQLException e) {
    	            System.out.println("ERROR!!");
    	        	e.printStackTrace();
    	        }
			
			
	    }
	    

}

