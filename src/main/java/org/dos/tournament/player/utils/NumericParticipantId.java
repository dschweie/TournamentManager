package org.dos.tournament.player.utils;

public class NumericParticipantId implements IParticipantId
{
  private int id;
  
  public NumericParticipantId(int id)
  {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return String.format("%3d", this.id);
  }

  public String getCode()
  {
    return String.format("%3d", this.id);
  }

  public String getName()
  {
    return String.valueOf(this.id);
  }

  public String getDescription()
  {
    return new String("");
  }
  
  
}
