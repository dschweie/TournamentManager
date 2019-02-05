/**
 * 
 */
package org.dos.tournament.application.common.panels.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.dos.tournament.application.common.utils.tablecelleditor.PetanqueTableCellEditor;
import org.dos.tournament.branch.petanque.tournament.matchday.Matchday;
import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.branch.petanque.tournament.partie.Partie;
import org.dos.tournament.application.common.controls.TableHeaderColumnContent;

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
  
  final static public Color  COLOR_CELL_DEFAULT               = Color.WHITE;
  final static public Color  COLOR_CELL_MATCH_SCORED          = new Color( 144, 238, 144 ); 
  final static public Color  COLOR_CELL_MATCH_SCORED_SELECTED = new Color(   0, 195,   0 );
  final static public Color  COLOR_TEXT_DEFAULT               = Color.BLACK;
  final static public Color  COLOR_TEXT_MATCH_SCORED          = Color.BLACK; 
  final static public Color  COLOR_TEXT_MATCH_SCORED_SELECTED = Color.WHITE;
  final static public Color  COLOR_TEXT_MATCH_WON             = new Color(   0, 120,   0 ); 
  final static public Color  COLOR_TEXT_MATCH_WON_SELECTED    = Color.WHITE;
  final static public Color  COLOR_TEXT_MATCH_LOST            = Color.GRAY; 
  final static public Color  COLOR_TEXT_MATCH_LOST_SELECTED   = Color.BLACK;
 
  
  
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
   * @return the iMatchdayIndex
   */
  public int getMatchdayIndex()
  {
    return iMatchdayIndex;
  }

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
    TableModel _model = new TableModel(matchday);
    this.setModel(_model);
    _model.update(this.xTournament, null);
    this.xTournament.addObserver(_model);
    this.iMatchdayIndex = matchday;
    this.addKeyListener(new KeyAdapter());
    this.setDefaultEditor(Object.class, new PetanqueTableCellEditor());
    if(null != this.getColumn(COL_INDEX))
      this.getColumn(COL_INDEX).setCellRenderer(new TableCellRendererMatchStatus());
    if(null != this.getColumn(COL_VS_SCORE))
      this.getColumn(COL_VS_SCORE).setCellRenderer(new TableCellRendererCenteredText());
    if(null != this.getColumn(COL_HOME_SCORE))
      this.getColumn(COL_HOME_SCORE).setCellRenderer(new TableCellRendererCenteredText());
    if(null != this.getColumn(COL_GUEST_SCORE))
      this.getColumn(COL_GUEST_SCORE).setCellRenderer(new TableCellRendererCenteredText());
    if(null != this.getColumn(COL_HOME_TEAM))
      this.getColumn(COL_HOME_TEAM).setCellRenderer(new TableCellRendererHomeColumn());
    if(null != this.getColumn(COL_GUEST_TEAM))
      this.getColumn(COL_GUEST_TEAM).setCellRenderer(new TableCellRendererGuestColumn());
  }
  
  public boolean toggleOutputParticipants()
  {
    return ((TableModel) this.getModel()).toggleOutputParticipants();
  }
  
  protected Matchday getMatchday()
  {
    return this.xTournament.getMatchday(this.iMatchdayIndex);  
  }
  
  
  /* (non-Javadoc)
   * @see javax.swing.JTable#getColumn(java.lang.Object)
   */
  @Override
  public TableColumn getColumn(Object identifier)
  {
    try
    {
      return this.getColumnModel().getColumn(((TableModel) this.getModel()).getColumnIndexOf((String) identifier));
    }
    catch(Exception e)
    {
      return super.getColumn(identifier);
    }
  }

  protected void updateColumnSize()
  {
    int _cols = this.getColumnModel().getColumnCount();
    
    for(int i = 0; i < _cols; ++i)
    {
      Object obj = this.getTableHeader().getColumnModel().getColumn(i).getHeaderValue();
      this.getColumnModel().getColumn(i).setPreferredWidth(((TableHeaderColumnContent)this.getTableHeader().getColumnModel().getColumn(i).getHeaderValue()).getPreferredWidth());
    }
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
        case "org.dos.tournament.branch.petanque.tournament.movement.SuperMeleeClubChampionship":
        case "org.dos.tournament.branch.petanque.tournament.movement.SuperMelee":                   this.update((SuperMelee)o, arg);
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
              case SuperMeleeMatchdayTable.COL_HOME_SCORE  : _row.add( _match.isScored()?    String.valueOf(_match.getHomeScore()):""); break;
              case SuperMeleeMatchdayTable.COL_GUEST_TEAM  : _row.add( this.bOutputNumeric?  _match.getCompetitor(1).getDescriptionByCode():
                                                                                _match.getCompetitor(1).getDescription());
                                                break;
              case SuperMeleeMatchdayTable.COL_GUEST_SCORE : _row.add( _match.isScored()?    String.valueOf(_match.getGuestScore()):""); break;
              case SuperMeleeMatchdayTable.COL_VS_SCORE    : _row.add(":"); break;
              case SuperMeleeMatchdayTable.COL_COURT       : _row.add("Platz"); break;
              default                         : _row.add("");
            }
          }
          _matchdayData.add(_row);
        }
        
        
        Vector<TableHeaderColumnContent> _header = this.compileHeader();
        this.setDataVector(_matchdayData, _header);
        
        for(int i=0; i < _header.size(); ++i)
          SuperMeleeMatchdayTable.this.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(_header.get(i));
        
        
        SuperMeleeMatchdayTable.this.updateColumnSize();
        this.fireTableDataChanged();
      }
    } 

    private boolean matchdayChanged(Object cause)
    {
      boolean _retval = false;
      
      if(null != cause)
        if(cause.getClass().getName().equals("org.dos.tournament.branch.petanque.tournament.movement.SuperMelee$MatchdayUpdate"))
          _retval = (this.iMatchdayIndex == ((org.dos.tournament.branch.petanque.tournament.movement.SuperMelee.MatchdayUpdate)cause).getMatchday());
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
    private Vector<TableHeaderColumnContent> compileHeader()
    {
      Vector<TableHeaderColumnContent> _header = new Vector<TableHeaderColumnContent>();
      
      for(String _column : this.astrHeader)
      {
        switch(_column)
        {
          case SuperMeleeMatchdayTable.COL_INDEX       : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_INDEX, "Nr.", 30)); break;
          case SuperMeleeMatchdayTable.COL_MATCH       : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_MATCH, "Id", 30)); break;
          case SuperMeleeMatchdayTable.COL_HOME_TEAM   : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_HOME_TEAM, "Heim", 250)); break;
          case SuperMeleeMatchdayTable.COL_HOME_SCORE  : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_HOME_SCORE, "Punkte", 60)); break;
          case SuperMeleeMatchdayTable.COL_GUEST_TEAM  : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_GUEST_TEAM, "Gast", 250)); break;
          case SuperMeleeMatchdayTable.COL_GUEST_SCORE : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_GUEST_SCORE, "Punkte", 60)); break;
          case SuperMeleeMatchdayTable.COL_VS_SCORE    : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_VS_SCORE, "vs", 10)); break;
          case SuperMeleeMatchdayTable.COL_COURT       : _header.add(new TableHeaderColumnContent(SuperMeleeMatchdayTable.COL_COURT, "Platz", 30)); break;
          default                         : _header.add(new TableHeaderColumnContent("", "", 100));
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
  
  private class TableCellRendererMatchStatus extends DefaultTableCellRenderer
  {
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      Component _comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      
      Object _home = table.getValueAt(row, ((TableModel) table.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE));
      Object _guest = table.getValueAt(row, ((TableModel) table.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE));

      _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_DEFAULT);

      if(isSelected)
        this.setFont(this.getFont().deriveFont(Font.BOLD));
      else
        _comp.setBackground(SuperMeleeMatchdayTable.COLOR_CELL_DEFAULT);
      
      this.setHorizontalAlignment(JLabel.CENTER);


      try
      {
        if(null != _home && null != _guest)
          if(0 < Integer.parseInt(_home.toString().trim()) + Integer.parseInt(_guest.toString().trim()))
          {
            if(isSelected)
            {
              _comp.setBackground(SuperMeleeMatchdayTable.COLOR_CELL_MATCH_SCORED_SELECTED);
              _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_MATCH_SCORED_SELECTED);
            }
            else
              _comp.setBackground(SuperMeleeMatchdayTable.COLOR_CELL_MATCH_SCORED);
          }
      }
      catch(Exception e) {};
      
      return _comp;
    }
  }

  private class TableCellRendererHomeColumn extends DefaultTableCellRenderer
  {
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      Component _comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      
      Object _home = table.getValueAt(row, ((TableModel) table.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE));
      Object _guest = table.getValueAt(row, ((TableModel) table.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE));

      _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_DEFAULT);
      _comp.setFont(_comp.getFont().deriveFont(Font.PLAIN));
      
      if(isSelected)
        _comp.setFont(_comp.getFont());
      else
        _comp.setBackground(SuperMeleeMatchdayTable.COLOR_CELL_DEFAULT);

      try
      {
        if(null != _home && null != _guest)
          if(Integer.parseInt(_home.toString().trim()) > Integer.parseInt(_guest.toString().trim()))
          {
            this.setFont(this.getFont().deriveFont(Font.BOLD));
            if(isSelected)
            {
              _comp.setBackground( SuperMeleeMatchdayTable.COLOR_CELL_MATCH_SCORED_SELECTED );
              _comp.setFont(_comp.getFont().deriveFont(Font.BOLD));
              _comp.setForeground( SuperMeleeMatchdayTable.COLOR_TEXT_MATCH_WON_SELECTED );
            }
            else
            {
              _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_MATCH_WON);
            }
          }
          else
            _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_MATCH_LOST);
            
      }
      catch(Exception e) {};
      
      return _comp;
    }
  }
  
  private class TableCellRendererGuestColumn extends DefaultTableCellRenderer
  {
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      Component _comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      
      Object _home = table.getValueAt(row, ((TableModel) table.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_HOME_SCORE));
      Object _guest = table.getValueAt(row, ((TableModel) table.getModel()).getColumnIndexOf(SuperMeleeMatchdayTable.COL_GUEST_SCORE));

      _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_DEFAULT);
      _comp.setFont(_comp.getFont().deriveFont(Font.PLAIN));
      
      if(isSelected)
        _comp.setFont(_comp.getFont());
      else
        _comp.setBackground(SuperMeleeMatchdayTable.COLOR_CELL_DEFAULT);

      try
      {
        if(null != _home && null != _guest)
          if(Integer.parseInt(_home.toString().trim()) < Integer.parseInt(_guest.toString().trim()))
          {
            this.setFont(this.getFont().deriveFont(Font.BOLD));
            if(isSelected)
            {
              _comp.setBackground( SuperMeleeMatchdayTable.COLOR_CELL_MATCH_SCORED_SELECTED );
              _comp.setFont(_comp.getFont().deriveFont(Font.BOLD));
              _comp.setForeground( SuperMeleeMatchdayTable.COLOR_TEXT_MATCH_WON_SELECTED );
            }
            else
            {
              _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_MATCH_WON);
            }
          }
          else
            _comp.setForeground(SuperMeleeMatchdayTable.COLOR_TEXT_MATCH_LOST);
            
      }
      catch(Exception e) {};
      
      return _comp;
    }
  }
  
  private class TableCellRendererCenteredText extends DefaultTableCellRenderer
  {
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      super.getTableCellRendererComponent(table, value, isSelected,
          hasFocus, row, column);
      this.setHorizontalAlignment(JLabel.CENTER);
      return this;
    }
  }
}
