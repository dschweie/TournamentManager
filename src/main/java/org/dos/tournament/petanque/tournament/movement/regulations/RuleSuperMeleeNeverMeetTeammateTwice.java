package org.dos.tournament.petanque.tournament.movement.regulations;

import java.util.Vector;

import org.dos.tournament.movement.regulations.Regulation;
import org.dos.tournament.player.IParticipant;

public class RuleSuperMeleeNeverMeetTeammateTwice extends RuleSuperMeleeNeverMeetOpponentTwice {

  public RuleSuperMeleeNeverMeetTeammateTwice(Regulation innerRegulation, boolean effective, boolean suspendable) 
  {
    super(innerRegulation, effective, suspendable);
  }

  protected boolean performCheck(int[] pointer, Vector<Vector<Vector<Integer>>> grid, Vector<IParticipant> participants) 
  {
    boolean _valid = true;
    
    for(int _iTeam=0; _valid && (_iTeam <= pointer[1]); ++_iTeam)
    {
      for(int _iSlot=0; _valid && (_iSlot < (_iTeam<pointer[1]?grid.get(pointer[0]).get(_iTeam).size():pointer[2])); ++_iSlot)
        _valid &= Regulation.FLAG_WERE_TEAMMATES != this.aiParticipantTable[grid.get(pointer[0]).get(_iTeam).get(_iSlot).intValue()][grid.get(pointer[0]).get(pointer[1]).get(pointer[2]).intValue()];
    }
    
    return _valid;
  }

  protected String getRuleDescription() {
    return "Ein Spieler soll maximal einmal mit einem anderen Spieler spielen.";
  }
  
}
