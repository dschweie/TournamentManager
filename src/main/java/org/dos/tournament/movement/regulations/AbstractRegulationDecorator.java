package org.dos.tournament.movement.regulations;

import java.util.Vector;

public abstract class AbstractRegulationDecorator<T,G,P> implements Regulation<T,G,P>
{
  private boolean bEffective = true;
  private boolean bSuspendable = false;
  
  protected Regulation<T, G, P> xInner = null;
  
  /**
   *  \brief    Standardkonstruktor der Klasse, der die wesentlichen Parameter befüllt.
   *  
   *  @param    innerRegulation         In dem Parameter wird eine Referenz auf
   *                                    die innerer Regel erwartet, mit der 
   *                                    diese Regel einen Regelverbund bilden
   *                                    soll.
   *  @param    effective               Dieser Parameter legt fest, ob die Regel
   *                                    innerhalb des Regelverbundes in Kraft
   *                                    sein soll.
   *  @param    suspendable             Dieser Parameter legt fest, ob die Regel
   *                                    außer Kraft gesetzt werden darf.
   */
  public AbstractRegulationDecorator(Regulation<T, G, P> innerRegulation, boolean effective, boolean suspendable)
  {
    this.xInner = innerRegulation;
    this.bEffective = effective;
    this.bSuspendable = suspendable;
  }
  
  @Override
  public boolean isValid(int[] pointer, Vector<G> grid, Vector<P> participants) 
  {
    boolean _isInnerValid = this.xInner.isValid(pointer, grid, participants);
    return this.bEffective?(_isInnerValid && this.performCheck(pointer, grid, participants)):_isInnerValid;
  }
  
  protected abstract boolean performCheck(int[] pointer, Vector<G> grid, Vector<P> participants);
  
  public void init(T tournament, int round, Vector<P> participants)
  {
    this.xInner.init(tournament, round, participants);
    this.performInit(tournament, round, participants);
  }
  
  protected abstract void performInit(T tournament, int round, Vector<P> participants);

  public void teardown()
  {
    this.xInner.teardown();
    this.performTeardown();
  }


  protected abstract void performTeardown();

  @Override
  public boolean suspend() 
  {
    boolean _hasInnerToggled = this.xInner.suspend();
    boolean _hasThisToggled = false;
    
    if(!_hasInnerToggled)
      if(this.bEffective && this.bSuspendable)
      {
        this.bEffective = false;
        _hasThisToggled = true;
      }
    
    return (_hasInnerToggled || _hasThisToggled);
  }

  @Override
  public boolean suspendAll() 
  {
    boolean _retval = this.xInner.suspendAll();
    
    if(this.bSuspendable)
    {
      _retval = _retval || this.bEffective;
      this.bEffective = false;
    }
    return _retval;
  }
  
  public boolean putIntoEffect()
  {
    boolean _hasInnerToggled = this.xInner.putIntoEffect();
    boolean _hasThisToggled = false;
    
    if(!_hasInnerToggled)
    {
      _hasThisToggled = !this.bEffective;
      this.bEffective = true;
    }
    
    return (_hasInnerToggled || _hasThisToggled);
  }

  public boolean putIntoEffectAll()
  {
    boolean _retval = this.xInner.putIntoEffectAll();
    
    _retval = _retval || !this.bEffective;
    this.bEffective = true;
    
    return _retval;
  }

  public boolean isEffective()
  {
    return this.bEffective;
  }
  
  public boolean isSuspendable()
  {
    return this.bSuspendable;
  }
  
  public String toString() {
    String _retval = this.xInner.toString();
    _retval = _retval.concat("\nRegel: ").concat(this.getRuleDescription());
    _retval = _retval.concat(" (").concat(this.isSuspendable()?(this.isEffective()?"ein":"aus"):"fest").concat(")");
    
    return _retval;
  }

  protected abstract String getRuleDescription();
}
