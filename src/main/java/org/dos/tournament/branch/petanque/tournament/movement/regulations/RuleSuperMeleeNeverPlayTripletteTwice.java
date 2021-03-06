/**
 *
 */
package org.dos.tournament.branch.petanque.tournament.movement.regulations;

import java.util.ArrayList;

import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.competition.AbstractTournament.Slot;
import org.dos.tournament.common.movement.regulations.AbstractRegulationDecorator;
import org.dos.tournament.common.movement.regulations.Regulation;
import org.dos.tournament.common.player.IParticipant;

/**
 * @author dos
 *
 */
public class RuleSuperMeleeNeverPlayTripletteTwice extends AbstractRegulationDecorator<SuperMelee, ArrayList<ArrayList<Slot>>, IParticipant>
{

  protected boolean[] abTriplettePlayed;


  public RuleSuperMeleeNeverPlayTripletteTwice(Regulation<SuperMelee, ArrayList<ArrayList<Slot>>, IParticipant> innerRegulation, boolean effective, boolean suspendable) {
    super(innerRegulation, effective, suspendable);
  }

  @Override
  protected boolean performCheck(int[] pointer, ArrayList<ArrayList<ArrayList<Slot>>> grid, ArrayList<IParticipant> participants)
  {
    return (3 == grid.get(pointer[0]).get(pointer[1]).size())?(!this.abTriplettePlayed[grid.get(pointer[0]).get(pointer[1]).get(pointer[2]).getNumber().intValue()]):true;
  }

  @Override
  protected void performInit(SuperMelee tournament, int round, ArrayList<IParticipant> participants)
  {
    this.initTriplettePlayed(participants.size(), false);

    for(int md=0; md < round; ++md)
    {
      Matchday _matchday = tournament.getMatchday(md);

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
  }

  protected void initTriplettePlayed(int size, boolean defaultValue)
  {
    this.abTriplettePlayed = new boolean[size];
    for(int i=0; i<size; ++i)
      this.abTriplettePlayed[i] = defaultValue;
  }


  @Override
  protected void performTeardown() {


  }

  protected String getRuleDescription() {
    return "Ein Spieler soll maximal einmal im Triplette spielen.";
  }
}
