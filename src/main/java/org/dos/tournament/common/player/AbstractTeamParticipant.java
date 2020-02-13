package org.dos.tournament.common.player;

import java.util.ArrayList;

public abstract class AbstractTeamParticipant extends AbstractParticipant
{
  protected ArrayList<IParticipant> participants;

  /**
   * @return the participants
   */
  protected ArrayList<IParticipant> getParticipants()
  {
    return participants;
  }

  /**
   * @param participants the participants to set
   */
  protected void setParticipants(ArrayList<IParticipant> participants)
  {
    this.participants = participants;
  }

  public AbstractTeamParticipant()
  {
    this.participants = new ArrayList<>();
  }

  public IParticipant[] getAttendeesToArray()
  {
    return this.participants.toArray(new IParticipant[this.participants.size()]);
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
