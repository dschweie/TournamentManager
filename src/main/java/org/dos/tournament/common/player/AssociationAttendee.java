package org.dos.tournament.common.player;

public class AssociationAttendee extends Attendee
{
  private String association = null;

  public AssociationAttendee(int id, String name)
  {
    this(id, name, null);
  }

  public AssociationAttendee(int id, String name, String association)
  {
    super(id, name);
    this.association = association;
  }

  /**
   * @return the association
   */
  public String getAssociation()
  {
    return association;
  }

  /**
   * @param association the association to set
   */
  public void setAssociation(String association)
  {
    this.association = association;
  }

  @Override
  protected String getElement(String id)
  {
    switch(id)
    {
      case "club":
      case "association" :  return this.getAssociation();
      default:              return super.getElement(id);
    }
  }

}
