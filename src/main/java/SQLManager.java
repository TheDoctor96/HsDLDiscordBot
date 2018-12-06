import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//TODO: sviluppare classe
public class SQLManager {

    private static String connectrionString = "jdbc:mysql://localhost:3306/hsdl?user=andrea&password=Thedoctoris1&serverTimezone=CET";
    private Connection connection = null;

    public SQLManager() {
        System.out.println("try connection");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectrionString);
            System.out.println("Connessione al DB avvenuta con successo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<HsDLMember> getKickable() {
        //TODO: implementare getKickable
        return null;
    }

    public boolean addMember(HsDLMember user) throws SQLException {
        if (isMember(user))
            return false;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String signUpDate = "\"" + dateFormat.format(date) + "\"";
        String lastActivity = signUpDate;
        String nickname = "\"" + user.getNickname() +"\"";
        String userID ="\"" + user.getUserID() + "\"";



        String query = "INSERT INTO `hsdl`.`users`\n" +
                "(`user_ID`,\n" +
                "`nickname`,\n" +
                "`sign_up_date`,\n" +
                "`last_activity_date`,\n" +
                "`last_month_time`,\n" +
                "`last_six_month_time`,\n" +
                "`total_time`)\n" +
                "VALUES(\n" +
                userID + ",\n" +
                nickname + ",\n" +
                signUpDate + ",\n" +
                lastActivity + ",\n" +
                "0,\n" +
                "0,\n" +
                "0\n);";
        System.out.printf("Trying %s", nickname);
        Statement statement = connection.createStatement();

        if (statement.executeUpdate(query) == 1) {
            statement.close();
            System.out.printf(" -> DONE\n");
            return true;
        }

        statement.close();
        return false;

    }

    public boolean isMember(HsDLMember member) throws SQLException {
        String query = "SELECT user_ID FROM users WHERE user_ID = " + member.getUserID() + ";";
        Statement stm = connection.createStatement();
        ResultSet resultSet = stm.executeQuery(query);

        if (resultSet.next()) {
            resultSet.close();
            stm.close();
            return true;
        }
        resultSet.close();
        stm.close();

        return false;
    }

    public void executeKick(List<HsDLMember> list) {
        //TODO implementare executeKick
    }

    public int updateMembers(List<HsDLMember> members) {

        try {
            int i = 0;
            for (HsDLMember m : members) {
                if(addMember(m))
                    i++;
            }
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
