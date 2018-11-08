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
 * \brief       Die Klasse steht für die Tabelle für Spielpaarungen im Supermelee
 * 
 * 
 * 
 * @author      dirk.schweier
 */
public class SuperMeleeMatchdayTable extends JTable
{
  /**
   *  \brief    Konstante für die Spalte mit den Index innerhalb der Runde
   *  
   *  Die Begegnungen in einer Runde werden fortlaufend mit 1 beginnend 
   *  durchnummeriert.
   *  
   *  Diese Konstante steht für die Spalte, in der dieser Index angezeigt
   *  wird.
   */
  final static public String COL_INDEX        = "INDEX";
  /**
   *  \brief    Konstante für den eindeutigen Index der Begegnung im Turnier
   *  
   *  Jede Begegnung soll selbst einen eindeutigen Index haben, der im Turnier
   *  nur einmal vorkommt. Damit lassen sich zum Beispiel Begegnungen 
   *  beschreiben, ohne dass ein konkretes Team zu benennen ist. Es kann z.B.
   *  "Sieger aus Begegnung 50" im Spielplan auftauchen und das Team wird
   *  erst angegeben, wenn die Begegnung mit Ergebnis versehen ist.
   *  
   *  Diese Konstante steht für die Spalte, die nur optional angezeigt wird.
   */
  final static public String COL_MATCH        = "MATCH";
  /**
   *  \brief    Konstante für die Spalte für das erste Team der Begegnung
   *  
   *  In einem Supermelee-Turnier besteht eine Begegnung aus zwei Teams. 
   *  
   *  Diese Konstante steht für das erste der beiden Teams einer Begegnung.
   */
  final static public String COL_HOME_TEAM    = "HOTEAM";
  /**
   *  \brief    Konstante für die Spalte mit dem Ergebnis des ersten Teams
   *  
   *  In einem Supermelee-Turnier besteht eine Begegnung aus zwei Teams. 
   *  
   *  Diese Konstante steht für die Spalte mit dem Ergebnis für das erste der 
   *  beiden Teams einer Begegnung.
   */
  final static public String COL_HOME_SCORE   = "HOSCOR";
  /**
   *  \brief    Konstante für die Spalte für das zweite Team der Begegnung
   *  
   *  In einem Supermelee-Turnier besteht eine Begegnung aus zwei Teams. 
   *  
   *  Diese Konstante steht für das zweiter der beiden Teams einer Begegnung.
   */
  final static public String COL_GUEST_TEAM   = "GUTEAM";
  /**
   *  \brief    Konstante für die Spalte mit dem Ergebnis des zweiten Teams
   *  
   *  In einem Supermelee-Turnier besteht eine Begegnung aus zwei Teams. 
   *  
   *  Diese Konstante steht für die Spalte mit dem Ergebnis für das zweite der 
   *  beiden Teams einer Begegnung.
   */
  final static public String COL_GUEST_SCORE  = "GUSCOR";
  /**
   *  \brief    Konstante für die Spalte zwischen den beiden Ergebnissen
   *  
   *  Die Ergebnisse der beiden Teams werden aus optischen Gründen durch eine
   *  Spalte von einander getrennt.
   *  
   *  Diese Konstante steht für diese Spalte, die die Ergebnisse von einander
   *  trennt.
   */
  final static public String COL_VS_SCORE     = "VERSUS";
  /**
   *  \brief    Konstante für den Platz, auf dem die Begegnung ausgespielt wird
   *  
   *  Je nach Turniermodus wird neben der Spielpaarung auch der Platz gelost, 
   *  auf dem die Begegnung auszutragen ist.
   *  
   *  Diese Konstante steht für die Spalte, in der der Platz ausgegeben wird.
   */
  final static public String COL_COURT        = "COURT";
  
  /**
   *  \brief    Referenz auf die Turnierinstanz, zu der die Runde gehört
   *  
   *  Diese Tabelle ist eine grafische Darstellung der Runde eines konkreten
   *  Turniers. In diesem Attribut wird die Referenz auf das Datenmodell
   *  gehalten, aus dem sich die angezeigten Daten ableiten lassen.
   */
  private SuperMelee xTournament = null;
  /**
   *  \brief    Index für die Runde, die durch diese JTable dargestellt wird
   */
  private int iMatchdayIndex = -1;

  /**
   *  Dieser Konstruktror ist der Standardkonstruktor der Klasse mit den notwendingen Parametern.
   *  
   *  @param    tournament    In dem Parameter ist eine Instanz von SuperMelee 
   *                          zu übergeben. 
   *  @param    matchday
   */
  public SuperMeleeMatchdayTable(SuperMelee tournament, int matchday)
  {
    super();
    this.xTournament = tournament;
    this.setModel(new TableModel(matchday));
    this.xTournament.addObserver((Observer) this.getModel());
    this.iMatchdayIndex = matchday;
    this.addKeyListener(new KeyAdapter());
    this.setDefaultEditor(Object.class, new PetanqueTableCellEditor());
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


    /**
     *  \brief  Methode übernimmt einen erfassten Wert in GUI und Datenmodell der Tabelle
     *  
     *  @param  aValue        In dem Paramter ist das Ergebnis zu übergeben.
     *  @param  row           In diesem Parameter wird der Zeilenindex der 
     *                        Zelle übergeben, die geändert werden soll.
     *  @param  column        In diesem Parameter wird der Spaltenindex der 
     *                        Zelle übergeben, die geändert werden soll.
     *                        
     *  \todo   In dieser Methode fehlt noch ein ordentliches Handling von
     *          fehlerhaften Eingaben, wie z.B. nicht numerische Eingaben.
     *  
     *  @see javax.swing.table.DefaultTableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt(Object aValue, int row, int column)
    {
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
        { //  Ergebnis ist vollständig und wird ausgewertet
          this.xTournament.setResult(iMatchdayIndex, row, Integer.parseInt(this.getValueAt(row, this.astrHeader.indexOf(COL_HOME_SCORE)).toString()), Integer.parseInt(this.getValueAt(row, this.astrHeader.indexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE)).toString()));
          this.xTournament.forceNotifyAll();
        }
      }
    }
    
    /**
     *  \brief  Methode erzeugt die Kopfzeile der Tabelle mit den Texten, die in der GUI angezeigt werden.
     *  
     *  In der internen Struktur SuperMeleeMatchdayTable.astrHeader werden nur
     *  die Konstanten der jeweiligen Spalten hinterlegt. Mit dieser Methode 
     *  wird die Kopfzeile der Tabelle mit den Spaltennamen erzeugt, die dann 
     *  in der GUI dargestellt werden.
     *  
     *  @todo   Die Bezeichner werden aktuell noch nicht aus den Properties 
     *          gelesen.
     *  
     *  @return Die Methode liefert die Kopfzeile als Vector zurück.
     */
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

    /**
     *  \brief  Methode liefert die Information, ob der Zelleninhalt editiert werden darf
     *  
     *  Der Anwender darf in der Tabelle nur die Spalten editieren, in denen 
     *  Spielergebnnisse der Teams einer Spielpaarung erfasst werden sollen.
     *  Alle anderen Zellen sollen nicht geändert werden dürfen.
     *  
     *  @param  row           In dem Parameter wird der Zeilenindex der Zelle
     *                        übergeben, für die angefragt werden soll, ob der
     *                        Anwender den Inhalt ändern darf.
     *  @param  column        In dem Parameter wird der Spaltenindex der Zelle
     *                        übergeben, für die angefragt werden soll, ob der
     *                        Anwender den Inhalt ändern darf
     *  
     *  @return Die Methode liefert im Rückgabewert den Wert <code>true</code>,
     *          wenn der Anwender den Zelleninhalt bearbeiten darf.
     */
    @Override
    public boolean isCellEditable(int row, int column)
    {
       
      return (      SuperMeleeMatchdayTable.COL_HOME_SCORE.equals(this.astrHeader.get(column)) 
                ||  SuperMeleeMatchdayTable.COL_GUEST_SCORE.equals(this.astrHeader.get(column)) );
    }
    
    /**
     *  \brief  Mit der Methode wird das Ausgabeformat der Paarungen umgeschaltet
     *  
     *  In der Tabelle werden Spielpaarungen und Spielergebnisse auf der GUI 
     *  ausgegeben. Für die Paarungen können entweder die Nummern oder Namen
     *  der Spieler der Teams angezeigt werden. 
     *  
     *  Mit dieser Methode kann die Darstellung umgeschaltet werden.
     *  
     *  @return Die Methode liefert den Wert <code>true</code>, wenn die 
     *          Darstellung auch Nummern umgeschaltet wurde. Andernfalls wird
     *          der Wert <code>false</code> zurückgegeben.
     */
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
    
    /**
     *  \brief  Getter-Methode liefert den Spaltenindex zu einem speziellen Spaltencode
     *  
     *  In der Tabelle kann es eine variable Anzahl von Spalten geben. Somit 
     *  kann ein Spalte nicht über einen konstanten Spaltenindex referenziert
     *  werden.
     *  
     *  Jede Tabelle hat mit dem Vector astrHeader einen Ort, an dem die 
     *  Spalten dokumentiert sind.
     *  
     *  @param  header        In diesem Parameter ist eine Spaltenkennung zu
     *                        übergeben, zu der der Spaltenindex zurückgegeben
     *                        werden soll.
     *                        
     *  @return Die Methode liefert den Spaltenindex als <code>int</code> 
     *          zurück. Wenn die Spalte nicht ermittelt werden kann, da 
     *          diese nicht eingeblendet oder unbekannt ist, wird der Wert
     *          -1 zurückgegeben.
     */
    public int getColumnIndexOf(String header)
    {
      return this.astrHeader.indexOf(header);
    }
  }
  
  /**
   *  \brief    Listener zur Positionierung des Focus in der SuperMeleeMatchdayTable
   * 
   *  In der Tabelle mit Spielergebnissen gibt es nur wenige Zellen, in denen
   *  der Anwender Eingaben machen kann. Diese Zellen sollen über die Tab-Taste
   *  angesteuert werden können.
   *  
   *  Dieser Listener kümmert sich darum, dass der Anwender mit der Tab-Taste 
   *  das nächste freie Feld in den Fokus nehmen kann, damit weitere Ergebnisse
   *  erfasst werden können.
   *  
   *  @author dirk.schweier
   *  
   *  \see      SuperMeleeMatchdayTable
   *
   */
  private class KeyAdapter extends java.awt.event.KeyAdapter {
    /**
     *  \brief  Methode, die im Falle der Tab-Taste den Fokus in der Tabelle steuert
     *  
     *  Diese Methode wird von dem Interface eingefordert und wird aufgerufen, 
     *  wenn Tasten gedrückt werden.
     *  
     *  \param  e             In dem Parameter wird eine Instanz übergeben, aus
     *                        der Informationen über das Ereignis hervorgehen,
     *                        das Auslöser für den Aufruf dieser Methode ist.
     */
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
