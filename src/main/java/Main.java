import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class Main {

    private static final String TOKEN = "NDkxNzQ0NTUwNTE2NDI0NzMw.DuoFyQ.MA6jzAQA18XVzn9PeAdNmClH3Gc";
    public static final String  TOKEN2 = "MzE5MTg4NDYwMDM4NjUxOTE1.DuoMtg.9kJSZK4A-KaMVXV8sgpvtxLWgZ8";


    public static void main(String[] args) throws Exception {
        JDA api = new JDABuilder(TOKEN2).build();
        SQLManager sqlManager = new SQLManager();
        api.addEventListener(new UserManager(sqlManager));

    }

}
