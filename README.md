# discord-rat
a RAT which injects into discord and uses discord as a 'C&amp;C'

Of course only for educational purposes!

This RAT is made to be controlled from a discord channel using a discord bot. It supports unlimited 'victims' using the bot is a little tough but not that hard.

The RAT can:
- /clipboard: sends a log in either text or a txt file. read the keylogger buffer stuff lol
- /gettokens: wont even explain it does lightcord,discord,canary,ptb,chrome,opera,opera gx,brave,yandex
- /keylogger <clear/send>: the clear flag will clear the buffer and the send flag will send and clear the buffer. The rat automatically sends the buffer in a txt if the discord char limit is reached. When the buffer is close the the 8mb limit the rat will automatically send and clear the buffer.
- /msgbox <text>: send a msgbox (for fun)
- /type <text>: type things on victims pc. Typing \F4 will translate to ALT+F4 and \n will translate to newline (enter)
- /run <command>: run command via cmd or windows run the flag run=true anywhere in the command will make it so that *windows run* will be used if the flag run=false or no run flag is given the rat will use cmd (new session everytime). The flag timeout for example timeout=5 will terminate the command after 5 seconds so the bot cant be stuck on running a command (the rat freezes while a command is run <- on that pc only)
- /sendfile <full path>: send files from victim pc to the discord channel
- /info: collect info about the victim pc
- /screenshot: send a screenshot to the discord channel
- /isolate <id/alias>: select a pc (or pcs if they have the same alias and you input that alias)
- /alias <bot_id> <alias> &amp; /whois <alias>: give victim pcs an alias (makes it easier in the C&amp;C) -- multiple pcs can have the same nickname (making you able to run a command on multiple pcs) but since they keep their unique ID you wont be stuck looking for which is which
- /notrace: removes itself and the injection from the victims pc leaving no traces
