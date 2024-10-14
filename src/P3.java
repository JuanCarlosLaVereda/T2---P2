import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class P3 {
    public static void main(String[] args) {
        try {
            //planets();
            //newCharacters();
            getAllDeaths();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void planets() throws SQLException {
        String query = "SELECT id, name, diameter FROM planets WHERE diameter BETWEEN ? AND ? ORDER BY diameter";

        MiConector miConector = new MiConector();
        try (Connection connection = miConector.getConexion();
             PreparedStatement ps = connection.prepareStatement(query);){

            for (int i = 0; i<3; i++){
                System.out.println("Dame el diametro minimo de la iteracion " + (i+1));
                Scanner sc = new Scanner(System.in);
                int minDiameter = sc.nextInt();
                System.out.println("Dame el diametro maximo de la iteracion " + (i+1));
                int maxDiameter = sc.nextInt();

                ps.setInt(1, minDiameter);
                ps.setInt(2, maxDiameter);

                ResultSet rs = ps.executeQuery();
                System.out.println("*******************************************************  PLANETS ITERACION " + (i+1) +" *******************************************************");
                while (rs.next()){
                    System.out.println("*********************************************************************************");
                    System.out.println("id: " + rs.getInt(1));
                    System.out.println("-- name: " + rs.getString(2));
                    System.out.println("-- diameter: " + rs.getInt(3));
                    System.out.println("*********************************************************************************");
                }

            }

        }
    }

    public static void newCharacters() throws SQLException {
        String query = "INSERT INTO characters (id, name, planet_id, created_date, updated_date) VALUES (?, ?, ?, ?, ?)";
        MiConector miConector = new MiConector();
        try (Connection connection = miConector.getConexion();
        PreparedStatement ps = connection.prepareStatement(query);){

            String[][] characters = {
                    {"102","Zara", "1"},
                    {"103","Korin", "2"},
                    {"104","Talon", "3"}
            };

            for (String[] character : characters){
                ps.setInt(1, Integer.parseInt(character[0]));
                ps.setString(2, character[1]);
                ps.setInt(3, Integer.parseInt(character[2]));
                ps.setTimestamp(4, new Timestamp(new Date().getTime()));
                ps.setTimestamp(5, new Timestamp(new Date().getTime()));
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("******************************************************* INSERT DE 3 CHARACTERS *******************************************************");

        }
    }

    public static void getAllDeaths() throws SQLException {
        String query = "SELECT c.name AS character_name, k.name AS killer_name FROM deaths d " +
                "JOIN characters c ON d.id_character = c.id LEFT JOIN characters k " +
                "ON d.id_killer = k.id JOIN films f ON d.id_film = f.id WHERE f.id = ?;";

        MiConector miConector = new MiConector();
        System.out.println("******************************************************* MUERTOS *******************************************************");

        try (Connection connection = miConector.getConexion();
        PreparedStatement ps = connection.prepareStatement(query);){
            for (int i = 1; i < 10; i++){
                System.out.println("******************************************************* EPISODIO "+ i +" *******************************************************");
                ps.setInt(1, i);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    System.out.println("El personaje: " + rs.getString(1) + " fue asesinado por: " + rs.getString(2));
                }
            }
        }
    }




}
