package org.dos.tournament.petanque.tournament.movement;

import org.dos.tournament.petanque.result.PetanqueSuperMeleeClubChampionshipResult;
import org.dos.tournament.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.petanque.tournament.movement.regulations.CoreRuleSuperMeleeAllIndicesUnique;
import org.dos.tournament.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTandem;
import org.dos.tournament.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTwice;
import org.dos.tournament.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTandem;
import org.dos.tournament.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTwice;
import org.dos.tournament.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTandem;
import org.dos.tournament.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTwice;
import org.dos.tournament.player.IParticipant;

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
  public SuperMeleeClubChampionship()
  {
    super();
    
    this.regulations = new CoreRuleSuperMeleeAllIndicesUnique();
    this.regulations = new RuleSuperMeleeNeverMeetTeammateTwice(this.regulations, true, false);
    this.regulations = new RuleSuperMeleeNeverPlayTripletteTandem(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverMeetOpponentTandem(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverPlayTripletteTwice(this.regulations, true, true);
    this.regulations = new RuleSuperMeleeNeverMeetOpponentTwice(this.regulations, true, true);
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
