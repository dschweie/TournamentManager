package org.dos.tournament.competition;

import org.dos.tournament.player.IParticipant;

public interface IEncounter
{
  public boolean addParticipant(IParticipant competitor);
  public boolean setResult(IParticipant competitor, DefaultCompetitorResult result);
  
  public boolean isComplete();
  
  public IParticipant getWinner();
  public IParticipant getRank(int i);
  
}
