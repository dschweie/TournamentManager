package org.dos.tournament.common.competition;

import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.UUID;

import org.bson.Document;

public abstract class AbstractTournament extends Observable implements ITournament 
{
  protected UUID tid = UUID.randomUUID();
  protected String title = "";
  
  public AbstractTournament()
  {
    this.title = String.format("%1$tA, %1$td.%1$tm.%1$tY (%1$tH:%1$tM Uhr)", new GregorianCalendar());
  }
  
  @Override
  public Document toBsonDocument() 
  {
    return new Document("tid", tid.toString()).append("name", this.title).append("_class", this.getClass().getName());
  }
  
  protected void setTournamentId(UUID tid)
  {
    this.tid = tid;
  }
  
  protected void setTournamentId(String value)
  {
    this.tid = UUID.fromString(value);
  }
  
  protected void setTitle(String value)
  {
    this.title = value.toString();
  }

}
