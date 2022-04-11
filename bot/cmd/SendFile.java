package cmd;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.io.File;

public class SendFile extends Command {
    public SendFile(){
        this.name = "sendfile";
        this.usage = "/sendfile";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        String str = "";
        for (int i = 0; i < args.length; i++) str += args[i] + " ";
        str = str.substring(0, str.length() - 1);
        File file = new File(str);
        if (!file.exists()||!file.isFile())message.getChannel().sendMessage("`file either doesn't exist or is a dir`").queue();
        else { try{message.getChannel().sendFile(file).queue();}catch (Exception ee){message.getChannel().sendMessage("`failed to send file (prob to big)`").queue();} }
        return true;
    }
}
