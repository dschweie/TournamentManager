package org.dos.tournament.petanque.tournament;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import org.dos.tournament.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.petanque.team.Doublette;
import org.dos.tournament.petanque.team.Triplette;
import org.dos.tournament.petanque.tournament.matchday.Matchday;
import org.dos.tournament.petanque.tournament.partie.Partie;
import org.dos.tournament.petanque.tournament.utils.TournamentUtils;
import org.dos.tournament.player.AbstractTeamParticipant;
import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.NumericParticipantId;
import org.dos.tournament.player.utils.ParticipantStatus;

public class SuperMeleeClubChampionship extends SuperMelee
{
  private int idxTeam = 0;
  
  public boolean generateNextMatchday()
  {
    return (0 == this.countMatchdays())?this.generateFirstMatchday():this.generateNextMatchday(0);
  }
  
  private boolean generateNextMatchday(int modus)
  {
    boolean _retval = false;
    AbstractPetanqueTeam[] _doublette = { null, null };
    AbstractPetanqueTeam[] _triplette = { null, null, null };
    Vector<IParticipant> _members = TournamentUtils.filterParticipantsByStatus(this.getCompetitors(), ParticipantStatus.ACTIVE);
    
    if(     ( 3 <  _members.size() )
        &&  ( 7 != _members.size() ) )
    { //  mindestens 4 aktive Teilnehmer werden benötigt, damit eine Runde erstellt werden kann
    
      Vector<Vector<Vector<Integer>>> _grid = new Vector<Vector<Vector<Integer>>>();
      int _membersLeft = _members.size();
      // Bilde das Grid für die Runde
      for(int i=0; ( _members.size() / 4 ) > i; ++i)
      {
        Vector<Integer> _home;
        Vector<Integer> _guest;
        
        switch( _membersLeft % 4 )
        {
          case 3:
          case 2:   //  in diesem Fall spielen zwei Triplette
                    _home  = new Vector<Integer>(3);
                    _guest = new Vector<Integer>(3);
                    _membersLeft -= 6;
                    break;
          case 1:   //  in diesem Fall spielt ein Triplette gegen ein Doublette
                    _home  = new Vector<Integer>(3);
                    _guest = new Vector<Integer>(2);
                    _membersLeft -= 5;
                    break;
          default:  //  in diesem Fall werden zwei Doublette gebildet
                    _home  = new Vector<Integer>(2);
                    _guest = new Vector<Integer>(2);
                    _membersLeft -= 4;
                    break;
        }
          
        Vector<Vector<Integer>> _partie = new Vector<Vector<Integer>>(2);
        _partie.addElement(_home);
        _partie.addElement(_guest);
        _grid.add(_partie);
      }
      
      // Durchlaufe das Grid, um Plätze zu füllen
      int _idxPartie = 0;
      int _idxTeam = 0;
      int _idxSlot = 0;
      IParticipant _marker = null; 
      boolean _valid = true;

      Collections.shuffle(_members);
 //     _marker = _members.firstElement();
      
      while( (-1 < _idxPartie) && (0 < _members.size()) )
      { 
        while(( -1 < _idxTeam ) && ( _grid.get(_idxPartie).size() > _idxTeam) )
        {
          while (( -1 < _idxSlot ) && ( _grid.get(_idxPartie).get(_idxTeam).capacity() > _idxSlot ))
          {
            _valid = true;
            if(null == _grid.get(_idxPartie).get(_idxTeam).get(_idxSlot))
              _grid.get(_idxPartie).get(_idxTeam).add(new Integer(0));
            _valid = this.checkMemberInGrid(_grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue(), _idxPartie, _idxTeam, _idxSlot, _grid);
            
            // increase if already set
            // check for teammate
            for(int i = 0; ((i < _idxSlot) && _valid); ++i)
            {
              _valid &= !this.wereTeammates(_members.get(_grid.get(_idxPartie).get(_idxTeam).get(i).intValue()), _members.get(_grid.get(_idxPartie).get(_idxTeam).get(_idxSlot)));
            }

          }
          
        }
        
      }
      
      if(0 < _idxPartie)
      {
        //  Partien bilden
        Matchday _matchday = new Matchday();
        for(Vector<Vector<IParticipant>> p : _grid)
        {
          Partie _partie = new Partie();
          for(Vector<IParticipant> t : p)
          {
            if( 3 == t.size() )
            {
              Triplette _tt = new Triplette(new NumericParticipantId(idxTeam++), t.remove(0), t.remove(0), t.remove(0));
              _partie.addParticipant(_tt);
              this.addTeam(_tt);
            }
            else
            {
              Doublette _td = new Doublette(new NumericParticipantId(idxTeam++), t.remove(0),t.remove(0));
              _partie.addParticipant(_td);
              this.addTeam(_td);
            }
            this.addPartie(_partie);
          }
          _matchday.addPartie(_partie);
        }
        this.getMatchdays().add(_matchday);
      }
      _retval = 0==_members.size();   //  Flag wird gesetzt, da nächste Runde erstellt ist
    }
    

    return _retval;
  }

private boolean checkMemberInGrid(int intValue, int idxPartie, int idxTeam, int idxSlot, Vector<Vector<Vector<Integer>>> grid)
{
  boolean _retval = false;
  for(int i=0; i<idxPartie; ++i)
    for(int j=0; j < ((i==idxPartie-1)?idxTeam:grid.get(i).size()); ++j)
      for(int k=0; k < (((i==idxPartie-1)&&(j==idxTeam-1))?idxSlot:grid.get(i).get(j).size()); ++k)
        _retval |= ( intValue == grid.get(i).get(j).get(k).intValue() );
  return _retval;
}

//  private boolean generateNextMatchday(int modus)
//  {
//    boolean _retval = false;
//    AbstractPetanqueTeam[] _doublette = { null, null };
//    AbstractPetanqueTeam[] _triplette = { null, null, null };
//    Vector<IParticipant> _members = TournamentUtils.filterParticipantsByStatus(this.getCompetitors(), ParticipantStatus.ACTIVE);
//    
//    if(     ( 3 <  _members.size() )
//        &&  ( 7 != _members.size() ) )
//    { //  mindestens 4 aktive Teilnehmer werden benötigt, damit eine Runde erstellt werden kann
//    
//      Vector<Vector<Vector<IParticipant>>> _grid = new Vector<Vector<Vector<IParticipant>>>();
//      int _membersLeft = _members.size();
//      // Bilde das Grid für die Runde
//      for(int i=0; ( _members.size() / 4 ) > i; ++i)
//      {
//        Vector<IParticipant> _home;
//        Vector<IParticipant> _guest;
//        
//        switch( _membersLeft % 4 )
//        {
//          case 3:
//          case 2:   //  in diesem Fall spielen zwei Triplette
//                    _home  = new Vector<IParticipant>(3);
//                    _guest = new Vector<IParticipant>(3);
//                    _membersLeft -= 6;
//                    break;
//          case 1:   //  in diesem Fall spielt ein Triplette gegen ein Doublette
//                    _home  = new Vector<IParticipant>(3);
//                    _guest = new Vector<IParticipant>(2);
//                    _membersLeft -= 5;
//                    break;
//          default:  //  in diesem Fall werden zwei Doublette gebildet
//                    _home  = new Vector<IParticipant>(2);
//                    _guest = new Vector<IParticipant>(2);
//                    _membersLeft -= 4;
//                    break;
//        }
//          
//        Vector<Vector<IParticipant>> _partie = new Vector<Vector<IParticipant>>(2);
//        _partie.addElement(_home);
//        _partie.addElement(_guest);
////        _partie.set(0, _home);
////        _partie.set(1, _guest);
////        _grid.set(i, _partie);
//        _grid.add(_partie);
//      }
//      
//      // Durchlaufe das Grid, um Plätze zu füllen
//      int _idxPartie = 0;
//      int _idxTeam = 0;
//      int _idxSlot = 0;
//      IParticipant _marker = null; 
//      boolean _valid = true;
//
//      Collections.shuffle(_members);
//      _marker = _members.firstElement();
//      
//      while( (-1 < _idxPartie) && (0 < _members.size()) )
//      { 
//        while(( -1 < _idxTeam ) && ( _grid.get(_idxPartie).size() > _idxTeam) )
//        {
//          while (( -1 < _idxSlot ) && ( _grid.get(_idxPartie).get(_idxTeam).capacity() > _idxSlot ))
//          {
//            _valid = true;
//            for(int i = 0; i < _idxSlot; ++i)
//            {
//              _valid &= !this.wereTeammates(_grid.get(_idxPartie).get(_idxTeam).get(i), _members.get(0));
//              // _valid &= !this.wereOpponents(_grid.get(_idxPartie).get(_idxTeam).get(i), _members.get(0));
//            }
//            /*
//            if( 1 == _idxTeam )
//              for(int i = 0; i < _grid.get(_idxPartie).get(0).size(); ++i)
//              {
//                _valid &= this.wereTeammates(_grid.get(_idxPartie).get(0).get(i), _members.get(0));
//                _valid &= this.wereOpponents(_grid.get(_idxPartie).get(0).get(i), _members.get(0));
//              }
//            */
//            if(_valid)
//            { // Spieler kann gesetzt werden und es wird der nächste Platz bearbeitet
//              _grid.get(_idxPartie).get(_idxTeam).add(_members.remove(0));
//              ++_idxSlot;
//              _marker = (0 < _members.size())?_members.firstElement():null;
//            }
//            else
//            { // Spieler kann nicht gesetzt werden
//              // Spieler wird im Stapel nach hinten gerückt
//              _members.add(_members.remove(0));
//              if(_marker.getParticipantId().getCode().equals( _members.firstElement().getParticipantId().getCode()))
//              { // in diesem Fall gibt es keinen möglichen Spieler in der Liste
//                // für den vorherigen Platz ist ein neuer Spieler zu suchen
//                --_idxSlot;
//                if( -1 < _idxSlot)
//                {
//                  _members.add(_grid.get(_idxPartie).get(_idxTeam).remove(_idxSlot));
//                  _marker = _members.firstElement();
//                }
//              }
//            }
//          }
//          
//          switch(_idxSlot)
//          {
//            case -1:  //  letzten Slot der vorherigen Mannschaft löschen
//                      --_idxTeam;
//                      if( -1 < _idxTeam )
//                      {
//                        _idxSlot = _grid.get(_idxPartie).get(_idxTeam).size()-1;
//                        _members.add(_grid.get(_idxPartie).get(_idxTeam).remove(_idxSlot));
//                        _marker = _members.firstElement();
//                      }
//                      break;
//            default:  //  weiter mit dem nächsten Slot
//                      ++_idxTeam;
//                      _idxSlot = 0;
//                      break;
//          }
//        }
//        
//        switch(_idxTeam)
//        {
//          case -1:  //  letzten Slot der vorherigen Mannschaft löschen
//                    --_idxPartie;
//                    if( -1 < _idxPartie )
//                    {
//                      _idxTeam = _grid.get(_idxPartie).size()-1;
//                      _idxSlot = _grid.get(_idxPartie).get(_idxTeam).size()-1;
//                      _members.add(_grid.get(_idxPartie).get(_idxTeam).remove(_idxSlot));
//                      _marker = _members.firstElement();
//                    }
//                    break;
//          default:  //  weiter mit dem nächsten Slot
//                    ++_idxPartie;
//                    _idxTeam = 0;
//                    _idxSlot = 0;
//                    break;
//        }
//      }
//      
//      if(0 < _idxPartie)
//      {
//        //  Partien bilden
//        Matchday _matchday = new Matchday();
//        for(Vector<Vector<IParticipant>> p : _grid)
//        {
//          Partie _partie = new Partie();
//          for(Vector<IParticipant> t : p)
//          {
//            if( 3 == t.size() )
//            {
//              Triplette _tt = new Triplette(new NumericParticipantId(idxTeam++), t.remove(0), t.remove(0), t.remove(0));
//              _partie.addParticipant(_tt);
//              this.addTeam(_tt);
//            }
//            else
//            {
//              Doublette _td = new Doublette(new NumericParticipantId(idxTeam++), t.remove(0),t.remove(0));
//              _partie.addParticipant(_td);
//              this.addTeam(_td);
//            }
//            this.addPartie(_partie);
//          }
//          _matchday.addPartie(_partie);
//        }
//        this.getMatchdays().add(_matchday);
//      }
//      _retval = 0==_members.size();   //  Flag wird gesetzt, da nächste Runde erstellt ist
//    }
//    
//
//    return _retval;
//  }

  private boolean generateFirstMatchday()
  {
    boolean _retval = false;
    Vector<IParticipant> _members = TournamentUtils.filterParticipantsByStatus(this.getCompetitors(), ParticipantStatus.ACTIVE);

    if(     ( 3 <  _members.size() )
        &&  ( 7 != _members.size() ) )
    { //  mindestens 4 aktive Teilnehmer werden benötigt, damit eine Runde erstellt werden kann
      Collections.shuffle(_members);
      Matchday _matchday = new Matchday();
      Partie _p = null;
      int _idx = 0;
      
      while(0 < _members.size())
      {
        _p = new Partie();
        AbstractPetanqueTeam _home = null;
        AbstractPetanqueTeam _guest = null;
        
        switch( _members.size() % 4 )
        {
          case 3:
          case 2:   //  in diesem Fall spielen zwei Triplette
                    _home   = new Triplette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0), _members.remove(0));
                    _guest  = new Triplette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0), _members.remove(0));
                    break;
          case 1:   //  in diesem Fall spielt ein Triplette gegen ein Doublette
                    _home   = new Triplette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0), _members.remove(0));
                    _guest  = new Doublette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0));
                    break;
          default:  //  in diesem Fall werden zwei Doublette gebildet
                    _home   = new Doublette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0));
                    _guest  = new Doublette(new NumericParticipantId(idxTeam++), _members.remove(0), _members.remove(0));
                    break;
        }

        _p.addParticipant(_home);
        _p.addParticipant(_guest);       
        _matchday.addPartie(_p);
        
        this.addPartie(_p);
        this.addTeam(_home);
        this.addTeam(_guest);
      }
      
      this.getMatchdays().addElement(_matchday);
      
      _retval = true;   //  Flag wird gesetzt, da nächste Runde erstellt ist
    }
    
    
    return _retval;
  }
  
  public String getMatchdayAsString(int number)
  {
    int _idx = number - 1;
    
    if(     ( -1 < _idx                         ) 
        &&  ( this.getMatchdays().size() > _idx ) )
    {
      return this.getMatchdays().get(_idx).toStringWithNames();
    }
    else
      return "";
  }
}
