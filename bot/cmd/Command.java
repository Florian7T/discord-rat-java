package cmd;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Command {

    public String name;
    public String usage = "";
    public String desc = "";
    public Permission permission = null;
    public boolean isThread = false;
    public CommandType type = CommandType.NONE;
    public String[] aliases = null;

    public Command(){ }

    public static boolean hasAlias(Command cmd,String s){
        if (cmd.aliases==null) return false;
        for (String x : cmd.aliases) if (("/"+x).equals(s)) return true;
        return false;
    }

    public boolean onCommand(User user, Message message, String[] args){
        return true;
    }

}
