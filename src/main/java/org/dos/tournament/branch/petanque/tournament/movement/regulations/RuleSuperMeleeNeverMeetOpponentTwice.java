/**
 * 
 */
package org.dos.tournament.branch.petanque.tournament.movement.regulations;

import java.util.Vector;

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
public class RuleSuperMeleeNeverMeetOpponentTwice extends AbstractRegulationDecorator<SuperMelee, Vector<Vector<Slot>>, IParticipant> 
{

  protected char[][] aiParticipantTable;

  public RuleSuperMeleeNeverMeetOpponentTwice(Regulation innerRegulation, boolean effective, boolean suspendable) 
  {
    super(innerRegulation, effective, suspendable);
  }

  @Override
  protected boolean performCheck(int[] pointer, Vector<Vector<Vector<Slot>>> grid, Vector<IParticipant> participants) 
  {
    boolean _valid = true;
    
    for(int _iTeam=0; _valid && (_iTeam <= pointer[1]); ++_iTeam)
    {
      for(int _iSlot=0; _valid && (_iSlot < (_iTeam<pointer[1]?grid.get(pointer[0]).get(_iTeam).size():pointer[2])); ++_iSlot)
        _valid &= Regulation.FLAG_WERE_OPPONENTS != this.aiParticipantTable[grid.get(pointer[0]).get(_iTeam).get(_iSlot).getNumber().intValue()][grid.get(pointer[0]).get(pointer[1]).get(pointer[2]).getNumber().intValue()];
    }
    
    return _valid;
  }

  @Override
  protected void performInit(SuperMelee tournament, int round, Vector<IParticipant> participants) 
  {
    this.initParticipantTable(participants.size(), Regulation.FLAG_NEVER_MET);
    
    for(int md=0; md<round; ++md)
    {
      Matchday _matchday = tournament.getMatchday(md);

      for(int _idxPartie=0; _idxPartie < _matchday.countMatches(); ++_idxPartie)
      {
        IParticipant[] _home = _matchday.getMatch(_idxPartie).getCompetitor(0).getAttendeesToArray();
        IParticipant[] _guest = _matchday.getMatch(_idxPartie).getCompetitor(1).getAttendeesToArray();
        
        for(int i=0; i<_home.length; ++i)
        {
          int iCurrentParticipant = participants.indexOf(_home[i]);
          
          if(-1 < iCurrentParticipant)
          {
            for(int h=0; h<_home.length; ++h)
              if(-1 < participants.indexOf(_home[h]))
                this.aiParticipantTable[iCurrentParticipant][participants.indexOf(_home[h])] = i==h?Regulation.FLAG_INVALID_PAIR:Regulation.FLAG_WERE_TEAMMATES;
            for(int g=0; g<_guest.length; ++g)
            { //  mark opps
              int iCurrentOpponent = participants.indexOf(_guest[g]);
              if(-1 < iCurrentOpponent)
              {
                this.aiParticipantTable[iCurrentParticipant][iCurrentOpponent] = Regulation.FLAG_WERE_OPPONENTS;
                this.aiParticipantTable[iCurrentOpponent][iCurrentParticipant] = Regulation.FLAG_WERE_OPPONENTS;
              
                if(0==i)
                  for(int j=0; j<_guest.length; ++j)
                    if(-1 < participants.indexOf(_guest[j]))
                      this.aiParticipantTable[iCurrentOpponent][participants.indexOf(_guest[j])] = g==j?Regulation.FLAG_INVALID_PAIR:Regulation.FLAG_WERE_TEAMMATES;
              }
            }
          }
        }
      }
    }
  }

  @Override
  protected void performTeardown() {
    // TODO Auto-generated method stub
    
  }

  protected void initParticipantTable(int size, char defaultValue)
  {
    this.aiParticipantTable = new char[size][size];
    for(int i=0; i<size; ++i)
      for(int j=0; j<size; ++j)
        this.aiParticipantTable[i][j] = defaultValue;
  }

  protected String getRuleDescription() {
    return "Ein Spieler soll maximal einmal gegen einen anderen Spieler spielen.";
  }
}
