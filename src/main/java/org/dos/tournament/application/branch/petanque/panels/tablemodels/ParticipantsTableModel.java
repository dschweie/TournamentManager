package org.dos.tournament.application.branch.petanque.panels.tablemodels;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dos.tournament.branch.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.common.player.IParticipant;

public class ParticipantsTableModel extends DefaultTableModel implements Observer
{

  public ParticipantsTableModel()
  {
    super(null, new String[] {"Nummer", "Vorname", "Nachname", "Verein", "Status"});
//    super(new Object[][] {{null, null, null, null}}, new String[] {"Nummer", "Name", "Verein", "Status"});
  }
  
  private Vector vecHeader = null;
  
  @SuppressWarnings("unchecked")
  @Override
  public void update(Observable o, Object arg)
  {
    Vector<IParticipant> _participants = ((SuperMelee)o).getCompetitors();
    Vector<Vector<Object>> _field = new Vector<Vector<Object>>();
    
    if(null == this.vecHeader)
      this.vecHeader = ParticipantsTableModel.compileDefaultHeader();
    
    for(int i=0; i < _participants.size(); ++i)
      _field.add(_participants.elementAt(i).getParticipantAsRow(this.vecHeader));
    
    this.setDataVector(_field, this.columnIdentifiers);

    this.fireTableDataChanged();
  }
  
  

  /* (non-Javadoc)
   * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
   */
  @Override
  public Class<?> getColumnClass(int columnIndex)
  {
    switch( columnIndex )
    {
        case 0:   return Integer.class;
        default:  return String.class;
    }
  }



  static private Vector<String> compileDefaultHeader()
  {
    Vector<String> _header=new Vector<String>();
    _header.add("id");
    _header.add("name.forename");
    _header.add("name.surname");
    _header.add("club");
    _header.add("status");
    return _header;
  }

  /* (non-Javadoc)
   * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
   */
  @Override
  public boolean isCellEditable(int row, int column)
  {
    // the table is read only.
    return false;
  }
  
  
}
