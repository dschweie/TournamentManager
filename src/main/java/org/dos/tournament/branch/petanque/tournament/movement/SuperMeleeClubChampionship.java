package org.dos.tournament.branch.petanque.tournament.movement;

import java.util.ArrayList;
import java.util.Vector;

import org.bson.Document;
import org.dos.tournament.branch.petanque.result.PetanqueSuperMeleeClubChampionshipResult;
import org.dos.tournament.branch.petanque.team.AbstractPetanqueTeam;
import org.dos.tournament.branch.petanque.team.Doublette;
import org.dos.tournament.branch.petanque.team.Triplette;
import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.CoreRuleSuperMeleeAllIndicesUnique;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetOpponentTwice;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverMeetTeammateTwice;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTandem;
import org.dos.tournament.branch.petanque.tournament.movement.regulations.RuleSuperMeleeNeverPlayTripletteTwice;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;
import org.dos.tournament.common.player.IParticipant;
import org.dos.tournament.common.player.utils.NumericParticipantId;
import org.dos.tournament.common.result.ConstantScoreResult;
import org.dos.tournament.common.storage.SingletonStorage;

import com.mongodb.BasicDBList;

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
    
    this.xTrophy = new ConstantScoreResult(3);
    
    this.iWinnerOfTheDay = -1;
  }
  
  public SuperMeleeClubChampionship(Document data) 
  {
    this();
    
    // import participants from database
    ArrayList<?> _participants = (ArrayList<?>) data.get("participants");
    if(null != _participants)
    {
      _participants.forEach(it -> {
        IParticipant _participant = SingletonStorage.getInstance().findParticipantById(((Document)it).getString("_id"));
        _participant.setParticipantId(new NumericParticipantId(((Document)it).getInteger("code", 0)));
        this.addCompetitor(_participant);
      });
    }
    
    // import the matchdays
    ArrayList<Document> _matchdays = (ArrayList<Document>) data.get("matchdays");
    for(Document _currMatchday: _matchdays)
    {
      this.matchdays.add(new Matchday());
      
      ArrayList<Document> _matches = (ArrayList<Document>) _currMatchday.get("matches");
      for(Document _currMatch: _matches)
      {
        IParticipant _home  = null;
        IParticipant _guest = null;
        
        switch(((Document)_currMatch.get("home")).getString("_class"))
        {
          case "org.dos.tournament.branch.petanque.team.Doublette":
                          _home = new Doublette(    new NumericParticipantId(idxTeam++), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("home")).getInteger("pointeur", -1)), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("home")).getInteger("tireur", -1)));
                          break;
          case "org.dos.tournament.branch.petanque.team.Triplette":
                          _home = new Triplette(    new NumericParticipantId(idxTeam++), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("home")).getInteger("pointeur", -1)), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("home")).getInteger("milieu", -1)), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("home")).getInteger("tireur", -1)));
                          break;
          default:
        }
        switch(((Document)_currMatch.get("guest")).getString("_class"))
        {
          case "org.dos.tournament.branch.petanque.team.Doublette":
                          _guest = new Doublette(   new NumericParticipantId(idxTeam++), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("guest")).getInteger("pointeur", -1)), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("guest")).getInteger("tireur", -1)));
                          break;
          case "org.dos.tournament.branch.petanque.team.Triplette":
                          _guest = new Triplette(   new NumericParticipantId(idxTeam++), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("guest")).getInteger("pointeur", -1)), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("guest")).getInteger("milieu", -1)), 
                                                    this.getCompetitorByCode(((Document)_currMatch.get("guest")).getInteger("tireur", -1)));
                          break;
          default:
        }
        
        Partie _currPartie = new Partie(_home, _guest);
        this.matchdays.lastElement().addPartie(_currPartie);
        this.parties.add(_currPartie);
        
        if(null != _currMatch.get("score"))
          this.setResult(   this.matchdays.size()-1, 
                            this.matchdays.lastElement().countMatches()-1, 
                            ((Document)_currMatch.get("score")).getInteger("score", 0), 
                            ((Document)_currMatch.get("score")).getInteger("oppsScore", 0));          
        
      }
    }
    /*
    if(null != _matchdays)
    {
      _matchdays.forEach(itmd -> {
        Matchday _current = new Matchday();
        this.matchdays.add(_current);
        
        ArrayList<Document> _matches = (ArrayList<Document>) ((Document)itmd).get("matches");
        if(null != _matches)
        {
          _matches.forEach(itm -> {
            Document     _cm = (Document)itm;
            IParticipant _home  = null;
            IParticipant _guest = null;
            
            switch(_cm.getString("home._class"))
            {
              case "org.dos.tournament.branch.petanque.team.Doublette":
                              _home = new Doublette(new NumericParticipantId(idxTeam++), this.getCompetitorByCode(_cm.getInteger("home.pointer", -1)), this.getCompetitorByCode(_cm.getInteger("home.tireur", -1)));
              case "org.dos.tournament.branch.petanque.team.Triplette":
                              _home = new Triplette(new NumericParticipantId(idxTeam++), this.getCompetitorByCode(_cm.getInteger("home.pointer", -1)), this.getCompetitorByCode(_cm.getInteger("home.milieu", -1)), this.getCompetitorByCode(_cm.getInteger("home.tireur", -1)));
              default:
            }
            switch(_cm.getString("guest._class"))
            {
              case "org.dos.tournament.branch.petanque.team.Doublette":
                              _guest = new Doublette(new NumericParticipantId(idxTeam++), this.getCompetitorByCode(_cm.getInteger("guest.pointer", -1)), this.getCompetitorByCode(_cm.getInteger("guest.tireur", -1)));
              case "org.dos.tournament.branch.petanque.team.Triplette":
                              _guest = new Triplette(new NumericParticipantId(idxTeam++), this.getCompetitorByCode(_cm.getInteger("guest.pointer", -1)), this.getCompetitorByCode(_cm.getInteger("guest.milieu", -1)), this.getCompetitorByCode(_cm.getInteger("guest.tireur", -1)));
              default:
            }
            Partie _cp = new Partie(_home, _guest);
            _current.addPartie(_cp);
            this.parties.add(_cp);
            
            if(null != _cm.get("score"))
              this.setResult(this.matchdays.indexOf(_current), _current.countMatches()-1, _cm.getInteger("score.score", 0), _cm.getInteger("score.oppsScore", 0));          
            });
        }
      });
    }
    */
    
    
    System.out.println(this.getCompetitors().toString());
  }

  private IParticipant getCompetitorByCode(int code) 
  {
    String _code = String.valueOf(code);
    IParticipant _retval = null;
    
    for(IParticipant it:this.competitors)
    {
      if(_code.equals(it.getCode().trim()))
        _retval = it;
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
    
    // this.updateWinnerOfTheDay();
    
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
