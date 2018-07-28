package org.dos.tournament.competition;

import java.util.HashMap;

import org.dos.tournament.player.IParticipant;

public abstract class AbstractEncounter implements IEncounter
{
  private HashMap<IParticipant, DefaultCompetitorResult> competitors;

  public AbstractEncounter()
  {
    this.competitors =  new HashMap<IParticipant, DefaultCompetitorResult>();
  }
  
  public boolean addParticipant(IParticipant competitor)
  {
    this.competitors.put(competitor, null);
    return true;
  }
  
  public boolean setResult(IParticipant competitor, DefaultCompetitorResult result)
  {
    boolean _retval = false;
    if(this.competitors.containsKey(competitor))
    {
      this.competitors.put(competitor, result);
      _retval = true;
    }
    return _retval;
  }
  
  protected HashMap<IParticipant, DefaultCompetitorResult> getCompetitors()
  {
    return this.competitors;
  }
  
  public IParticipant getWinner()
  {
    return this.getRank(1);
  }
}
