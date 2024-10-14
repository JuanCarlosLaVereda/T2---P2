import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MiConector {

    private static String user = "root";
    private static String pass = "1234";
    private static String database = "starwars";
    private static String url = "jdbc:mysql://localhost:3306/" + database;
    private static Connection con = null;

    public MiConector() {

        try {
            con = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return con;
    }

    public void closeConexion() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
