/**
 * 
 */
package org.dos.tournament.petanque.tournament.movement.regulations;

import java.util.Vector;

import org.dos.tournament.movement.regulations.Regulation;
import org.dos.tournament.petanque.tournament.matchday.Matchday;
import org.dos.tournament.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.player.IParticipant;

/**
 * @author dos
 *
 */
public class RuleSuperMeleeNeverMeetOpponentTandem extends RuleSuperMeleeNeverMeetOpponentTwice 
{

  public RuleSuperMeleeNeverMeetOpponentTandem(Regulation innerRegulation, boolean effective, boolean suspendable) 
  {
    super(innerRegulation, effective, suspendable);
  }

  protected void performInit(SuperMelee tournament, int round, Vector<IParticipant> participants) 
  {
    this.initParticipantTable(participants.size(), Regulation.FLAG_NEVER_MET);
    Matchday _matchday = tournament.getMatchday(tournament.countMatchdays()-1);

    for(int _idxPartie=0; _idxPartie < _matchday.countMatches(); ++_idxPartie)
    {
      IParticipant[] _home = _matchday.getMatch(_idxPartie).getCompetitor(0).getAttendeesToArray();
      IParticipant[] _guest = _matchday.getMatch(_idxPartie).getCompetitor(1).getAttendeesToArray();
      
      for(int i=0; i<_home.length; ++i)
      {
        int iCurrentParticipant = participants.indexOf(_home[i]);
        
        for(int h=0; h<_home.length; ++h)
          this.aiParticipantTable[iCurrentParticipant][participants.indexOf(_home[h])] = i==h?Regulation.FLAG_INVALID_PAIR:Regulation.FLAG_WERE_TEAMMATES;
        for(int g=0; g<_guest.length; ++g)
        { //  mark opps
          int iCurrentOpponent = participants.indexOf(_guest[g]);
          this.aiParticipantTable[iCurrentParticipant][iCurrentOpponent] = Regulation.FLAG_WERE_OPPONENTS;
          this.aiParticipantTable[iCurrentOpponent][iCurrentParticipant] = Regulation.FLAG_WERE_OPPONENTS;
          
          if(0==i)
            for(int j=0; j<_guest.length; ++j)
              this.aiParticipantTable[iCurrentOpponent][participants.indexOf(_guest[j])] = g==j?Regulation.FLAG_INVALID_PAIR:Regulation.FLAG_WERE_TEAMMATES;
        }
      }
    }
  }


  protected String getRuleDescription() {
    return "Ein Spieler soll nicht zweimal hintereinander gegen den gleichen Spieler spielen.";
  }
}
