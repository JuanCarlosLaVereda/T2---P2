import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class P5 {
    public static void main(String[] args) {
        try {
            getMetaDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getMetaDatos() throws SQLException{
        MiConector miConector = new MiConector();
        try (Connection con = miConector.getConexion()) {
            DatabaseMetaData databaseMetaData = con.getMetaData();
            ResultSet rs = databaseMetaData.getTables("starwars", null, null, null);
            while (rs.next()) {
                String nombre = rs.getString("TABLE_NAME");
                System.out.println("**************************** " + nombre + " ****************************");
                ResultSet rsPrimaryKey = databaseMetaData.getPrimaryKeys("starwars", null, nombre);
                while (rsPrimaryKey.next()) {
                    System.out.println("Clave primaria: " + rsPrimaryKey.getString("COLUMN_NAME"));
                }
                ResultSet rsAjenas = databaseMetaData.getImportedKeys("starwars", null, nombre);
                while (rsAjenas.next()) {
                    String foreignKey = rsAjenas.getString("FKCOLUMN_NAME");
                    String referencedTable = rsAjenas.getString("PKTABLE_NAME");
                    String referencedColumn = rsAjenas.getString("PKCOLUMN_NAME");
                    System.out.println("Clave ajena: " + foreignKey + " -> " + referencedTable + "." + referencedColumn);
                }

                System.out.println("********** COLUMNAS **********");
                ResultSet rs2 = databaseMetaData.getColumns("starwars", null, nombre, null);
                while (rs2.next()) {
                    int nullable = rs2.getInt("NULLABLE");
                    String nulo = "NO";
                    if (nullable == DatabaseMetaData.columnNullable){
                        nulo = "YES";
                    }
                    System.out.println("Columna: " + rs2.getString("COLUMN_NAME") + " - " + rs2.getString("TYPE_NAME") + " - nulable? " + nulo);
                }
            }

        }
    }
}
