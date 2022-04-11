package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Alias  extends Command{
    public Alias(){
        this.name = "alias";
        this.usage = "/alias <id> <name>";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        if (args.length == 2){
            if (args[0].equals(String.valueOf(Bot.bot_id))&&!args[1].equals("")&&!args[1].isEmpty()){ // autist check
                Bot.alias = args[1];
                message.getChannel().sendMessage("alias of `"+Bot.bot_id+"` set to `"+Bot.alias+"`").queue();
            }
        }
        return true;
    }
}
