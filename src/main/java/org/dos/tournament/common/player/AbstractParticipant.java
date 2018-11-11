package org.dos.tournament.common.player;

import java.util.Collection;
import java.util.Vector;

import org.dos.tournament.common.player.utils.IParticipantId;
import org.dos.tournament.common.player.utils.ParticipantStatus;
import org.dos.tournament.common.result.IResult;
import org.dos.tournament.common.result.ITotalResult;

public abstract class AbstractParticipant implements IParticipant
{
  protected IParticipantId id = null;
  protected ParticipantStatus status = ParticipantStatus.ACTIVE;
  protected ITotalResult result = null;

  @Override
  public IParticipantId getParticipantId()
  {
    return this.id;
  }

  @Override
  public void setParticipantId(IParticipantId id)
  {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.players.IParticipant#getCode()
   */
  @Override
  public String getCode()
  {
    return this.id.getCode();
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.players.IParticipant#getName()
   */
  @Override
  public String getName()
  {
    return this.id.getName();
  }
  

  /* (non-Javadoc)
   * @see org.dos.tournament.players.IParticipant#getDescription()
   */
  @Override
  public String getDescription()
  {
    return String.format("%s - %s", this.id.getCode(), this.getName());
  }
  
  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#addMatchdayResult(int, org.dos.tournament.result.IResult)
   */
  @Override
  public void addResultOfMatchday(int matchday, IResult result)
  {
    this.result.addResultOfMatchday(matchday, result);
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#getTotalResult()
   */
  @Override
  public int getTotalScore()
  {
    return this.result.getTotalScore();
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#getTotalResult()
   */
  @Override
  public int[] getTotalResult()
  {
    return this.result.getTotalResult();
  }
  
    /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#getTotalResultIdentifiers()
   */
  @Override
  public Collection<Object> getTotalResultIdentifiers()
  {
    return this.result.getTotalResultIdentifiers();
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#getStatus()
   */
  @Override
  public ParticipantStatus getStatus()
  {
    return this.status;
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#setStatus(org.dos.tournament.player.utils.ParticipantStatus)
   */
  @Override
  public void setStatus(ParticipantStatus status)
  {
    if(ParticipantStatus.DISQUALIFIED != this.status)
      this.status = status;
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#activateParticipant()
   */
  @Override
  public boolean activateParticipant()
  {
    this.setStatus(ParticipantStatus.ACTIVE);
    return ParticipantStatus.ACTIVE == this.getStatus();
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#inactivateParticipant()
   */
  @Override
  public boolean inactivateParticipant()
  {
    this.setStatus(ParticipantStatus.INACTIVE);
    return ParticipantStatus.INACTIVE == this.getStatus();
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.IParticipant#disqualifyParticipant()
   */
  @Override
  public boolean disqualifyParticipant()
  {
    this.setStatus(ParticipantStatus.DISQUALIFIED);
    return ParticipantStatus.DISQUALIFIED == this.getStatus();
  }

  @Override
  public Vector<String> getParticipantAsRow(Vector<String> header)
  {
    Vector<String> _retval = new Vector<String>();
    for(int i=0; i < header.size(); ++i)
    {
      _retval.add(this.getElement(header.elementAt(i).toLowerCase()));
    }
    return _retval;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Object o)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  protected String getElement(String id)
  {
    switch(id)
    {
      case "id":      return this.id.getCode();
      case "status":  return this.getStatus().toString();
      default:        return null;
    }
  }
  
  
}
