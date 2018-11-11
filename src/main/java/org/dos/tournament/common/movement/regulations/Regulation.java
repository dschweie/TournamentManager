package org.dos.tournament.common.movement.regulations;

import java.util.Vector;

public interface Regulation<T, G, P> 
{
  static final public char FLAG_NEVER_MET       = ' ';
  static final public char FLAG_WERE_TEAMMATES  = 'T';
  static final public char FLAG_WERE_OPPONENTS  = 'O';
  static final public char FLAG_INVALID_PAIR    = 'X';
  
  public boolean isValid(int[] pointer, Vector<G> grid, Vector<P> participants);

  public void init(T tournament, int round, Vector<P> participants);
  public void teardown();
  
  public boolean suspend();
  public boolean suspendAll();
  
  public boolean putIntoEffect();
  public boolean putIntoEffectAll();
  
  public boolean isEffective();
  public boolean isSuspendable();
}
