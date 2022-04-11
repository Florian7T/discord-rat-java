package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Isolate  extends Command{
    public Isolate(){
        this.name = "listen";
        this.usage = "/listen <id>";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
       if (args.length > 0){
           boolean y = false;
           for(String id : args)
               if (id.equals("all")||id.equals(String.valueOf(Bot.bot_id))||id.equals(Bot.alias)){Bot.is_run = true; y = true; message.getChannel().sendMessage("`"+Bot.bot_id +" is listening`").queue(); break;}
           if (!y) Bot.is_run = false;
       }
        return true;
    }
}
