package org.dos.tournament.common.movement.regulations;

import java.util.ArrayList;

public interface Regulation<T, G, P>
{
  public static final char FLAG_NEVER_MET       = ' ';
  public static final char FLAG_WERE_TEAMMATES  = 'T';
  public static final char FLAG_WERE_OPPONENTS  = 'O';
  public static final char FLAG_INVALID_PAIR    = 'X';

  public boolean isValid(int[] pointer, ArrayList<G> grid, ArrayList<P> participants);

  public void init(T tournament, int round, ArrayList<P> participants);
  public void teardown();

  public boolean suspend();
  public boolean suspendAll();

  public boolean putIntoEffect();
  public boolean putIntoEffectAll();

  public boolean isEffective();
  public boolean isSuspendable();
}
