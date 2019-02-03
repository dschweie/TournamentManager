/**
 * 
 */
package org.dos.tournament.common.storage.mongo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.bson.Document;
import org.dos.tournament.branch.petanque.team.JoueurIndividuel;
import org.dos.tournament.common.competition.ITournament;
import org.dos.tournament.common.player.AbstractParticipant;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.storage.SingletonStorage;
import org.dos.tournament.common.storage.IStorage;

import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatStartedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;
import com.mongodb.BasicDBList;

/**
 * @author dirk.schweier
 *
 */
public class DatabaseClient implements IStorage,  ServerMonitorListener
{
  static public final String IDENTIFIER = "mongo";
  
  private MongoClient mongoClient = null;
  private String mongoHost = null;
  private String mongoDatabaseName = null;
  private String mongoCollectionParticipantsName = null;
  private String mongoCollectionTournamentsName = null;
  private int iConnectionStatus = -1;
  
  public DatabaseClient()
  {
    this.mongoClient = null;
    try 
    {
      HashMap<String,Object> _parameters = SingletonStorage.getStorageParameters(IDENTIFIER);
      
      this.mongoDatabaseName = _parameters.getOrDefault("storage.mongo.database", "TournamentManager").toString();
      this.mongoCollectionParticipantsName = _parameters.getOrDefault("storage.mongo.collection.participants", "participants").toString();
      this.mongoCollectionTournamentsName = _parameters.getOrDefault("storage.mongo.collection.tournaments", "tournaments").toString();
      this.mongoHost =  _parameters.getOrDefault("storage.mongo.host", "127.0.0.1").toString();
      MongoClientOptions clientOptions = new MongoClientOptions.Builder()
          .addServerMonitorListener(this)
          .build();

      this.mongoClient = new MongoClient(new ServerAddress(this.mongoHost, 27017), clientOptions);
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

  public boolean isConnected()
  {
    return ( -1 < this.iConnectionStatus );
  }
  
  public boolean isFailed()
  {
    return ( -5 == this.iConnectionStatus );
  }


  @Override
  public Vector<HashMap<String, String>> getTournamentData() 
  {
    
    //if(this.isConnected())
    //{
      Vector<HashMap<String,String>> _retval = new Vector<HashMap<String,String>>();

      this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionTournamentsName).find().forEach((Block<Document>) it -> {
        HashMap<String,String> _element = new HashMap<String,String>();
        String _description = "";
        if(it.containsKey("participants"))
          _description = _description.concat(String.valueOf(((ArrayList<Object>) it.get("participants")).size()));
        _element.put("tid", it.getString("tid"));
        _element.put("name", it.containsKey("name")?it.getString("name"):it.getString("tid"));
        _element.put("description", it.containsKey("description")?it.getString("description"):_description);
        _retval.add(_element);
      });
      
      return _retval;
    //}
    //else
    //  return null;
  }

  @Override
  public ITournament loadTournament(String tid) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public Document loadTournamentAsDocument(String tid)
  {
    return this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionTournamentsName).find(Filters.eq("tid", tid)).first();
  }

  //    //////////////////////////////////////////////////////////////////    //
  //    Methoden basierend auf org.dos.tournament.common.storage.Storage      //
  //    //////////////////////////////////////////////////////////////////    //
  
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
  
  public IParticipant findParticipantById(String id)
  {
    IParticipant _retval = null;

    try     
    {
      Document _hit = this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionParticipantsName).find(Filters.eq("_id", id)).first();
      if(null != _hit)
      {
        //Class<?> clazz = Class.forName(_hit.getString("_class"));
        //Constructor<?> ctor = clazz.getConstructor(String.class);
          _retval = (IParticipant) Class.forName(_hit.getString("_class")).getConstructor(Document.class).newInstance(new Object[] { _hit });
      }
    } 
    catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException | ClassNotFoundException e) 
    {
      e.printStackTrace();
    }
    
    return _retval;
  }


  @Override
  public boolean saveTournament(ITournament tournament, boolean overwrite) 
  {
    boolean retval = false;
    if(     ( null != tournament ) 
        &&  ( this.isConnected() ) )
    {
      Document _doc = tournament.toBsonDocument();
      UpdateOptions _option = new UpdateOptions().upsert(true);
      
      this.mongoClient.getDatabase(mongoDatabaseName).getCollection(mongoCollectionTournamentsName).replaceOne(Filters.eq("tid", _doc.get("tid").toString()), _doc, _option);
    }
      
    return false;
  }


  //    //////////////////////////////////////////////////////////////////    //
  //    Methoden basierend auf com.mongodb.event.ServerMonitorListener        //
  //    //////////////////////////////////////////////////////////////////    //
  
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
