package org.dos.tournament.player;

import org.dos.tournament.player.utils.IParticipantId;
import org.dos.tournament.player.utils.ParticipantStatus;

public abstract class AbstractParticipant implements IParticipant
{
  protected IParticipantId id = null;
  protected ParticipantStatus status = ParticipantStatus.ACTIVE;

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

}
