import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CourseControler {

public static void main(String[] args) {
        CourseControler courseControler = new CourseControler();
        Connection conn = null;
        conn = courseControler.connectDB();
        courseControler.creatTable(conn);
 	courseControler.deleteTable(conn);
        courseControler.closeConnection(conn);
    }

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

 }

