package org.dos.tournament.player;

import org.dos.tournament.player.utils.NumericParticipantId;

public class Attendee extends AbstractParticipant
{
  private String name = null;
  
  public Attendee(int id, String name)
  {
    this.setParticipantId(new NumericParticipantId(id));
    this.name = name.toString();
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.players.AbstractParticipant#getName()
   */
  @Override
  public String getName()
  {
    // TODO Auto-generated method stub
    return this.name.toString();
  }
  
  public void setName(String name)
  {
    this.name = name.toString();
  }

  @Override
  public boolean hasAttendees()
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IParticipant[] getAttendeesToArray()
  {
    // TODO Auto-generated method stub
    return null;
  }
}
