import javax.swing.plaf.PanelUI;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            planetas();
            //Comentado para que no de error al ya estar añadido
/*            nuevoEpisodio("VII", "El despertar de la fuerza", 7);
            nuevoEpisodio("VIII", "Los ultimos jedi", 8);
            nuevoEpisodio("IX", "El ascenso de los Skywalker", 9);*/
            jediOrder();
            deaths();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void nuevoEpisodio(String episodio, String titulo, int num) throws SQLException{
        MiConector miConector = new MiConector();
        String query = "INSERT INTO films (id, episode, title) VALUES(?, ?,?)";

        try (Connection con = miConector.getConexion();
             PreparedStatement ps = con.prepareStatement(query);) {

            ps.setInt(1, num);
            ps.setString(2, episodio);
            ps.setString(3, titulo);

            System.out.println(ps.executeUpdate() + "");


        }
    }

    public static void planetas() throws SQLException {
        MiConector miConector = new MiConector();
        String query = "SELECT * FROM planets";
        System.out.println("*******************************************************  PLANETS  *******************************************************");

        try (Connection connection = miConector.getConexion();
             Statement statement = connection.createStatement();){
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                System.out.println("*********************************************************************************");
                System.out.println("id: " + rs.getInt(1));
                System.out.println("-- name: " + rs.getString(2));
                System.out.println("-- rotation_period: " + rs.getInt(3));
                System.out.println("-- orbital_period: " + rs.getInt(4));
                System.out.println("-- diameter: " + rs.getInt(5));
                System.out.println("-- climate: " + rs.getString(6));
                System.out.println("-- gravity: " + rs.getString(7));
                System.out.println("-- terrain: " + rs.getString(8));
                System.out.println("-- surface_water: " + rs.getString(9));
                System.out.println("-- population: " + rs.getLong(10));
                System.out.println("-- created_date: " + rs.getDate(11));
                System.out.println("-- updated_date: " + rs.getDate(12));
                System.out.println("-- url: " + rs.getString(13));
                System.out.println("*********************************************************************************");
            }
        }
    }

    public static void jediOrder() throws SQLException{
        String query = "SELECT * FROM characters c INNER JOIN character_affiliations ca ON c.id = ca.id_character INNER JOIN affiliations a ON ca.id_affiliation = a.id WHERE a.affiliation = 'Jedi Order';";
        MiConector miConector = new MiConector();
        System.out.println("*******************************************************  JEDI ORDER  *******************************************************");
        try (Connection connection = miConector.getConexion();
        Statement statement = connection.createStatement();){

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                System.out.println("*********************************************************************************");
                System.out.println("id: " + rs.getInt(1));
                System.out.println("-- name: " + rs.getString(2));
                System.out.println("-- height: " + rs.getInt(3));
                System.out.println("-- mass: " + rs.getFloat(4));
                System.out.println("-- hair_color: " + rs.getString(5));
                System.out.println("-- skin_color: " + rs.getString(6));
                System.out.println("-- eye_color: " + rs.getString(7));
                System.out.println("-- birth_year: " + rs.getString(8));
                System.out.println("-- gender: " + rs.getString(9));
                System.out.println("-- planet_id: " + rs.getInt(10));
                System.out.println("-- created_date: " + rs.getDate(11));
                System.out.println("-- updated_date: " + rs.getDate(12));
                System.out.println("-- url: " + rs.getString(13));
                System.out.println("*******************************************************************************");
            }

        }
    }

    public static void deaths() throws SQLException{
        String query = "SELECT c.name AS dead_character_name, k.name AS killer_name FROM characters c INNER JOIN deaths d ON c.id = d.id_character LEFT JOIN characters k ON d.id_killer = k.id WHERE d.id_film = 3;";

        MiConector miConector = new MiConector();
        System.out.println("*******************************************************  DETHS IN EPISODE III  *******************************************************");
        try (Connection connection = miConector.getConexion();
        Statement statement = connection.createStatement();){
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("*******************************************************************************");
                System.out.println("El personaje: " + rs.getString(1) + " fue asesinado por: " + rs.getString(2));
                System.out.println("*******************************************************************************");
            }

        }
    }
}
