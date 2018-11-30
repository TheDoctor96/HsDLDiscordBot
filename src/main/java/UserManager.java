import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.List;

public class UserManager extends ListenerAdapter {

    private boolean onKick = false;

    private SQLManager sqlManager;
    private TextChannel channel = null;
    private List<HsDLMember> usersList;

    public UserManager() {
        //TODO cambiare il metodo in base al costruttore che usero in sqlmanager
        sqlManager = new SQLManager();
    }

    public void kick(TextChannel channel) {
        onKick = true;
        this.channel = channel;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        //sono nella fase di kick
        if (onKick) {
            String msg = event.getMessage().getContentRaw();
            String[] cmds = msg.split(" ");
            //TODO riparti da qui

            if(cmds[0].equals("-print"))
                printList();
        }

    }

    private void printList() {
        if(usersList.isEmpty() || usersList ==null) return;

        StringBuffer buffer = new StringBuffer();
        int i = 1;
        for (HsDLMember m : usersList) {
            buffer.append(i++);
            buffer.append(" - ");
            buffer.append(m.getNickname());
            buffer.append("\n");
        }
        channel.sendMessage(buffer.toString()).queue();
    }
}

