/**
 * 
 */
package org.dos.tournament.branch.petanque.tournament.movement.regulations;

import java.util.Vector;

import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.movement.regulations.Regulation;
import org.dos.tournament.common.player.IParticipant;

/**
 * @author dos
 *
 */
public class RuleSuperMeleeNeverPlayTripletteTandem extends RuleSuperMeleeNeverPlayTripletteTwice 
{

  @Override
  protected void performInit(SuperMelee tournament, int round, Vector<IParticipant> participants) 
  {
    this.initTriplettePlayed(participants.size(), false);
    Matchday _matchday = tournament.getMatchday(tournament.countMatchdays()-1);

    for(int _idxPartie=0; _idxPartie < _matchday.countMatches(); ++_idxPartie)
    {
      IParticipant[] _home = _matchday.getMatch(_idxPartie).getCompetitor(0).getAttendeesToArray();
      IParticipant[] _guest = _matchday.getMatch(_idxPartie).getCompetitor(1).getAttendeesToArray();
      
      if(3==_home.length)
        for(int i=0; i<3; ++i)
          if(-1 < participants.indexOf(_home[i]))
            this.abTriplettePlayed[participants.indexOf(_home[i])] = true;
      if(3==_guest.length)
        for(int i=0; i<3; ++i)
          if(-1 < participants.indexOf(_guest[i]))
            this.abTriplettePlayed[participants.indexOf(_guest[i])] = true;
      
    }
  }

  public RuleSuperMeleeNeverPlayTripletteTandem(Regulation<SuperMelee, Vector<Vector<Integer>>, IParticipant> innerRegulation, boolean effective, boolean suspendable) 
  {
    super(innerRegulation, effective, suspendable);
  }

  protected String getRuleDescription() {
    return "Ein Spieler soll nicht zweimal hintereinander in einem Triplette spielen.";
  }
  
}
