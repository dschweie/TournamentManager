package org.dos.tournament.petanque.tournament.movement;

import java.util.Collections;
import java.util.Vector;

import org.dos.tournament.petanque.result.PetanqueSuperMeleeClubChampionshipResult;
import org.dos.tournament.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.petanque.team.Doublette;
import org.dos.tournament.petanque.team.JoueurIndividuel;
import org.dos.tournament.petanque.team.Triplette;
import org.dos.tournament.petanque.tournament.matchday.Matchday;
import org.dos.tournament.petanque.tournament.partie.Partie;
import org.dos.tournament.petanque.tournament.utils.TournamentUtils;
import org.dos.tournament.player.IParticipant;
import org.dos.tournament.player.utils.NumericParticipantId;
import org.dos.tournament.player.utils.ParticipantStatus;

/**
 *  \brief      Diese Klasse bildet das monatliche Super Melee-Turnier der Bouleabteilung von Blau-Gelb Groß-Gerau ab.
 *  
 *  Die Bouleabteilung bietet einmal im Monat ein Super Melee-Turnier an, das
 *  für alle Spieler offen ist.
 *  
 *  Für die Abrechnung ist besonders, dass jeder Spieler für jede Runde 
 *  (Matchday) an der er teilnimmt, einen Punkt bekommt und einen weiteren
 *  für jedes Spiel, dass er gewinnt.
 *  
 *  Zusätzlich gibt es drei Punkte für den Tagessieger. 
 *  
 *  Die Wertung der Monatsturniere wird aufaddiert zu einer Jahreswertung.
 *   
 *  @author     dschweie
 *
 */
public class SuperMeleeClubChampionship extends SuperMelee
{
  private int idxTeam = 0;
  
  /**
   *  \brief    Die Methode erzeugt einen neuen "Spieltag" mit den aktiven Teilnehmern.
   *  
   *  
   *  @return   Die Methode liefert im Rückgabewert die Information, ob ein 
   *            neuer Spieltag angelegt werden konnte.
   */
  protected boolean generateNextMatchdayByAlgorithm()
  {
    int _matchdaysExpected = this.countMatchdays() + 1;
    Vector<IParticipant> _members = TournamentUtils.filterParticipantsByStatus(this.getCompetitors(), ParticipantStatus.ACTIVE);
    Vector<Vector<Vector<Integer>>> _grid = compileGridTemplateForSupermelee(_members.size());
    
    if(null != _grid)
    {
      Collections.shuffle(_members);
      _grid = fillGridWithParticipants(_grid, _members);
      
      if(null != _grid)
        compileNextMatchDayFromGrid(_grid, _members);
    }

    return (_matchdaysExpected == this.countMatchdays());
  }

  private void compileNextMatchDayFromGrid(Vector<Vector<Vector<Integer>>> grid, Vector<IParticipant> participants)
  {
    if((null != grid) && (null != participants))
    {
      //  Partien bilden
      Matchday _matchday = new Matchday();
      for(int p = 0; p < grid.size(); ++p)
      {
        Partie _partie = new Partie();
        
        for(int t=0; t < grid.get(p).size(); ++t)
        {
          if( 3 == grid.get(p).get(t).size())
          {
            Triplette _tt = new Triplette(new NumericParticipantId(idxTeam++), participants.get(grid.get(p).get(t).get(0).intValue()), participants.get(grid.get(p).get(t).get(1).intValue()), participants.get(grid.get(p).get(t).get(2).intValue()));
            _partie.addParticipant(_tt);
            this.addTeam(_tt);
          }
          else
          {
            Doublette _td = new Doublette(new NumericParticipantId(idxTeam++), participants.get(grid.get(p).get(t).get(0).intValue()), participants.get(grid.get(p).get(t).get(1).intValue()));
            _partie.addParticipant(_td);
            this.addTeam(_td);
          }
        }
        
        _matchday.addPartie(_partie);
        this.addPartie(_partie);
      }
      this.getMatchdays().addElement(_matchday);
    }
  }

  private Vector<Vector<Vector<Integer>>> fillGridWithParticipants(Vector<Vector<Vector<Integer>>> grid,
      Vector<IParticipant> participants)
  {
    //  this counter should help tio avoid to long runnings of this algorithm
    long _stepper = 0;
    // Durchlaufe das Grid, um Plätze zu füllen
    int _idxPartie = 0;
    int _idxTeam = 0;
    int _idxSlot = 0;
    boolean _valid = true;
    
    while( (-1 < _idxPartie) && (grid.size() > _idxPartie) )
    { 
      while(( -1 < _idxTeam ) && ( grid.get(_idxPartie).size() > _idxTeam) )
      {
        while (( -1 < _idxSlot ) && ( grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ))
        {
          while(( -1 < _idxSlot ) &&  ( grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ) && grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() < participants.size() )
          {
            grid.get(_idxPartie).get(_idxTeam).set(_idxSlot, new Integer(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() + 1) );
            
            _valid = grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() < participants.size();
            _valid &= !this.checkMemberInGrid(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue(), _idxPartie, _idxTeam, _idxSlot, grid);

            if(_valid && (3 == grid.get(_idxPartie).get(_idxTeam).size()) && this.isRuleNoTripletteTwiceActive())
              _valid &= !this.alreadyPlayedTriplette(participants.get(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue()));
            
            for(int _iTeam=0; _valid && (_iTeam <= _idxTeam); ++_iTeam)
            {
              for(int _iSlot=0; _valid && (_iSlot < (_iTeam<_idxTeam?grid.get(_idxPartie).get(_iTeam).size():_idxSlot)); ++_iSlot)
              {
                if(this.isRuleNotSamePartnerActive())
                  _valid &= !this.wereTeammates(participants.get(grid.get(_idxPartie).get(_iTeam).get(_iSlot).intValue()), participants.get(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue()));
                if(this.isRuleNotSameOpponentActive())
                  _valid &= !this.wereOpponents(participants.get(grid.get(_idxPartie).get(_iTeam).get(_iSlot).intValue()), participants.get(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue()));
              }
            }

            if(_valid)
            {
              // System.out.println(String.format("%d ; %d ; %d ", _idxPartie, _idxTeam, _idxSlot).concat(String.valueOf(_valid)).concat(" => ").concat(grid.toString()));
              ++_idxSlot;

              //  currently the algorithm should run very long in fifth and following matchday, so it opens the ruleset
              ++_stepper;
              if(10000000 < _stepper)
                this.setRuleNotSameOpponent(false);
            }
          }

          if(( -1 < _idxSlot ) &&  ( grid.get(_idxPartie).get(_idxTeam).size() > _idxSlot ))
          {
            if(grid.get(_idxPartie).get(_idxTeam).get(_idxSlot).intValue() >= participants.size())
            { // Backtracking: Spieler zurücksetzen und Platz zurück gehen
              grid.get(_idxPartie).get(_idxTeam).set(_idxSlot, new Integer(-1) );
              --_idxSlot;
            }
          }
        }
        
        if(-1 == _idxSlot)
        { // 
          --_idxTeam;
          if(-1 < _idxTeam)
            _idxSlot = grid.get(_idxPartie).get(_idxTeam).size() - 1;
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
          _idxTeam = grid.get(_idxPartie).size() - 1;
          _idxSlot = grid.get(_idxPartie).get(_idxTeam).size() - 1;
        }
      }
      else
      {
        ++_idxPartie;
        _idxTeam = 0;
      }
      
    }
    return (-1 == _idxPartie)?null:grid;
  }

  private boolean checkMemberInGrid(int intValue, int idxPartie, int idxTeam, int idxSlot, Vector<Vector<Vector<Integer>>> grid)
  {
    boolean _retval = false;
    for(int i=0; ( !_retval ) && ( i<=idxPartie ); ++i)
      for(int j=0; ( !_retval ) && ( j <= ((i==idxPartie)?idxTeam:grid.get(i).size()-1) ); ++j)
        for(int k=0; ( !_retval ) && ( k < (((i==idxPartie)&&(j==idxTeam))?idxSlot:grid.get(i).get(j).size()) ); ++k)
          _retval |= ( intValue == grid.get(i).get(j).get(k).intValue() );
    return _retval;
  }

  protected boolean generateFirstMatchday()
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
  
  protected Vector<Vector<Vector<Integer>>> compileGridTemplateForSupermelee(int competitors)
  {
    int _membersLeft = competitors;
    Vector<Vector<Vector<Integer>>> _retval = null;
    
    if(     ( 3 <  competitors )
        &&  ( 7 != competitors ) )
    { //  mindestens 4 aktive Teilnehmer werden benötigt, damit eine Runde erstellt werden kann
    
      _retval = new Vector<Vector<Vector<Integer>>>();
      // Bilde das Grid für die Runde
      for(int i=0; ( competitors / 4 ) > i; ++i)
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
        _retval.add(_partie);
      }
    }
    return _retval;
  }
  
  
  public void setResult(int iMatchdayIndex, int iMatchIndex, int iHome, int iGuest)
  {
    PetanqueSuperMeleeClubChampionshipResult _home  = new PetanqueSuperMeleeClubChampionshipResult(iHome, iGuest);
    PetanqueSuperMeleeClubChampionshipResult _guest = new PetanqueSuperMeleeClubChampionshipResult(iGuest, iHome);
    
    this.getMatchday(iMatchdayIndex).getMatch(iMatchIndex).setResult(_home);
    
    IParticipant[] _homeAttendees = ((AbstractPetanqueTeam)this.getMatchday(iMatchdayIndex).getMatch(iMatchIndex).getCompetitor(0)).getAttendeesToArray();
    for(int i=0; i < _homeAttendees.length; ++i)
      _homeAttendees[i].addResultOfMatchday(iMatchdayIndex, _home);

    IParticipant[] _guestAttendees = ((AbstractPetanqueTeam)this.getMatchday(iMatchdayIndex).getMatch(iMatchIndex).getCompetitor(1)).getAttendeesToArray();
    for(int i=0; i < _guestAttendees.length; ++i)
      _guestAttendees[i].addResultOfMatchday(iMatchdayIndex, _guest);
    
    this.setChanged();
    this.notifyObservers();
    this.clearChanged();
  }
}
