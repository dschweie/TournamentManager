package org.dos.tournament.petanque.tournament.matchday;

import java.util.Vector;

import org.dos.tournament.petanque.tournament.partie.Partie;

public class Matchday
{
  private Vector<Partie> matches;
  
  public Matchday()
  {
    this.matches = new Vector<Partie>();
  }
  
  public void addPartie(Partie match)
  {
    this.matches.addElement(match);
  }
  
  public int countMatches()
  {
    return this.matches.size();
  }
  
  public int countCompletedMatches()
  {
    int _retval = 0;
    
    for(int i=0; i < this.matches.size(); ++i)
      if(this.matches.get(i).isComplete())
        ++_retval;
    
    return _retval;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    String _retval = "";
    
    for(int i = 0; i < this.matches.size(); ++i)
      _retval = _retval.concat(this.matches.get(i).toString()).concat("\n");
    
    return _retval;
  }
  
  public String toStringWithNames()
  {
    String _retval = "";
    
    for(int i = 0; i < this.matches.size(); ++i)
      _retval = _retval.concat(this.matches.get(i).toStringWithNames()).concat("\n");
    
    return _retval;
  }
  
  public Partie getMatch(int index)
  {
    return ((-1<index)&&(this.matches.size()>index))?this.matches.get(index):null;    
  }
}
