package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Whois  extends Command{
    public Whois(){
        this.name = "whois";
        this.usage = "/whois <alias>";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        if (args.length == 1){
            if (args[0].equals(Bot.alias)) message.getChannel().sendMessage("`"+args[0]+"` belongs to: `"+Bot.bot_id+"`").queue();
        }
        return true;
    }
}
