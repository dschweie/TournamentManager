package org.dos.tournament.common.movement.regulations;

public abstract class AbstractCoreRule<T, G, P> implements Regulation<T, G, P> 
{
  @Override
  public void teardown() 
  {
    // Diese Methode ist leer, da nichts zu tun ist.
  }

  /**
   *  \brief    Methode zur Deaktivierung einer Regel
   *  
   *  \return   Die Methode liefert immer den Wert <code>false</code>, da
   *            diese elementare Regel nicht deaktiviert werden darf.
   */
  @Override
  public boolean suspend() 
  {
    return false;
  }

  /**
   *  \brief    Methode zur Deaktivierung aller Regeln die außer Kraft gesetzt werden dürfen
   *  
   *  \return   Die Methode liefert immer den Wert <code>false</code>, da
   *            diese elementare Regel nicht deaktiviert werden darf.
   */
  @Override
  public boolean suspendAll() 
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean putIntoEffect() 
  {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean putIntoEffectAll() 
  {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isEffective()
  {
    return true;
  }
  
  public boolean isSuspendable()
  {
    return false;
  }
}
