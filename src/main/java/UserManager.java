import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.MemberAction;

import java.util.LinkedList;
import java.util.List;

public class UserManager extends ListenerAdapter {

    private boolean onKick = false;

    private SQLManager sqlManager;
    private TextChannel channel = null;
    private List<HsDLMember> usersList;

    private GuildController guildController=null;

    public UserManager(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    private void kick(TextChannel channel) {
        onKick = true;
        this.channel = channel;
        usersList = sqlManager.getKickable();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String msg = event.getMessage().getContentRaw();

        //sono nella fase di kick
        if (onKick) {
            String[] cmds = msg.split(" ");

            if(cmds[0].equals("-print"))
                printList();

            if(cmds[0].equals("-kick")){
                sqlManager.executeKick(usersList);
                executeKick();
            }

            if(cmds[0].equals("-exit"))
                stopKick();

            if(cmds[0].equals("-remove")){
                try {
                    Integer[] indexes = new Integer[cmds.length - 1];
                    for (int i = 1; i < cmds.length; i++)
                        indexes[i - 1] = Integer.parseInt(cmds[i]);
                    removeFromList(indexes);
                }
                catch (Exception e){
                    stopKick();
                    return;
                }

            }


        }

        if(msg.equals("!kick")){
            if(guildController==null)
                guildController = new GuildController(event.getGuild());
            kick(event.getTextChannel());
            int n = sqlManager.updateMembers(fetchMembers());
            System.out.println("Sono stati aggiunti " + n + " membri.");
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

    private void printList(List<HsDLMember> list) {
        if(list.isEmpty() || list ==null) return;

        StringBuffer buffer = new StringBuffer();
        int i = 1;
        for (HsDLMember m : list) {
            buffer.append(i++);
            buffer.append(" - ");
            buffer.append(m.getNickname());
            buffer.append("\n");
        }
        channel.sendMessage(buffer.toString()).queue();
    }

    private void removeFromList(Integer[] indexes){
        List<HsDLMember> list = new LinkedList<>();

        for(int i = 0; i< indexes.length; i++){
            if(--indexes[i]>usersList.size())
                channel.sendMessage("L'utente " + (indexes[i]+1) + " non è presente in lista" ).queue();
            else {
                list.add(usersList.get(indexes[i]));
                usersList.remove(indexes[i]);
            }
        }
        //stampa chi ho rimosso
        printList(list);
    }

    private void stopKick(){
        channel.sendMessage("Fase di kick terminata, bot pronto").queue();
        channel=null;
        onKick=false;
        usersList=null;
    }

    private void executeKick(){
        for(HsDLMember m : usersList){
            guildController.kick(m.getUserID(), "Sei stato kickato per inattività");
        }

        stopKick();
    }

    private List<HsDLMember> fetchMembers(){
        if(guildController==null)
            return null;

        Guild guild = guildController.getGuild();

        List<Member> members = guild.getMembers();
        LinkedList<HsDLMember> hsDLMembers = new LinkedList<>();

        for(Member m : members){
            if(!m.getUser().isBot()) {
                String userID = m.getUser().getId();
                String nickname = m.getEffectiveName();

                //TODO sistema il fetch, deve comprendere tutte le info
                hsDLMembers.add(new HsDLMember(nickname, userID));
            }
        }

        return hsDLMembers;
    }
}

