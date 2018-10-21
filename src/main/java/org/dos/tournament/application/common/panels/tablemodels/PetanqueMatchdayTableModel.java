package org.dos.tournament.application.common.panels.tablemodels;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dos.tournament.petanque.tournament.movement.SuperMelee;

public class PetanqueMatchdayTableModel extends DefaultTableModel implements Observer
{
  private int iMatchdayIndex;
  private boolean bInit = true;
  private SuperMelee xTournament = null;
  private boolean bOutputNumeric = true;
  
  public PetanqueMatchdayTableModel(int matchday)
  {
    this.iMatchdayIndex = matchday;
  }

  
  @Override
  public void update(Observable o, Object arg)
  {
    
    switch(o.getClass().getName())
    {
      case "org.dos.tournament.petanque.tournament.movement.SuperMeleeClubChampionship":
      case "org.dos.tournament.petanque.tournament.movement.SuperMelee":                  this.update((SuperMelee)o, arg);
                                                                                          break;
    }
  }
  
  protected void update(SuperMelee tournament, Object arg)
  {
    if((0 == this.getDataVector().size()) || this.matchdayChanged(arg))
    {
      int iMatches = tournament.getMatchday(iMatchdayIndex).countMatches();
      Vector<Vector<String>> _matchdayData = new Vector<Vector<String>>();
  
      for(int i=0; i<iMatches; ++i)
      {
        Vector<String> _row = new Vector<String>();
        _row.add(String.valueOf(i+1));
        if (this.bOutputNumeric)
        {
          _row.add(tournament.getMatchday(iMatchdayIndex).getMatch(i).getCompetitor(0).getDescriptionByCode());
          _row.add(tournament.getMatchday(iMatchdayIndex).getMatch(i).getCompetitor(1).getDescriptionByCode());
        }
        else
        {
          _row.add(tournament.getMatchday(iMatchdayIndex).getMatch(i).getCompetitor(0).getDescription());
          _row.add(tournament.getMatchday(iMatchdayIndex).getMatch(i).getCompetitor(1).getDescription());
        }
        _row.add("");
        _row.add(":");
        _row.add("");
        
        _matchdayData.add(_row);
      }
      
      this.setDataVector(_matchdayData, PetanqueMatchdayTableModel.STANDARDHEADER_PETANQUE());
      
      this.xTournament = tournament;
      
      this.fireTableDataChanged();
    }
  } 

  private boolean matchdayChanged(Object cause)
  {
    boolean _retval = false;
    
    if(null != cause)
      if(cause.getClass().getName().equals("org.dos.tournament.petanque.tournament.movement.SuperMelee$MatchdayUpdate"))
        _retval = (this.iMatchdayIndex == ((org.dos.tournament.petanque.tournament.movement.SuperMelee.MatchdayUpdate)cause).getMatchday());
    return _retval;
  }


  /* (non-Javadoc)
   * @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
   */
  @Override
  public void setValueAt(Object aValue, int row, int column)
  {
    // TODO Auto-generated method stub
    super.setValueAt(aValue, row, column);
    if((""!=this.getValueAt(row, 3))&&(""!=this.getValueAt(row, 5))&&(null!=this.xTournament))
    { 
      if(Integer.parseInt(this.getValueAt(row, 3).toString()) == Integer.parseInt(this.getValueAt(row, 5).toString()))
      { //  Falls ein "Unentschieden" erfasst wird, wird der andere Wert gel�scht!
        switch(column)
        {
          case 3 : this.setValueAt("", row, 5);break;
          case 5 : this.setValueAt("", row, 3);break;
        }
      }
      else  
      { //  Ergebnis ist vollst�ndig und wird ausgewertet
        this.xTournament.setResult(iMatchdayIndex, row, Integer.parseInt(this.getValueAt(row, 3).toString()), Integer.parseInt(this.getValueAt(row, 5).toString()));
        this.xTournament.forceNotifyAll();
      }
    }
  }


  static private Vector<String> STANDARDHEADER_PETANQUE()
  {
    Vector<String> _header = new Vector<String>();
    _header.add("Partie");
    _header.add("Heim");
    _header.add("Gast");
    _header.add("");
    _header.add("");
    _header.add("");
    
    return _header;
  }


  @Override
  public boolean isCellEditable(int row, int column)
  {
    return ((3 == column)||(5 == column));
  }
  
  public boolean toggleOutputParticipants()
  {
    this.bOutputNumeric = !this.bOutputNumeric;
    if(null != this.xTournament)
    {
      for(int i=0; i<this.xTournament.getMatchday(this.iMatchdayIndex).countMatches(); ++i)
        if (this.bOutputNumeric)
        {
          this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(0).getDescriptionByCode(), i, 1);          
          this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(1).getDescriptionByCode(), i, 2);
        }
        else
        {
          this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(0).getDescription(), i, 1);          
          this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(1).getDescription(), i, 2);
        }        
    }
    this.fireTableDataChanged();
    return this.bOutputNumeric;
  }
  
}
