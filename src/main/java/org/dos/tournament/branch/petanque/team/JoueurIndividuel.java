package org.dos.tournament.branch.petanque.team;

import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import org.bson.Document;
import org.dos.tournament.branch.petanque.result.PetanqueMatchResult;
import org.dos.tournament.common.player.AssociationAttendee;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.result.AbstractTotalResult;
import org.dos.tournament.common.result.IResult;

public class JoueurIndividuel extends AssociationAttendee
{
  private String strSurname = "";
  
  public JoueurIndividuel(int id, String name, String association)
  {
    super(id, name, association);
    this.result = new PetanqueTotalResult();
  }
  
  public JoueurIndividuel(int id, String name, String surname, String association)
  {
    super(id, name, association);
    this.strSurname = surname;
    this.result = new PetanqueTotalResult();
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



  private class PetanqueTotalResult extends AbstractTotalResult
  {
    public final static int POINTS      = 0;
    public final static int DIFFERENCE  = 1;
    public final static int WINS        = 2;
    public final static int MATCHES     = 3;
    
    @Override
    public int getTotalScore()
    {
      Iterator<IResult> it = this.mapResults.values().iterator();
      int _retval = 0;
      while(it.hasNext())
        _retval += ((PetanqueMatchResult)it.next()).getMatchScore();
      return _retval;
    }
    
    /* (non-Javadoc)
     * @see org.dos.tournament.result.ITotalResult#getTotalResult()
     */
    @Override
    public int[] getTotalResult()
    {
      int[] _retval = { this.getTotalScore(), this.getTotalDifference(), this.countWins(), this.countMatches() };
      return _retval;
    }
    
    @Override
    public Collection<Object> getTotalResultIdentifiers()
    {
      Collection<Object> _retval = new Vector<Object>();
      _retval.add(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueTotalResult.points.text"));
      _retval.add(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueTotalResult.difference.text"));
      _retval.add(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueTotalResult.wins.text"));
      _retval.add(ResourceBundle.getBundle("org.dos.tournament.resources.messages.messages").getString("PetanqueTotalResult.matches.text"));
      return _retval;
    }



    public int getTotalDifference()
    {
      Iterator<IResult> it = this.mapResults.values().iterator();
      int _retval = 0;
      while(it.hasNext())
        _retval += ((PetanqueMatchResult)it.next()).getDifference();
      return _retval;
    }

    public int countWins()
    {
      Iterator<IResult> it = this.mapResults.values().iterator();
      int _retval = 0;
      while(it.hasNext())
        _retval += (0 < ((PetanqueMatchResult)it.next()).getDifference())?1:0;
      return _retval;
    }


    @Override
    public int getValueForCategory(int category)
    {
      int _retval = -1;
      
      switch(category)
      {
        case PetanqueTotalResult.POINTS :     _retval = this.getTotalScore();
                                              break;
        case PetanqueTotalResult.DIFFERENCE : _retval = this.getTotalDifference();
                                              break;
        case PetanqueTotalResult.WINS :       _retval = this.countWins();
                                              break;
        case PetanqueTotalResult.MATCHES :    _retval = this.countMatches();
                                              break;
      }
      
      return _retval;
    }
    
    public String getName()
    {
      return JoueurIndividuel.this.getName();
    }

    @Override
    public int compareTo(Object o)
    {
      return this.compareTo((PetanqueTotalResult) o, 0);
    }

    private int compareTo(PetanqueTotalResult opps, int category)
    {
      switch(category)
      {
        case PetanqueTotalResult.POINTS :     
        case PetanqueTotalResult.DIFFERENCE : 
        case PetanqueTotalResult.WINS :       
        case PetanqueTotalResult.MATCHES :    return(this.getValueForCategory(category) == opps.getValueForCategory(category)?this.compareTo(opps, category+1):this.getValueForCategory(category) - opps.getValueForCategory(category));
        default:                              return 0;
      }
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
    String _name = this.getForename().concat(" ").concat(this.getSurname());
    return _name.concat("; ").concat(this.getAssociation());
  }

  
}
