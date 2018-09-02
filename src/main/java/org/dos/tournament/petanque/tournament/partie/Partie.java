package org.dos.tournament.petanque.tournament.partie;

import java.util.Iterator;
import java.util.Set;

import org.dos.tournament.competition.AbstractEncounter;
import org.dos.tournament.competition.DefaultCompetitorResult;
import org.dos.tournament.player.AbstractTeamParticipant;
import org.dos.tournament.player.IParticipant;

public class Partie extends AbstractEncounter
{

  /* (non-Javadoc)
   * @see org.dos.tournament.competition.AbstractEncounter#addParticipant(org.dos.tournament.player.IParticipant)
   */
  @Override
  public boolean addParticipant(IParticipant competitor)
  {
    return (this.getCompetitors().size() < 2)?super.addParticipant(competitor):false;
  }

  @Override
  public boolean isComplete()
  {
    Iterator<IParticipant> _it = this.getCompetitors().keySet().iterator();
    boolean _retval = 2==this.getCompetitors().size();
    
    while(_it.hasNext())
      _retval &= null != this.getCompetitors().get(_it.next());
    
    return _retval;    
  }


  @Override
  public IParticipant getRank(int i)
  {
    IParticipant _retval = null;
    
    if(this.isComplete())
    {
      IParticipant[] _competitors = {};
      _competitors = this.getCompetitors().keySet().toArray(_competitors);
      
      switch(i)
      {
        case 1 :  _retval =  0 < (this.getCompetitors().get(_competitors[0])).compareTo(this.getCompetitors().get(_competitors[1])) ? _competitors[0] :  _competitors[1];
                  break;
        case 2 :  _retval =  0 > (this.getCompetitors().get(_competitors[0])).compareTo(this.getCompetitors().get(_competitors[1])) ? _competitors[0] :  _competitors[1];
                  break;
      }
    }    
    return _retval;
  }
  
  public CompetitorPartieResult getCompetitorResult(IParticipant competitor)
  {
    CompetitorPartieResult _retval = null;
    
    if(this.getCompetitors().containsKey(competitor))
    {
      IParticipant[] _competitors = {};
      _competitors = this.getCompetitors().keySet().toArray(_competitors);
      
      int _idxCompetitor  = competitor.equals(_competitors[0])?0:1;
      int _idxOpponent    = competitor.equals(_competitors[0])?1:0;
      
      _retval = new CompetitorPartieResult( this.getCompetitors().get(_competitors[_idxCompetitor]).getValue()  ,
                                            this.getCompetitors().get(_competitors[_idxOpponent]).getValue()    ,
                                            _competitors[_idxOpponent]                                            );
    }
    
    return _retval;
  }

  @Override
  public boolean setResult(IParticipant competitor, DefaultCompetitorResult result)
  {
    boolean _retval = false;
    
    if(this.getCompetitors().containsKey(competitor))
    {
      _retval = ( null != this.getCompetitors().put(competitor, result) );
    }
    
    return _retval;
  }
  
  public boolean wereOpponents(IParticipant first, IParticipant second)
  {
    AbstractTeamParticipant[] _teams = {};
    _teams = this.getCompetitors().keySet().toArray(_teams);
    
    return (      (_teams[0].contains(first) && _teams[1].contains(second))
              ||  (_teams[1].contains(first) && _teams[0].contains(second)) );
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
      IParticipant[] _competitors = {};
      _competitors = this.getCompetitors().keySet().toArray(_competitors);
      
      switch ( _competitors.length )
      {
        case 1:   _retval = _retval.concat(_competitors[0].getCode())
                                  .concat(" -  ?   ")
                                  .concat(null==this.getCompetitors().get(_competitors[0])?"__":String.format("%2d", this.getCompetitors().get(_competitors[0]).getValue()))
                                  .concat(":__");
                  break;
        case 2:   _retval = _retval.concat(_competitors[0].getCode())
                                  .concat(" - ")
                                  .concat(_competitors[1].getCode())
                                  .concat("  ")
                                  .concat(null==this.getCompetitors().get(_competitors[0])?"__":String.format("%2d", this.getCompetitors().get(_competitors[0]).getValue()))
                                  .concat(":")
                                  .concat(null==this.getCompetitors().get(_competitors[1])?"__":String.format("%2d", this.getCompetitors().get(_competitors[1]).getValue()));
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
      IParticipant[] _competitors = {};
      _competitors = this.getCompetitors().keySet().toArray(_competitors);
      
      switch ( _competitors.length )
      {
        case 1:   _retval = _retval.concat(_competitors[0].getDescription())
                                  .concat(" -  ?   ")
                                  .concat(null==this.getCompetitors().get(_competitors[0])?"__":String.format("%2d", this.getCompetitors().get(_competitors[0]).getValue()))
                                  .concat(":__");
                  break;
        case 2:   _retval = _retval.concat(_competitors[0].getDescription())
                                  .concat(" - ")
                                  .concat(_competitors[1].getDescription())
                                  .concat("  ")
                                  .concat(null==this.getCompetitors().get(_competitors[0])?"__":String.format("%2d", this.getCompetitors().get(_competitors[0]).getValue()))
                                  .concat(":")
                                  .concat(null==this.getCompetitors().get(_competitors[1])?"__":String.format("%2d", this.getCompetitors().get(_competitors[1]).getValue()));
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
    if((-1 < index) && (this.getCompetitors().size() > index))
      return (IParticipant) this.getCompetitors().keySet().toArray()[index];
    else
      return null;
  }

  
}
