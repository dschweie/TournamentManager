package org.dos.tournament.branch.petanque.tournament.partie;

import java.util.Iterator;
import java.util.Set;

import org.bson.Document;
import org.dos.tournament.branch.petanque.result.PetanqueResult;
import org.dos.tournament.common.competition.AbstractEncounter;
import org.dos.tournament.common.competition.DefaultCompetitorResult;
import org.dos.tournament.common.player.AbstractTeamParticipant;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.result.IResult;

public class Partie extends AbstractEncounter
{

  public Partie()
  {
    super();
  }
  
  public Partie(IParticipant home, IParticipant guest)
  {
    super();
    this.addParticipant(home);
    this.addParticipant(guest);
  }

  @Override
  public boolean isComplete()
  {
    return 2==this.competitors.size();
  }


  @Override
  public IParticipant getRank(int i)
  {
    IParticipant _retval = null;
    
    if(null != this.result)
    {
      // TODO Prüfung implementieren
    }
    
    return _retval;
  }
  
  public boolean wereOpponents(IParticipant first, IParticipant second)
  {
    Object[] _teams =  this.competitors.toArray();
   
    return (      (((AbstractTeamParticipant)_teams[0]).contains(first) && ((AbstractTeamParticipant)_teams[1]).contains(second))
              ||  (((AbstractTeamParticipant)_teams[1]).contains(first) && ((AbstractTeamParticipant)_teams[0]).contains(second)) );
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    String _retval = "";
    
    if(0 < this.getCompetitors().size())
    {
      IParticipant[] _competitors = (IParticipant[]) this.competitors.toArray();
      
      switch ( _competitors.length )
      {
        case 1:   _retval = _retval.concat(_competitors[0].getCode())
                                  .concat(" -  ?   ")
                                  .concat(this.isScored()?String.format("%2d", ((PetanqueResult)this.result).getScore()):"__")
                                  .concat(":__");
                  break;
        case 2:   _retval = _retval.concat(_competitors[0].getCode())
                                  .concat(" - ")
                                  .concat(_competitors[1].getCode())
                                  .concat("  ")
                                  .concat(this.isScored()?String.format("%2d", ((PetanqueResult)this.result).getScore()):"__")
                                  .concat(":")
                                  .concat(this.isScored()?String.format("%2d", ((PetanqueResult)this.result).getOppsScore()):"__");
                  break;
        default:  _retval = _retval.concat(" ?  -  ?   __:__");      
      }
    }
    else 
      _retval = _retval.concat(" ?  -  ?   __:__");
    
    return _retval;
  }

  public String toStringWithNames()
  {
    String _retval = "";
    
    if(0 < this.getCompetitors().size())
    {
      IParticipant[] _competitors = (IParticipant[]) this.competitors.toArray();
      
      switch ( _competitors.length )
      {
        case 1:   _retval = _retval.concat(_competitors[0].getDescription())
                                  .concat(" -  ?   ")
                                  .concat(this.isScored()?String.format("%2d", ((PetanqueResult)this.result).getScore()):"__")
                                  .concat(":__");
                  break;
        case 2:   _retval = _retval.concat(_competitors[0].getDescription())
                                  .concat(" - ")
                                  .concat(_competitors[1].getDescription())
                                  .concat("  ")
                                  .concat(this.isScored()?String.format("%2d", ((PetanqueResult)this.result).getScore()):"__")
                                  .concat(":")
                                  .concat(this.isScored()?String.format("%2d", ((PetanqueResult)this.result).getOppsScore()):"__");
                  break;
        default:  _retval = _retval.concat(" ?  -  ?   __:__");      
      }
    }
    else 
      _retval = _retval.concat(" ?  -  ?   __:__");
    
    return _retval;
  }

  public IParticipant getCompetitor(int index)
  {
    return this.competitors.get(index);
  }


  @Override
  public IResult getResult()
  {
    return this.result;
  }


  @Override
  public IResult getCompetitorResult(IParticipant competitor)
  {
    if(this.isScored())
    {
      if(this.competitors.get(0).equals(competitor) || this.competitors.get(0).contains(competitor))
        return new PetanqueResult(((PetanqueResult)this.result).getScore(), ((PetanqueResult)this.result).getOppsScore());
      if(this.competitors.get(1).equals(competitor) || this.competitors.get(1).contains(competitor))
        return new PetanqueResult(((PetanqueResult)this.result).getOppsScore(), ((PetanqueResult)this.result).getScore());
    }
    return null;
  }


  public boolean playedInTriplette(IParticipant participant)
  {
    Object[] _teams =  this.competitors.toArray();
    return (      ( 3==((AbstractTeamParticipant)_teams[0]).countAttendees()?((AbstractTeamParticipant)_teams[0]).contains(participant):false)
              ||  ( 3==((AbstractTeamParticipant)_teams[1]).countAttendees()?((AbstractTeamParticipant)_teams[1]).contains(participant):false)  );
  }


  public String getId()
  {
    // TODO Auto-generated method stub
    return "xyz";
  }
  
  public Document toBsonDocument()
  {
    Document _retval = new Document();
    _retval.append("home", this.competitors.get(0).toBsonDocument()).append("guest", this.competitors.get(1).toBsonDocument());
    if(this.isScored())
      _retval.append("score", this.result.toBsonDocument());
      
    return _retval;
  }

  public int getHomeScore() 
  {
    return ((PetanqueResult)this.result).getScore();
  }

  public int getGuestScore() 
  {
    // TODO Auto-generated method stub
    return ((PetanqueResult)this.result).getOppsScore();
  }
}
