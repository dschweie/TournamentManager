package org.dos.tournament.common.player;

import org.dos.tournament.common.player.utils.NumericParticipantId;

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
    return this.name.toString();
  }

  public void setName(String name)
  {
    this.name = name.toString();
  }

  @Override
  public boolean hasAttendees()
  {
    return false;
  }

  @Override
  public IParticipant[] getAttendeesToArray()
  {
    return null;
  }

  @Override
  public boolean contains(IParticipant competitor)
  {
    return false;
  }

  protected Object getElement(String id)
  {
    switch(id)
    {
      case "name":    return this.getName();
      default:        return super.getElement(id);
    }
  }

  @Override
  public String getDescriptionByCode()
  {
    return this.getCode();
  }

}
