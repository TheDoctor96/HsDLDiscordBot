import net.dv8tion.jda.core.entities.Member;

import java.util.Date;

//TODO sviluppare classe

public class HsDLMember {

    private String nickname;
    private String userID;


    //elenco costruttori
    public HsDLMember(String nickname){
        this.nickname=nickname;

    }


    public String getNickname(){

        return nickname;
    }

    public String getUserID() {
        return userID;
    }
}
