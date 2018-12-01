import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//TODO: sviluppare classe
public class SQLManager {

    private static String connectrionString = "jdbc:mysql://localhost:3306/hsdl?user=andrea&password=Thedoctoris1";
    private Connection connection = null;

    public SQLManager(){
        System.out.println("try connection");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectrionString);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public List<HsDLMember> getKickable(){
        //TODO: implementare getKickable
        return null;
    }

    public boolean addMember(HsDLMember user) throws SQLException{
        if(isMember(user))
            return false;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String signInDate = "'"+dateFormat.format(date)+"'";
        String lastActivity = signInDate;
        String nickname = "'"+user.getNickname()+"'";

        String query = "INSERT INTO users (nickname, sign_in_date, last_activity)" +
                "VALUES(" + nickname + "," + signInDate + "," + lastActivity + ");";
        Statement statement = connection.createStatement();

        if(statement.executeUpdate(query)==1){
            statement.close();
            return true;
        }

        statement.close();
        return false;

    }

    public boolean isMember(HsDLMember member) throws SQLException {
        String query = "SELECT * FROM users WHERE nickname = \""  +  member.getNickname() + "\"";
        Statement stm = connection.createStatement();
        ResultSet resultSet = stm.executeQuery(query);

        if(resultSet.next()){
            resultSet.close();
            stm.close();
            return true;
        }
        resultSet.close();
        stm.close();

        return false;
    }

    public void executeKick(List<HsDLMember> list){
        //TODO implementare executeKick
    }
}
