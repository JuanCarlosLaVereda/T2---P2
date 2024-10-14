import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class P4 {
    public static void main(String[] args) {
        try {
            getDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getDatos() throws SQLException{
        MiConector miConector = new MiConector();
        try (Connection con = miConector.getConexion()) {
            DatabaseMetaData databaseMetaData = con.getMetaData();
            ResultSet rs = databaseMetaData.getTables("exemples_accessDades", null, null, null);
            while (rs.next()) {
                String catalogo = rs.getString("TABLE_CAT");
                String esquema = rs.getString("TABLE_SCHEM");
                String tabla = rs.getString("TABLE_NAME");
                String tipo = rs.getString("TABLE_TYPE");
                System.out.println("Catalogo: " + catalogo + " Esquema: " + esquema + " Tipo: " + tipo + " Nombre tabla: " + tabla);
            }
        }


    }
}
