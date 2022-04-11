package cmd;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.Locale;

public class Type extends Command{
    public Type(){
        this.name = "\u0074\u0079\u0070\u0065";
        this.usage = "\u002f\u0074\u0079\u0070\u0065 \u003c\u0077\u006f\u0072\u0064\u0073\u003e";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        try {
            final Robot robot = new Robot();
            String str = "";
            for (int i = 0; i < args.length; i++) str += args[i] + " ";
            str = str.substring(0, str.length() - 1).replace("\\n","\n").toUpperCase().replace("\\F4","f").replace("\\DEL","d");
            for (int i = 0; i<str.length();i++) {
                switch (str.charAt(i)){
                    case 'f':
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_F4);
                        robot.delay(20);
                        robot.keyRelease(KeyEvent.VK_ALT);
                        robot.keyRelease(KeyEvent.VK_F4);
                        break; // troll complete
                    case 'd':
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_ALT);
                        robot.keyPress(KeyEvent.VK_DELETE);
                        robot.delay(20);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyRelease(KeyEvent.VK_ALT);
                        robot.keyRelease(KeyEvent.VK_DELETE);
                        break;
                }
                int y = str.charAt(i); robot.keyPress(y); robot.delay(20); robot.keyRelease(y); robot.delay(20);
            }
            message.getChannel().sendMessage("\u0074\u0079\u0070\u0065\u0064 \u0060"+str.toLowerCase()+"`").queue();
        }catch (Exception ee){}
        return true;
    }
}
