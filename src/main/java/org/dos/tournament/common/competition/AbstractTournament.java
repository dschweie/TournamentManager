package org.dos.tournament.common.competition;

import java.util.Observable;
import java.util.UUID;

import org.bson.Document;

public abstract class AbstractTournament extends Observable implements ITournament 
{
  protected UUID tid = UUID.randomUUID(); 
  
  @Override
  public Document toBsonDocument() 
  {
    return new Document("tid", tid.toString());
  }

}
