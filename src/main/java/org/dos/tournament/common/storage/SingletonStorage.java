/**
 * 
 */
package org.dos.tournament.common.storage;

import java.util.HashMap;

import org.dos.tournament.application.common.config.SingletonProperties;
import org.dos.tournament.common.storage.mongo.DatabaseClient;

/**
 *  \brief      Über diese Klasse läuft das Speichern und Laden von Daten
 */
public class SingletonStorage
{
  private static Storage xPrimaryStorage = null;
  
  public static Storage getInstance()
  {
    return SingletonStorage.getPrimaryStorage();
  }
  
  public static Storage getPrimaryStorage()
  {
    if(null == SingletonStorage.xPrimaryStorage)
    {
      SingletonStorage.xPrimaryStorage = SingletonStorage.generateStorageInstance(SingletonProperties.getProperty("storage"));
    }
    
    return SingletonStorage.xPrimaryStorage;
  }

  protected static Storage generateStorageInstance(String storageType)
  {
    Storage _retval = null;
    
    switch(storageType.toLowerCase())
    {
      case DatabaseClient.IDENTIFIER:   _retval = new DatabaseClient(); 
                                        _retval.open(SingletonStorage.getStorageParameters(storageType)); 
                                         break;
      default:                          _retval = null; break;
    }
    
    return _retval;
  }
  
  public static HashMap<String,Object> getStorageParameters(String storageType)
  {
    HashMap<String,Object> _map = new HashMap<String,Object>();
    
    switch(storageType.toLowerCase())
    {
      case "mongo":
      case "file" : try
                    {                      
                      for(Object _key : SingletonProperties.getProperties().keySet())
                      {
                        String _strKey = _key.toString().toLowerCase();
                        if(_strKey.startsWith(String.format("storage.%s.", storageType.toLowerCase())))
                          _map.put(_strKey.toString(), SingletonProperties.getProperty(_strKey));
                      }
                    }
                    catch(Exception e) { /* nothing to do */ }
                    break;
    }
    
    return _map;
  }
}
