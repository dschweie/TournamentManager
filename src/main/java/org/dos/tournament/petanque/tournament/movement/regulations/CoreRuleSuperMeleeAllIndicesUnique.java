package org.dos.tournament.petanque.tournament.movement.regulations;

import java.util.Vector;

import org.dos.tournament.movement.regulations.AbstractCoreRule;
import org.dos.tournament.movement.regulations.Regulation;
import org.dos.tournament.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.player.IParticipant;

/**
 *  \brief      Diese Klasse ist Basisklasse im Regelwerk
 *  
 *  Diese Basisklasse stellt sicher, dass ein Teilnehmer nur genau einmal in
 *  der Runde eingeplant wird.
 *  
 *  @author dschweie
 *
 */
public class CoreRuleSuperMeleeAllIndicesUnique extends AbstractCoreRule<SuperMelee, Vector<Vector<Integer>>, IParticipant>
{
  @Override
  public boolean isValid(int[] pointer, Vector<Vector<Vector<Integer>>> grid, Vector<IParticipant> participants) 
  {
    int _idxParticipant = grid.get(pointer[0]).get(pointer[1]).get(pointer[2]).intValue();
    boolean _retval = false;
    for(int i=0; ( !_retval ) && ( i<=pointer[0] ); ++i)
      for(int j=0; ( !_retval ) && ( j <= ((i==pointer[0])?pointer[1]:grid.get(i).size()-1) ); ++j)
        for(int k=0; ( !_retval ) && ( k < (((i==pointer[0])&&(j==pointer[1]))?pointer[2]:grid.get(i).get(j).size()) ); ++k)
          _retval |= ( _idxParticipant == grid.get(i).get(j).get(k).intValue() );
    return !_retval;
  }

  @Override
  public void init(SuperMelee tournament, int round, Vector<IParticipant> participants) 
  {
    // Diese Methode ist leer, da nichts zu tun ist.
  }
  
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return "Regel: Kein Spieler darf mehrfach geteilt sein (fest)";
  }

  
}
