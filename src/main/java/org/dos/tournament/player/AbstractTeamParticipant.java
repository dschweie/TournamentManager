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
 
  public IParticipant[] getAttendeesToArray()
  {
    return (0<this.participants.size())?(IParticipant[])this.participants.toArray():null;
  }
  
  public boolean contains(IParticipant participant)
  {
    return this.participants.contains(participant);
  }
  
  public int countAttendees()
  {
    int retval = 0;
    for(int i=0; i < this.participants.size(); ++i)
      retval += null!=this.participants.get(i)?1:0;
    return retval;
  }
  
  public boolean hasAttendees()
  {
    return 0 < this.countAttendees();
  }

}
