import java.sql.*;
import java.util.Date;

public class P6 {
    public static void main(String[] args) {
        try {
            getAffiliations(3);
            getAffiliationsByCharacter(8);
            countKills("Palpatine", 3);
            //newCharacters();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAffiliations(int id) throws SQLException {
        String query = "{call get_affiliation_info (?,?,?)}";
        MiConector miConector = new MiConector();
        System.out.println("******************************************************* AFFILIATION BY NAME *******************************************************");
        try (Connection connection = miConector.getConexion();
        CallableStatement cs = connection.prepareCall(query)) {
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.registerOutParameter(3, Types.INTEGER);

            cs.execute();
            System.out.println("=> Name: " + cs.getString(2));
            System.out.println("=> Count: " + cs.getInt(3));

        }
    }

    public static void getAffiliationsByCharacter(int id) throws SQLException {
        String query = "{call get_affiliations_with_characters (?)}";
        MiConector miConector = new MiConector();
        try (Connection connection = miConector.getConexion();
        CallableStatement cs = connection.prepareCall(query)) {
            System.out.println("******************************************************* AFFILIATION BY CHARACTER *******************************************************");
            cs.setInt(1, id);

            cs.execute();
            ResultSet resultSet = cs.getResultSet();
            while (resultSet.next()) {
                System.out.println("*********************************************************************************");
                System.out.println("Affiliation Name: " + resultSet.getString(1));
                System.out.println("Members: " + resultSet.getInt(2));
                System.out.println("*********************************************************************************");

            }


        }
    }

    public static void countKills(String name, int idFilm) throws SQLException {
        String query = "{? = CALL count_kills(?,?)}";
        MiConector miConector = new MiConector();
        try (Connection connection = miConector.getConexion();
        CallableStatement cs = connection.prepareCall(query)) {
            System.out.println("******************************************************* KILLS BY " + name + " IN EPISODE " + idFilm + " *******************************************************");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, name);
            cs.setInt(3, idFilm);

            cs.execute();

            System.out.println("Asesinatos: " + cs.getInt(1));
        }
    }

    public static void newCharacters() throws SQLException {
        String query = "INSERT INTO characters (id, name, planet_id, created_date, updated_date) VALUES (?, ?, ?, ?, ?)";
        MiConector miConector = new MiConector();
        Connection connection = miConector.getConexion();
        try{
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(query);
            Savepoint savepoint1 = connection.setSavepoint("punto de backup");

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
            connection.commit();
            System.out.println("******************************************************* INSERT DE 3 CHARACTERS *******************************************************");

        } catch (SQLException ex) {
            connection.rollback();
        }
    }
}
