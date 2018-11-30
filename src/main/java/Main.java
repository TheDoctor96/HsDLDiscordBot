import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {

    private static String TOKEN = "QKjDZWiP-nqhUxoLQvJgZAPU5sLcyD1I";
    private UserManager userManager;


    public static void main(String[] args) throws Exception {
        JDA api = new JDABuilder(TOKEN).build();
        SQLManager sqlManager = new SQLManager();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getAuthor().isBot()) return;

        String cmd = event.getMessage().getContentRaw();
        TextChannel channel = event.getTextChannel();

        //TODO: sistema sta roba in qualche modo
        if(cmd.equals("!kick")){
            userManager.kick(channel);
        }


    }
}
