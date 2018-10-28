/**
 * 
 */
package org.dos.tournament.application.common.panels.components;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dos.tournament.application.common.utils.tablecelleditor.PetanqueTableCellEditor;
import org.dos.tournament.petanque.tournament.matchday.Matchday;
import org.dos.tournament.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.petanque.tournament.partie.Partie;

/**
 * @author dirk.schweier
 *
 */
public class SuperMeleeMatchdayTable extends JTable
{
  final static public String COL_INDEX        = "INDEX";
  final static public String COL_MATCH        = "MATCH";
  final static public String COL_HOME_TEAM    = "HOTEAM";
  final static public String COL_HOME_SCORE   = "HOSCOR";
  final static public String COL_GUEST_TEAM   = "GUTEAM";
  final static public String COL_GUEST_SCORE  = "GUSCOR";
  final static public String COL_VS_SCORE     = "VERSUS";
  final static public String COL_COURT        = "COURT";
  
  private SuperMelee xTournament = null;
  private int iMatchdayIndex = -1;
  
  public SuperMeleeMatchdayTable(SuperMelee tournament, int matchday)
  {
    super();
    this.xTournament = tournament;
    this.setModel(new TableModel(matchday));
    this.xTournament.addObserver((Observer) this.getModel());
    this.iMatchdayIndex = matchday;
    this.addKeyListener(new KeyAdapter());
    this.setDefaultEditor(Object.class, new PetanqueTableCellEditor());

    // TODO Auto-generated constructor stub
  }
  
  public boolean toggleOutputParticipants()
  {
    return ((TableModel) this.getModel()).toggleOutputParticipants();
  }
  
  protected Matchday getMatchday()
  {
    return this.xTournament.getMatchday(this.iMatchdayIndex);  
  }

  private class TableModel extends DefaultTableModel implements Observer
  {
    
    /**
     *  \brief    In diesem Attribut wird die Information gehalten zu
     *            welcher Runde des Turniers das Panel gehört.
     *  
     *  In der Applikation wird für jede Spielrunde/Spieltag ein Panel angezeigt.
     *  Über dieses Attribut weiß die Instanz, welche Daten des Turnieres für sie
     *  entscheidend sind.
     */
    private int iMatchdayIndex;
    private boolean bInit = true;
    private SuperMelee xTournament = null;
    private boolean bOutputNumeric = true;
    private Vector<String> astrHeader = null;
    
    public TableModel(int matchday)
    {
      this.iMatchdayIndex = matchday;
      this.astrHeader = new Vector<String>();
      this.astrHeader.addElement(SuperMeleeMatchdayTable.COL_INDEX);
      this.astrHeader.addElement(SuperMeleeMatchdayTable.COL_HOME_TEAM);
      this.astrHeader.addElement(SuperMeleeMatchdayTable.COL_GUEST_TEAM);
      this.astrHeader.addElement(SuperMeleeMatchdayTable.COL_HOME_SCORE);
      this.astrHeader.addElement(SuperMeleeMatchdayTable.COL_VS_SCORE);
      this.astrHeader.addElement(SuperMeleeMatchdayTable.COL_GUEST_SCORE);
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
      if(null == this.xTournament)
        this.xTournament = tournament;

      if((0 == this.getDataVector().size()) || this.matchdayChanged(arg))
      {
        int iMatches = tournament.getMatchday(iMatchdayIndex).countMatches();
        Vector<Vector<String>> _matchdayData = new Vector<Vector<String>>();
    
        for(int i=0; i<iMatches; ++i)
        {
          Vector<String> _row = new Vector<String>();

          for(String _column : this.astrHeader)
          { 
            Partie _match = this.xTournament.getMatchday(iMatchdayIndex).getMatch(i);
            switch(_column)
            {
              case SuperMeleeMatchdayTable.COL_INDEX       : _row.add(String.valueOf(i+1)); break;
              case SuperMeleeMatchdayTable.COL_MATCH       : _row.add( _match.getId()); break;
              case SuperMeleeMatchdayTable.COL_HOME_TEAM   : _row.add( this.bOutputNumeric?  _match.getCompetitor(0).getDescriptionByCode():
                                                                                _match.getCompetitor(0).getDescription());
                                                break;
              case SuperMeleeMatchdayTable.COL_HOME_SCORE  : _row.add( _match.isScored()?    "":""); break;
              case SuperMeleeMatchdayTable.COL_GUEST_TEAM  : _row.add( this.bOutputNumeric?  _match.getCompetitor(1).getDescriptionByCode():
                                                                                _match.getCompetitor(1).getDescription());
                                                break;
              case SuperMeleeMatchdayTable.COL_GUEST_SCORE : _row.add( _match.isScored()?    "":""); break;
              case SuperMeleeMatchdayTable.COL_VS_SCORE    : _row.add(":"); break;
              case SuperMeleeMatchdayTable.COL_COURT       : _row.add("Platz"); break;
              default                         : _row.add("");
            }
          }
          _matchdayData.add(_row);
        }
        
        this.setDataVector(_matchdayData, this.compileHeader());
        
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
      if((""!=this.getValueAt(row, this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE)))&&(""!=this.getValueAt(row, this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE)))&&(null!=this.xTournament))
      { 
        if(Integer.parseInt(this.getValueAt(row, this.astrHeader.indexOf(COL_HOME_SCORE)).toString()) == Integer.parseInt(this.getValueAt(row, this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE)).toString()))
        { //  Falls ein "Unentschieden" erfasst wird, wird der andere Wert gelï¿½scht!
          if( column == this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE) )
            this.setValueAt("", row, this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE));
          if( column == this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE) )
            this.setValueAt("", row, this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE));
        }
        else  
        { //  Ergebnis ist vollstï¿½ndig und wird ausgewertet
          this.xTournament.setResult(iMatchdayIndex, row, Integer.parseInt(this.getValueAt(row, this.astrHeader.indexOf(COL_HOME_SCORE)).toString()), Integer.parseInt(this.getValueAt(row, this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE)).toString()));
          this.xTournament.forceNotifyAll();
        }
      }
    }
    
    private Vector<String> compileHeader()
    {
      Vector<String> _header = new Vector<String>();
      
      for(String _column : this.astrHeader)
      {
        switch(_column)
        {
          case SuperMeleeMatchdayTable.COL_INDEX       : _header.add("Nr."); break;
          case SuperMeleeMatchdayTable.COL_MATCH       : _header.add("Id"); break;
          case SuperMeleeMatchdayTable.COL_HOME_TEAM   : _header.add("Heim"); break;
          case SuperMeleeMatchdayTable.COL_HOME_SCORE  : _header.add(""); break;
          case SuperMeleeMatchdayTable.COL_GUEST_TEAM  : _header.add("Gast"); break;
          case SuperMeleeMatchdayTable.COL_GUEST_SCORE : _header.add(""); break;
          case SuperMeleeMatchdayTable.COL_VS_SCORE    : _header.add(""); break;
          case SuperMeleeMatchdayTable.COL_COURT       : _header.add("Platz"); break;
          default                         : _header.add("");
        }
      }
      
      return _header;
    }


    private Vector<String> STANDARDHEADER_PETANQUE()
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
       
      return (      SuperMeleeMatchdayTable.COL_HOME_SCORE.equals(this.astrHeader.get(column)) 
                ||  SuperMeleeMatchdayTable.COL_GUEST_SCORE.equals(this.astrHeader.get(column)) );
    }
    
    public boolean toggleOutputParticipants()
    {
      this.bOutputNumeric = !this.bOutputNumeric;
      if(null != this.xTournament)
      {
        for(int i=0; i<this.xTournament.getMatchday(this.iMatchdayIndex).countMatches(); ++i)
          if (this.bOutputNumeric)
          {
            this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(0).getDescriptionByCode(), i, this.astrHeader.indexOf(COL_HOME_TEAM));          
            this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(1).getDescriptionByCode(), i, this.astrHeader.indexOf(COL_GUEST_TEAM));
          }
          else
          {
            this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(0).getDescription(), i, this.astrHeader.indexOf(COL_HOME_TEAM));          
            this.setValueAt(this.xTournament.getMatchday(this.iMatchdayIndex).getMatch(i).getCompetitor(1).getDescription(), i, this.astrHeader.indexOf(COL_GUEST_TEAM));
          }        
      }
      this.fireTableDataChanged();
      return this.bOutputNumeric;
    }
    
    public int getColumnIndexOf(String header)
    {
      return this.astrHeader.indexOf(header);
    }
  }
  
  private class KeyAdapter extends java.awt.event.KeyAdapter {
    public void keyPressed(KeyEvent e) {
      if('\t' == e.getKeyChar())
      {
        int _iCurrentColumn = SuperMeleeMatchdayTable.this.getSelectedColumn();
        int _iCurrentRow    = SuperMeleeMatchdayTable.this.getSelectedRow();
        if(((TableModel) SuperMeleeMatchdayTable.this.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE) == _iCurrentColumn)
          SuperMeleeMatchdayTable.this.changeSelection(_iCurrentRow, ((TableModel) SuperMeleeMatchdayTable.this.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE) - 1, false, false);
        else
        { //  In this case the next unscored match should be focused 
          ++_iCurrentRow;
          if(SuperMeleeMatchdayTable.this.getMatchday().countMatches() <= _iCurrentRow)
            _iCurrentRow = 0;
          while( (SuperMeleeMatchdayTable.this.getMatchday().countMatches() > _iCurrentRow) && (SuperMeleeMatchdayTable.this.getMatchday().getMatch(_iCurrentRow).isScored()))
            ++_iCurrentRow;
          SuperMeleeMatchdayTable.this.changeSelection(_iCurrentRow, ((TableModel) SuperMeleeMatchdayTable.this.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE) - 1, false, false);
        }
      }
    }
  }
}
