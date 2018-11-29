package org.dos.tournament.application.common.config;

import java.io.FileInputStream;
import java.util.Properties;

public class SingletonProperties
{
  static private SingletonProperties instance = null;
  private Properties xProperties = null;
  
  private SingletonProperties()
  {
    try
    {
      this.xProperties = new Properties();
      this.xProperties.load(new FileInputStream("config.properties"));
      if(this.xProperties.isEmpty())
        this.xProperties = null;
    }
    catch(Exception e)
    {
      this.xProperties = null;
    }
  }
  
  public static String getProperty(String key)
  {
    return (null==SingletonProperties.getProperties())?null:SingletonProperties.getProperties().getProperty(key);
  }
  
  public static String getOrDefault(String key, String defaultValue)
  {
    return (null==SingletonProperties.getProperties())?null:SingletonProperties.getProperties().getOrDefault(key, defaultValue).toString();
  }
  
  public static Properties getProperties()
  {
    if(null == SingletonProperties.instance)
      SingletonProperties.instance = new SingletonProperties();
    return (null==SingletonProperties.instance)?null:SingletonProperties.instance.xProperties;  
  }
}
