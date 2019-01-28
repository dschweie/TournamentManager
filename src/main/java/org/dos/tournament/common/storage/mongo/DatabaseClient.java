/**
 * 
 */
package org.dos.tournament.common.storage.mongo;

import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Pattern;

import org.bson.Document;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.common.player.AbstractParticipant;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.storage.SingletonStorage;
import org.dos.tournament.common.storage.Storage;

import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatStartedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;

/**
 * @author dirk.schweier
 *
 */
public class DatabaseClient implements Storage,  ServerMonitorListener
{
  static public final String IDENTIFIER = "mongo";
  
  private MongoClient mongoClient = null;
  private String mongoDatabaseName = null;
  private String mongoCollectionParticipantsName = null;
  private int iConnectionStatus = -1;
  
  public DatabaseClient()
  {
    this.mongoClient = null;
    try 
    {
      HashMap<String,Object> _parameters = SingletonStorage.getStorageParameters(IDENTIFIER);
      
      this.mongoDatabaseName = _parameters.getOrDefault("storage.mongo.database", "TournamentManager").toString();
      this.mongoCollectionParticipantsName = _parameters.getOrDefault("storage.mongo.collection.participants", "participants").toString();

      MongoClientOptions clientOptions = new MongoClientOptions.Builder()
          .addServerMonitorListener(this)
          .build();

      this.mongoClient = new MongoClient(new ServerAddress("192.168.56.101", 27017), clientOptions);
    } 
    catch (Exception ex) 
    {
      System.out.println(ex.getLocalizedMessage().toString());
    }
  }
  
  public DatabaseClient(String connectionString)
  {
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("storage.mongo.connectionString", connectionString);
    this.init(parameters);
  }
  
  private void init(HashMap<String, Object> parameters)
  {
    if(-1 == this.iConnectionStatus)
    {
//      this.mongoClient = MongoClients.create(parameters.getOrDefault("storage.mongo.connectionString", "mongodb://localhost:27017").toString());
    }
  }
  
  /* (non-Javadoc)
   * @see org.dos.tournament.common.storage.Storage#open(java.util.HashMap)
   */
  @Override
  public boolean open(HashMap<String, Object> parameters)
  {
    try
    {
//      if(null == this.mongoClient)
//        this.init(parameters);
      //this.mongoClient.startSession();
    }
    catch(Exception e) { this.mongoClient = null; }
    
    return null != this.mongoClient;
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.common.storage.Storage#close()
   */
  @Override
  public void close()
  {
    // TODO Auto-generated method stub
    this.mongoClient.close();
    this.mongoClient = null;
  }
  
  public boolean isConnected()
  {
    return ( -1 < this.iConnectionStatus );
  }
  
  public boolean isFailed()
  {
    return ( -5 == this.iConnectionStatus );
  }


  /* (non-Javadoc)
   * @see org.dos.tournament.common.storage.Storage#saveParticipant(org.dos.tournament.common.player.IParticipant, boolean)
   */
  @Override
  public boolean saveParticipant(IParticipant participant, boolean overwrite)
  {
    // TODO Auto-generated method stub
    return this.isConnected()?this.saveParticipant((JoueurIndividuel)participant, overwrite):false;
  }
  
  public boolean saveParticipant(JoueurIndividuel participant, boolean overwrite)
  {
    if(this.isConnected())
    {
    
      Document doc = new Document("_id", participant.hasAttribute("_id")?participant.getAttribute("_id"):UUID.randomUUID().toString())
                          .append("name", new Document("forename", participant.getForename()).append("surname", participant.getSurname()))
                          .append("association", participant.getAssociation())
                          .append("_class", participant.getClass().getName());
      if(participant.hasAttribute("_id"))
      {
        this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionParticipantsName).updateOne( Filters.eq      ( "_id", doc.get("_id").toString()), 
                                                                                                                  Updates.combine ( Updates.set("name.forename",  participant.getForename()   ) , 
                                                                                                                                    Updates.set("name.surname",   participant.getSurname()    ) ,
                                                                                                                                    Updates.set("association",    participant.getAssociation())   ));
      }
      else
        this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionParticipantsName).insertOne(doc);
      participant.setAttribute("_id", doc.get("_id"));
      return true;
    }
    else
      return false;
  }

  @Override
  public Vector<JoueurIndividuel> findParticipantAsJoueurIndividuel(String forename, String surname, String association)
  {
    Vector<JoueurIndividuel> _retval = new Vector<JoueurIndividuel>();
    
    if(this.isConnected())
    {
      MongoCollection<Document> _collParticipants = this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionParticipantsName);
      
      if(null != _collParticipants)
      {
  //      FindIterable<Document> _hits = _collParticipants.find(Filters.and(  Filters.regex("name.forname", Pattern.compile(forname.concat(".*")))    ,
  //          Filters.regex("name.surname", Pattern.compile(surename.concat(".*")))   , 
  //          Filters.regex("association",  Pattern.compile(association.concat(".*")))  )).sort(Sorts.ascending("name.surname"));
  //      FindIterable<Document> _hits = _collParticipants.find(Filters.regex("association", association+".*", "i")).sort(Sorts.ascending("name.surname"));
        FindIterable<Document> _hits = _collParticipants.find(Filters.and(Filters.regex("name.forename", forename+".*", "i"), Filters.regex("name.surname", surname+".*", "i"), Filters.regex("association", association+".*", "i"))).sort(Sorts.ascending("name.surname"));
        
        
        for (Document _it : _hits)
        {
          _retval.add(new JoueurIndividuel(_it));
        }    
      }
    }
    return _retval;
  }

  public Vector<JoueurIndividuel> findParticipantAsJoueurIndividuel(String forename, String surname, String association, Vector<IParticipant> excluded)
  {
    Vector<JoueurIndividuel> _retval = new Vector<JoueurIndividuel>();
    Vector<String> _values = new Vector<String>();
    
    if(this.isConnected())
    {
      excluded.forEach(it -> _values.add((String) it.getAttribute("_id")));
      
      MongoCollection<Document> _collParticipants = this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionParticipantsName);
      
      if(null != _collParticipants)
      {
        FindIterable<Document> _hits = _collParticipants.find(  Filters.and(  Filters.regex("name.forename", forename+".*", "i"), 
                                                                              Filters.regex("name.surname", surname+".*", "i"), 
                                                                              Filters.regex("association", association+".*", "i"),
                                                                              Filters.nin("_id", _values))).sort(Sorts.ascending("name.surname"));
        
        
        for (Document _it : _hits)
        {
          _retval.add(new JoueurIndividuel(_it));
        }    
      }
    }
    return _retval;
  }

  @Override
  public void serverHearbeatStarted(ServerHeartbeatStartedEvent event) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent event) 
  {
    this.iConnectionStatus = event.getConnectionId().getLocalValue();
  }

  @Override
  public void serverHeartbeatFailed(ServerHeartbeatFailedEvent event) 
  {
    this.iConnectionStatus = -5;
  }

}
