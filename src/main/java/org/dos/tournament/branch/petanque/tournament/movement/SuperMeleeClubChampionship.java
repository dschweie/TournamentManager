package org.dos.tournament.branch.petanque.tournament.movement;

import org.dos.tournament.branch.petanque.result.PetanqueSuperMeleeClubChampionshipResult;
import org.dos.tournament.branch.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.CoreRuleSuperMeleeAllIndicesUnique;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTwice;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTwice;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTwice;
import org.dos.tournament.common.player.IParticipant;

/**
 *  \brief      Diese Klasse bildet das monatliche Super Melee-Turnier der Bouleabteilung von Blau-Gelb Gro�-Gerau ab.
 *  
 *  Die Bouleabteilung bietet einmal im Monat ein Super Melee-Turnier an, das
 *  f�r alle Spieler offen ist.
 *  
 *  F�r die Abrechnung ist besonders, dass jeder Spieler f�r jede Runde 
 *  (Matchday) an der er teilnimmt, einen Punkt bekommt und einen weiteren
 *  f�r jedes Spiel, dass er gewinnt.
 *  
 *  Zus�tzlich gibt es drei Punkte f�r den Tagessieger. 
 *  
 *  Die Wertung der Monatsturniere wird aufaddiert zu einer Jahreswertung.
 *   
 *  @author     dschweie
 *
 */
public class SuperMeleeClubChampionship extends SuperMelee
{
  protected int iWinnerOfTheDay;
  
  public SuperMeleeClubChampionship()
  {
    super();
    
    this.regulations = new CoreRuleSuperMeleeAllIndicesUnique();
    this.regulations = new RuleSuperMeleeNeverMeetTeammateTwice(this.regulations, true, false);
    this.regulations = new RuleSuperMeleeNeverPlayTripletteTandem(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverMeetOpponentTandem(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverPlayTripletteTwice(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverMeetOpponentTwice(this.regulations, true, true);
    
    this.iWinnerOfTheDay = -1;
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
    
    this.updateWinnerOfTheDay();
    
    this.setChanged();
    this.notifyObservers();
    this.clearChanged();
  }

  protected void updateWinnerOfTheDay()
  {
    int _iLeaderIndex = -1;
    
    for(int i=0; i<this.competitors.size(); ++i)
    {
      this.competitors.get(i).unsetWinnerOfTheDayTrophy();
      if(-1 != _iLeaderIndex)
      {
        if(this.competitors.get(i).compareTo(this.competitors.get(_iLeaderIndex))>0)
          _iLeaderIndex = i;
      }
      else
        _iLeaderIndex = i;
    }
    this.competitors.get(_iLeaderIndex).setWinnerOfTheDayTrophy(null);
  }
}
