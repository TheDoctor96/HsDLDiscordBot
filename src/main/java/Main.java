import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class Main {

    private static String TOKEN = "MzE5MTg4NDYwMDM4NjUxOTE1.DuOCKQ.FcYheYBskVJgVUuQgpuiTEy0t90";


    public static void main(String[] args) throws Exception {
        JDA api = new JDABuilder(TOKEN).build();
        api.addEventListener(new UserManager());
    }

}
