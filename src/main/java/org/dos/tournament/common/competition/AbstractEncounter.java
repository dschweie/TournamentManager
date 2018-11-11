package org.dos.tournament.common.competition;

import java.util.Vector;

import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.result.IResult;

public abstract class AbstractEncounter implements IEncounter
{
  protected Vector<IParticipant> competitors;
  protected IResult result;

  public AbstractEncounter()
  {
    this.competitors =  new Vector<IParticipant>();
    this.result = null;
  }
  
  public void addParticipant(IParticipant competitor)
  {
    this.competitors.add(competitor);
  }
  
  
  
  /**
   * \copydoc org::dos::tournament::common::competition::IEncounter::setResult(IResult)
   */
  @Override
  public void setResult(IResult result)
  {
    this.result = result;
  }

  /**
   * \copydoc org::dos::tournament::common::competition::IEncounter::isComplete()
   */
  @Override
  public boolean isComplete()
  {
    return false;
  }

  /**
   * \copydoc org::dos::tournament::common::competition::IEncounter::isScored()
   */
  @Override
  public boolean isScored()
  {
    return null != this.result;
  }

  protected Vector<IParticipant> getCompetitors()
  {
    return this.competitors;
  }
  
  public IParticipant getWinner()
  {
    return this.getRank(1);
  }
}
