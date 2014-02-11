import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author anshika
 *
 */
public class Aagarw6 {

	ArrayList<String> array;
	ArrayList<Integer> arrayCid;
	String fileName;
	int counter;
	/**
	 * @param args
	 */
	public Aagarw6()
	{
		counter = 0;
		fileName = "transFile.txt";
		array = new ArrayList<String>();
    	arrayCid = new ArrayList<Integer>();
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Aagarw6 aagarw6 = new Aagarw6();
        Connection conn = null;
        conn = aagarw6.connectDB();
        aagarw6.creatTable(conn);
        aagarw6.readInputFile(conn);
        aagarw6.deleteTable(conn);
        aagarw6.closeConnection(conn);

	}

	/**
	 * Create connection class
	 * @return
	 */
	
	 public Connection connectDB() {
	        String url = "jdbc:mysql://localhost:3306/";
	        String dbName = "courses";
	        String driver = "com.mysql.jdbc.Driver";
	        String userName = "root";
	        String password = "root";
	        Connection conn = null;
	        try {
	            Class.forName(driver).newInstance();
	            conn = DriverManager.getConnection(url + dbName, userName, password);
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
	        String sql1 = "CREATE TABLE `courses`.`Course` (`cid` INTEGER  NOT NULL,`name` VARCHAR(20)  NOT NULL, `capacity` int(11), PRIMARY KEY (`cid`) )";
	        String sql2 = "CREATE TABLE `courses`.`PrerequisiteCourse` (`cid` INTEGER  NOT NULL,`pid` INTEGER  NOT NULL,  PRIMARY KEY (`cid`, `pid`))";
	        try {
	            stmt = conn.createStatement();
	            stmt.execute(sql1);
	            stmt.execute(sql2);
	            stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		/**
		 * Create Delete Table
		 * @param conn
		 */
	    private void deleteTable(Connection conn) {
	       Statement stmt = null;
	        String sql1 = "drop table `courses`.`Course`";
	        String sql2 = "drop table `courses`.`PrerequisiteCourse`";
	        try {
	            stmt = conn.createStatement();
	            stmt.execute(sql1);
	            stmt.execute(sql2);
	            stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	   }
	    
	   
	    private boolean check(Connection conn,int cid,int pid){
	    	Statement stmt = null;
	    	String select = "Select * From courses.PrerequisiteCourse ";
	    	ResultSet rs = null;
	    	try{
	    		stmt = conn.createStatement();
	    		rs = stmt.executeQuery(select);
	    		while(rs.next()){
	    			int cid1 = rs.getInt("cid");
	    			int pid1 = rs.getInt("pid");
	    			if(cid1==cid && pid1==pid)
	    				return false;
	    		}
	    	}catch(SQLException e){
	    		e.printStackTrace();
	    	}
	    	return true;
	    }
	    
	    private boolean check(Connection conn,int cid){
	    	Statement stmt = null;
	    	String select = "Select * From courses.Course";
	    	ResultSet rs = null;
	    	try{
	    		stmt = conn.createStatement();
	    		rs = stmt.executeQuery(select);
	    		while(rs.next()){
	    			int cid1 = rs.getInt("cid");
	    			if(cid1==cid)
	    				return false;
	    		}
	    	}catch(SQLException e){
	    		e.printStackTrace();
	    	}
	    	return true;
	    }
	    
	    
		   
	    private boolean check1(Connection conn,int cid){
	    	Statement stmt = null;
	    	String select = "Select * From courses.PrerequisiteCourse ";
	    	ResultSet rs = null;
	    	try{
	    		stmt = conn.createStatement();
	    		rs = stmt.executeQuery(select);
	    		while(rs.next()){
	    			int cid1 = rs.getInt("cid");
	    			int pid1 = rs.getInt("pid");
	    			if(cid1==cid || pid1==cid)
	    				return false;
	    		}
	    	}catch(SQLException e){
	    		e.printStackTrace();
	    	}
	    	return true;
	    }
	    
	    
	       /**
		    * Create Delete Tupple
		     * @param conn
		     */
	    private void deleteTupple(Connection conn,String strLine)	    {
	    	//System.out.println("1 : "+strLine);
	    	
	    	int cid=Integer.parseInt(strLine.substring(0));
	    	Statement stmt = null;
	    	String deleteCourse = "delete from courses.Course where cid="+cid;
	    	String deletePrereq = "delete from courses.PrerequisiteCourse where pid="+cid+" or cid="+cid;
	    	
	    	if(!check(conn ,cid)){
	    		try{
	    			stmt=conn.createStatement();
	    			stmt.executeUpdate(deleteCourse);
			        stmt.close();
			        System.out.println("Done!!");
	    		}
	    		catch(SQLException e){
	    			System.out.println("error!!");
	    		}
	    	}
	    	else
	    	{
	    		System.out.println(cid+" does not exist");
	    	}
	    	
	    	
	    }
	    /**
	     * Create Insert Tupple
	     * @param conn
	     */
	    private void insertTupple(Connection conn, String strLine)
	    {
	    	Statement stmt = null;
	        String insertCourse = "insert into `courses`.`Course` values";
	        String insertPrereq = "insert into `courses`.`PrerequisiteCourse` values";
	        
	    	//System.out.println("2 : " +strLine);
	    	String name = "";
	    	int capacity =0 ;
	    	int pid=0;
	    	int cid=Integer.parseInt(strLine.substring(0, strLine.indexOf(" ")));	        
	        
	    	strLine=strLine.substring(strLine.indexOf(" "));
	    	strLine = strLine.trim();
	    	name = strLine.substring(0, strLine.indexOf(" "));
	    	strLine=strLine.substring(strLine.indexOf(" "));
	    	strLine = strLine.trim();
	    	name=name.trim();
	    	if (!strLine.isEmpty()){
	    		if(strLine.contains(" ")){
	    			capacity=Integer.parseInt(strLine.substring(0, strLine.indexOf(" ")));
	    			strLine=strLine.substring(strLine.indexOf(" "));
	    			strLine=strLine.trim();
	    			
	    		}
	    		else {
	    			capacity=Integer.parseInt(strLine.substring(0));
	    			strLine="";
	    		}
	    		while(!strLine.isEmpty()){
	    			if(strLine.contains(" ")){
	    				pid=Integer.parseInt(strLine.substring(0, strLine.indexOf(" ")));
	    				strLine=strLine.substring(strLine.indexOf(" "));
		    			strLine=strLine.trim();
	    			}
	    			else{
	    				pid=Integer.parseInt(strLine.substring(0));
		    			strLine="";
	    			}
	    			//System.out.println(cid + "," +pid);
	    			if(check(conn,cid,pid)){
		    			try {
		    	            stmt = conn.createStatement();
		    	            stmt.execute(insertPrereq+"("+cid+","+pid+")");
		    	            stmt.close();
		    	            System.out.println("Done!!");
		    	        } catch (SQLException e) {
		    	            System.out.println("ERROR!!");
		    	        	//e.printStackTrace();
		    	        }
	    			}
	    			else{
	    				System.out.println(cid+","+pid+" already present in prerequisite course relation");
	    			}
	    		}
	    		
	    	}
	    	//System.out.println(cid + name + capacity + ","+ strLine);
	    	if(check(conn,cid)){
	    		try {
		            stmt = conn.createStatement();
		            stmt.execute(insertCourse+"("+cid+",'"+name+"',"+capacity+")");
		            stmt.close();
		            System.out.println("Done!!");
		        } catch (SQLException e) {
		            System.out.println("ERROR!!");
		        	//e.printStackTrace();
		        }
	    	}
	    	else{
	    		System.out.println(cid + " already present in Course relation");
	    	}
	   
	    	
	   }
	    /**
	     * Create AverageCapacity Tupple
	     * @param conn
	     */
	    private void averageCapacity(Connection conn)
	    {
	    	Statement stmt = null;
	        String average = "select AVG(Capacity) From `courses`.`Course`";
	        ResultSet rs = null;
	        try{
	        	stmt = conn.createStatement();
	            rs = stmt.executeQuery(average);
	            while(rs.next()){
	            	float avg=rs.getFloat(1);
	            	System.out.println("average capacity of all the courses : "+avg);
	            }
	            stmt.close();
	        } catch (SQLException e) {
	            System.out.println("ERROR!!");
	        	//e.printStackTrace();
	        }
	    	//System.out.println("3 : inside avaerage capacity");
	    }
	    /** Create output names
	     * 
	     * @param conn
	     */
	    private boolean checkPresence(int tempArrayVal){
	    	int counter = 0;
	    	while(counter < arrayCid.size()){
	    		if (tempArrayVal == arrayCid.get(counter)){
					return true;
				}
	    		counter++;
	    	}
	    	return false;
	    }
	    
	    private void outputNamesRecur(Connection conn, int id){
	    	ArrayList<Integer> tempArray = new ArrayList<Integer>();
	    	Statement stmt = null;
	    	String names = "select pid From courses.prerequisitecourse where cid = " +id;
	        ResultSet rs = null;
	        
	        try{
	        	
	        	stmt = conn.createStatement();
	            rs = stmt.executeQuery(names);
	            
	            while(rs.next()){
	            	tempArray.add(rs.getInt(1));
	            }
	            
	            int count = 0;
	            while(count < tempArray.size()){
	            	if (!checkPresence(tempArray.get(count))){
	            		arrayCid.add(tempArray.get(count));
	            	}
	            	count++;
	            }
	            
	        }
	        catch(SQLException e){
	        	System.out.println("error!!!");
	        	e.printStackTrace();
	        }
	        
	    	
	    }
	    
	    private void outputNames(Connection conn,String strLine)
	    {
	    	int cid=Integer.parseInt(strLine.substring(0));
	    	outputNamesRecur(conn, cid);
	    	
	    	int counter = 0;
	    	while(counter < arrayCid.size()){
	    		outputNamesRecur(conn, arrayCid.get(counter));
	    		counter++;
	    	}	     
	    	Statement stmt = null;
	    	String names = "select name From courses.course where cid = ";
	        ResultSet rs = null;
	        System.out.print("prerequisites of "+strLine+" : ");
	    	 
	        
	    	counter = 0;
	    	while(counter < arrayCid.size()) {
	    		try{
		        	
		        	stmt = conn.createStatement();
		            rs = stmt.executeQuery(names+arrayCid.get(counter));
		            while(rs.next()){
		            System.out.print(rs.getString(1)+" ");
		            }
	    		}
	    		catch(SQLException e){
	    			System.out.println("Error!!");
	    		}
	    	counter ++;
	    	}
	       
	    	
	    	//System.out.println("4 : "+strLine);
	    }
	    
	    /** Create output average capacity
	     * @param conn
	     */
	    private void outputAverageCapacity(Connection conn,String strLine)
	    {
	    	int cid=Integer.parseInt(strLine.substring(0));
	    	Statement stmt = null;
	        String average = "select AVG(capacity) from courses.course where cid IN (select pid From courses.prerequisitecourse where cid="+cid+")";
	        ResultSet rs = null;
	        try{
	        	stmt = conn.createStatement();
	            rs = stmt.executeQuery(average);
	            while(rs.next()){
	            	float avg=rs.getFloat(1);
	            	System.out.println("average capacity of all the prereqcourses for cid = "+cid+" is "+(int)avg);
	            }
	            stmt.close();
	        } catch (SQLException e) {
	            //System.out.println("ERROR!!");
	        	e.printStackTrace();
	        }
	    	//System.out.println("5 : "+strLine);
	    	
	    }
	    /**
	     * Create check prerequisites
	     * @param conn
	     */
	    private void checkPrerequisites(Connection conn)
	    {
	    	Statement stmt = null;
	        String names = "select name from courses.Course where cid IN (SELECT cid from courses.Prerequisitecourse group by cid having count(pid)>1)";
	        ResultSet rs = null;
	        try{
	        	stmt = conn.createStatement();
	            rs = stmt.executeQuery(names);
	            System.out.print("Courses with more than one pre-requisite : ");
	            if (!rs.last()){
	            	System.out.println("no courses with more than one prerequisites");
	            }
	            rs = stmt.executeQuery(names);
	            while(rs.next()){
	            	String nam = rs.getString(1);
	            	System.out.print(nam+" ");
	            }
	            System.out.println("");
	            stmt.close();
	        } catch (SQLException e) {
	            //System.out.println("ERROR!!");
	        	e.printStackTrace();
	        }
	    	
	    	//System.out.println("6 : check prerequisites");
	    }
	    private void readInputFile(Connection conn)
	    {
	    	try
	    	{
	    		FileInputStream fstream = new FileInputStream(fileName);
	       		DataInputStream in = new DataInputStream(fstream);
	       		BufferedReader br = new BufferedReader (new InputStreamReader(in));
		    	String strLine;
		    	while ((strLine = br.readLine())!= null )
		    	{
		    		char an = strLine.charAt(0);
		    		strLine = strLine.substring(1);
		    		strLine = strLine.trim();
		    		switch (an){
		    		case '1':
		    			deleteTupple(conn,strLine);
		    			break;
		    		case '2':
		    			insertTupple(conn,strLine);
		    			break;
		    		case '3':
		    			averageCapacity(conn);
		    			break;
		    		case '4':
		    			outputNames(conn,strLine);
		    			System.out.println();
		    			break;
		    		case '5':
		    			outputAverageCapacity(conn,strLine);
		    			break;
		    		case '6':
		    			checkPrerequisites(conn);
		    			break;
		    				
		    		}
		    	}
	    	}
	    	catch(Exception e)
	    	{
	    		System.err.println("Error: " + e.getMessage());
	    		e.printStackTrace();
	    	}
	    }    
	    
}
