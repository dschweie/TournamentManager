package org.dos.tournament.application.petanque.panels.tablemodels;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.dos.tournament.petanque.tournament.movement.SuperMelee;
import org.dos.tournament.player.IParticipant;

public class ParticipantsTableModel extends DefaultTableModel implements Observer
{

  public ParticipantsTableModel()
  {
    super(null, new String[] {"Nummer", "Name", "Verein", "Status"});
//    super(new Object[][] {{null, null, null, null}}, new String[] {"Nummer", "Name", "Verein", "Status"});
  }
  
  private Vector vecHeader = null;
  
  @SuppressWarnings("unchecked")
  @Override
  public void update(Observable o, Object arg)
  {
    Vector<IParticipant> _participants = ((SuperMelee)o).getCompetitors();
    Vector<Vector<String>> _field = new Vector<Vector<String>>();
    
    if(null == this.vecHeader)
      this.vecHeader = ParticipantsTableModel.compileDefaultHeader();
    
    for(int i=0; i < _participants.size(); ++i)
      _field.add(_participants.elementAt(i).getParticipantAsRow(this.vecHeader));
    
    this.setDataVector(_field, this.columnIdentifiers);
    
    this.fireTableDataChanged();
  }

  static private Vector<String> compileDefaultHeader()
  {
    Vector<String> _header=new Vector<String>();
    _header.add("id");
    _header.add("name");
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
