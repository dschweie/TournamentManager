package org.dos.tournament.player;

import java.util.Vector;

public abstract class AbstractTeamParticipant extends AbstractParticipant
{
  private Vector<IParticipant> participants;
  
  /**
   * @return the participants
   */
  protected Vector<IParticipant> getParticipants()
  {
    return participants;
  }

  /**
   * @param participants the participants to set
   */
  protected void setParticipants(Vector<IParticipant> participants)
  {
    this.participants = participants;
  }

  public AbstractTeamParticipant()
  {
    this.participants = new Vector<IParticipant>();
  }
  
  public Attendee[] getAttendeesToArray()
  {
    return (0<this.participants.size())?(Attendee[])this.participants.toArray():null;
  }
  
  public boolean contains(IParticipant participant)
  {
    return this.participants.contains(participant);
  }
}
