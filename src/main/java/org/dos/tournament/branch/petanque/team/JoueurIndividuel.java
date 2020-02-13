package org.dos.tournament.branch.petanque.team;

import org.bson.Document;
import org.dos.tournament.branch.petanque.result.PetanqueTotalResult;
import org.dos.tournament.common.player.AssociationAttendee;
import org.dos.tournament.common.player.IParticipant;

public class JoueurIndividuel extends AssociationAttendee
{
  private String strSurname = "";

  public JoueurIndividuel(int id, String name, String association)
  {
    super(id, name, association);
    this.result = new JoueurIndividuelTotalResult();
  }

  public JoueurIndividuel(int id, String name, String surname, String association)
  {
    super(id, name, association);
    this.strSurname = surname;
    this.result = new JoueurIndividuelTotalResult();
  }


  public JoueurIndividuel(Document document)
  {
    this(0, ((Document) document.get("name")).get("forename").toString(), ((Document) document.get("name")).get("surname").toString(), document.get("association").toString());
    for( String _it : document.keySet())
      switch(_it)
      {
        case "_class":
        case "association":
        case "name":
        case "name.forname":
        case "name.surname":  /* NOTHING TO DO */ break;
        default:              this.attributes.put(_it, document.getString(_it));
      }
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.player.AbstractParticipant#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Object o)
  {
    int _iResult = this.compareToByResult((IParticipant) o, true);
    if(0 == _iResult)
      _iResult = (-1)*this.getName().compareToIgnoreCase(((IParticipant)o).getName());

    return _iResult;
  }

  /* (non-Javadoc)
   * @see org.dos.tournament.common.player.AbstractParticipant#compareToByResult(org.dos.tournament.common.player.IParticipant, boolean)
   */
  @Override
  public int compareToByResult(IParticipant opponent, boolean includeTiebreaker)
  {
    if( true == includeTiebreaker )
      return this.result.compareTo(((JoueurIndividuel)opponent).result);
    else
      return this.result.getTotalScore() - ((JoueurIndividuel)opponent).result.getTotalScore();
  }

  @Override
  public String getName()
  {
    return super.getName().concat(" ").concat(strSurname).trim();
  }

  public String getForename()
  {
    return super.getName();
  }

  public String getSurname()
  {
    return strSurname;
  }


  /* (non-Javadoc)
   * @see org.dos.tournament.common.player.AssociationAttendee#getElement(java.lang.String)
   */
  @Override
  protected Object getElement(String id)
  {
    switch(id.toLowerCase().trim())
    {
      case "name.surname"   :
      case "surname"        :
      case "name"           : return this.getSurname();
      case "name.forename"  :
      case "forename"       : return this.getForename();
      default               : return super.getElement(id);
    }
  }



  public void setSurname(String value)
  {
    this.strSurname = value.toString();
  }

  public void setForename(String value)
  {
    this.setName(value);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return this.getSurname().concat("; ").concat(this.getForename()).concat("; ").concat(this.getAssociation());
  }


  public class JoueurIndividuelTotalResult extends PetanqueTotalResult
  {
    @Override
    public String getName()
    {
      return JoueurIndividuel.this.getName();
    }
  }

}
