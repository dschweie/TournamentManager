package org.dos.tournament.petanque.tournament;

import java.util.Collections;
import java.util.Vector;

import org.dos.tournament.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.petanque.team.Doublette;
import org.dos.tournament.petanque.team.Triplette;
import org.dos.tournament.petanque.tournament.matchday.Matchday;
import org.dos.tournament.petanque.tournament.partie.Partie;
import org.dos.tournament.petanque.tournament.utils.TournamentUtils;
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
                    _home  = new Vector<Integer>(3); _home.add(new Integer(-1)); _home.add(new Integer(-1)); _home.add(new Integer(-1)); 
                    _guest = new Vector<Integer>(3); _guest.add(new Integer(-1)); _guest.add(new Integer(-1)); _guest.add(new Integer(-1));
                    _membersLeft -= 6;
                    break;
          case 1:   //  in diesem Fall spielt ein Triplette gegen ein Doublette
                    _home  = new Vector<Integer>(3); _home.add(new Integer(-1)); _home.add(new Integer(-1)); _home.add(new Integer(-1)); 
                    _guest = new Vector<Integer>(2); _guest.add(new Integer(-1)); _guest.add(new Integer(-1));
                    _membersLeft -= 5;
                    break;
          default:  //  in diesem Fall werden zwei Doublette gebildet
                    _home  = new Vector<Integer>(2); _home.add(new Integer(-1)); _home.add(new Integer(-1));
                    _guest = new Vector<Integer>(2); _guest.add(new Integer(-1)); _guest.add(new Integer(-1));
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
      boolean _valid = true;

      Collections.shuffle(_members);
      
      while( (-1 < _idxPartie) && (_grid.size() > _idxPartie) )
      { 
        while(( -1 < _idxTeam ) && ( _grid.get(_idxPartie).size() > _idxTeam) )
        {
          while (( -1 < _idxSlot ) && ( _grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ))
          {
            while(( -1 < _idxSlot ) &&  ( _grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ) && _grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() < _members.size() )
            {
              _grid.get(_idxPartie).get(_idxTeam).set(_idxSlot, new Integer(_grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() + 1) );
              
              _valid = _grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() < _members.size();
              
              if(_valid)
              { // prüfe, ob Spieler passt
                _valid &= !this.checkMemberInGrid(_grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue(), _idxPartie, _idxTeam, _idxSlot, _grid);
                for(int i = 0; i < _idxSlot; ++i)
                {
                  _valid &= !this.wereTeammates(_members.get(_grid.get(_idxPartie).get(_idxTeam).get(i).intValue()), _members.get(_grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue()));
                }
              }
                
              if(_valid)
              {
                ++_idxSlot;
              }
            }

            if(( -1 < _idxSlot ) &&  ( _grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ))
            {
              if(_grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() >= _members.size())
              { // Backtracking: Spieler zurücksetzen und Platz zurück gehen
                _grid.get(_idxPartie).get(_idxTeam).set(_idxSlot, new Integer(-1) );
                --_idxSlot;
              }
            }
          }
          
          if(-1 == _idxSlot)
          { // 
            --_idxTeam;
            if(-1 < _idxTeam)
              _idxSlot = _grid.get(_idxPartie).get(_idxTeam).size() - 1;
          }
          else
          {
            ++_idxTeam;
            _idxSlot=0;
          }
          
        }
        
        if(-1 == _idxTeam)
        {
          --_idxPartie;
          if(-1 < _idxPartie)
          {
            _idxTeam = _grid.get(_idxPartie).size() - 1;
            _idxSlot = _grid.get(_idxPartie).get(_idxTeam).size() - 1;
          }
        }
        else
        {
          ++_idxPartie;
          _idxTeam = 0;
        }
        
      }
      
      if(0 < _idxPartie)
      {
        //  Partien bilden
        Matchday _matchday = new Matchday();
        for(int p = 0; p < _grid.size(); ++p)
        {
          Partie _partie = new Partie();
          
          for(int t=0; t < _grid.get(p).size(); ++t)
          {
            if( 3 == _grid.get(p).get(t).size())
            {
              Triplette _tt = new Triplette(new NumericParticipantId(idxTeam++), _members.get(_grid.get(p).get(t).get(0).intValue()), _members.get(_grid.get(p).get(t).get(1).intValue()), _members.get(_grid.get(p).get(t).get(2).intValue()));
              _partie.addParticipant(_tt);
              this.addTeam(_tt);
            }
            else
            {
              Doublette _td = new Doublette(new NumericParticipantId(idxTeam++), _members.get(_grid.get(p).get(t).get(0).intValue()), _members.get(_grid.get(p).get(t).get(1).intValue()));
              _partie.addParticipant(_td);
              this.addTeam(_td);
            }
          }
          
          _matchday.addPartie(_partie);
        }
        
        this.getMatchdays().addElement(_matchday);
        _retval = - 1 < _idxPartie;   //  Flag wird gesetzt, da nächste Runde erstellt ist
      }
    }
    

    return _retval;
  }

  private boolean checkMemberInGrid(int intValue, int idxPartie, int idxTeam, int idxSlot, Vector<Vector<Vector<Integer>>> grid)
  {
    boolean _retval = false;
    for(int i=0; i<=idxPartie; ++i)
      for(int j=0; j <= ((i==idxPartie)?idxTeam:grid.get(i).size()-1); ++j)
        for(int k=0; k < (((i==idxPartie)&&(j==idxTeam))?idxSlot:grid.get(i).get(j).size()); ++k)
          _retval |= ( intValue == grid.get(i).get(j).get(k).intValue() );
    return _retval;
  }


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
