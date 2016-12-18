package com.qa.utility;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
 
public class Log {
 
  static FileHandler handler;
  static Logger logger;
 
  public static void createLog(String path){
    try {
      String [] s = path.split("\\.");
      File f = new File("./"+"logs/"+s[s.length-2]);
      if(f.exists()==false){
        f.mkdirs();
      }
      handler = new FileHandler("logs/"+s[s.length-2]+"/"+s[s.length-1]+".log");
      handler.setFormatter(new SimpleFormatter());
      logger = Logger.getLogger(path);
      logger.addHandler(handler);
     } catch (IOException e) {
        e.printStackTrace();
     }
  }
 
  public static void loggerINFO(String msg){
     logger.info(msg);
  }
 
  public static void loggerWARN(String msg){
     logger.warning(msg);
  }
 
  public static void loggerERROR(String msg){
     logger.severe(msg);
  }
}
