package main;

import cmd.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String cmd = event.getMessage().getContentRaw().split(" ")[0].toLowerCase();
        if (!cmd.equals("/listen")&&!cmd.equals("/die")&&!cmd.equals("/whois")&&!cmd.equals("/alias")&&!cmd.equals("/listen")&&!Bot.is_run) return;
        for (Command command : Bot.commands){
            String[] args;
            if (cmd.length()+1<event.getMessage().getContentRaw().length()) args= event.getMessage().getContentRaw().substring(cmd.length() + 1).split(" ");
            else args = new String[]{""};
            if (cmd.equals("/"+command.name)||Command.hasAlias(command,cmd)){
                if (command.isThread){
                    new Thread(() -> {
                        if (!command.onCommand(event.getAuthor(),event.getMessage(),args)) event.getChannel().sendMessage("usage: "+command.usage).queue();
                    }).start();
                } else if (!command.onCommand(event.getAuthor(),event.getMessage(),args)) event.getChannel().sendMessage("command doesnt exist").queue();
                break;
            }else if (cmd.equals("/die")&&(args.length!=0&&(args[0].equals("all") ||args[0].equals(String.valueOf(Bot.bot_id))||args[0].equals(Bot.alias)))){event.getMessage().getChannel().sendMessage("`died | id: "+Bot.bot_id+" | alias: "+Bot.alias+"`").queue();Bot.shutdown(); break;}
        }
    }
}
